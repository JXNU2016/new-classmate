package com.example.lenovo.newclassmate.Activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.newclassmate.AllActivity;
import com.example.lenovo.newclassmate.R;
import com.example.lenovo.newclassmate.suggest.AndroidBug5497Workaround;
import com.example.pickerview.widge.CommonTitleBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/*
注册检验Activity
 */
public class VerifyActivity extends Activity {

    private Button mNext;
    private Spinner spinner;
    private EditText text;
    private String schoolName="江西师范大学";
    private String studentId=null;
    private String name=null;
    private String sex=null;
    private ClientThread clientThread;
    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度

    static  String[] str=new String[]{
            "江西师范大学",
            "江西科技大学",
            "江西财经大学"
    };
    private View content;
    private ScrollView scroll;
    private View service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_verify);
        AllActivity.getInstance().addActivity(this);   //添加此Activity到容器内
        text=findViewById(R.id.student_number);
        mNext = findViewById(R.id.bg_btn_login_selected);
        spinner = findViewById(R.id.spiner_textview_Verify);
        content = findViewById(R.id.content_verify);
        scroll = findViewById(R.id.scroll_verify);
        service = findViewById(R.id.service_vreify);
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content));
        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3
        initListener();

        spinner.setDropDownVerticalOffset(150);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,str);
        spinner.setAdapter(adapter);
        spinner.setSelection(0,false);


        //监听输入框（得到登录非焦点效果）
        mNext.setEnabled(false);
        TextWatcher watcher =  new TextWatcher (){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()>0)
                    mNext.setEnabled(true);
            }
        };
        text.addTextChangedListener(watcher);


        //为学校下拉框绑定监听器获取学校名字
        AdapterView.OnItemSelectedListener mOnItemSelectedListener = new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                                       long arg3) {
                schoolName=arg0.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        };
        spinner.setOnItemSelectedListener(mOnItemSelectedListener);


        //绑定（下一步）按钮监听器(创建网络连接)
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    clientThread=new ClientThread(schoolName,studentId);
                    new Thread(clientThread).start();
                }
                else {
                    Toast.makeText(getBaseContext(), "输入错误！", Toast.LENGTH_LONG).show();
                }
            }
        });


        //设置title标题栏
        ((CommonTitleBar) findViewById(R.id.titlebar)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    onBackPressed();
                }
            }
        });
    }


    //判断输入的合法性
    public boolean validate() {
        boolean valid = true;

        studentId = text.getText().toString();

        if (studentId.isEmpty() || studentId.length()<8) {
            text.setError("输入有效的学号");
            valid = false;
        }
        else {
            text.setError(null);
        }
        return valid;
    }

    //匹配成功进行页面跳转
    public void onVerifySuccess()
    {
        Intent intent = new Intent(VerifyActivity.this, RegisterActivity.class);
        intent.putExtra("schoolName", schoolName);
        intent.putExtra("studentId", studentId);
        intent.putExtra("name",name);
        intent.putExtra("sex",sex);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    //创建线程处理http请求
    class ClientThread implements Runnable
    {
        private Handler handler;
        public Handler revHandler;
        String schoolName;
        String studentId;

        public ClientThread(String schoolName,String studentId) {
            this.schoolName=schoolName;
            this.studentId=studentId;
        }
        public void run() {
            InternetRequest(schoolName,studentId);
        }
    }


    //创建http请求
    public void InternetRequest(final String schoolName, final String studentId) {
        //请求地址
        String url = "http://47.107.48.62:8080/MyProject/RegisterServlet";
        String tag = "zszszs";

        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {//连接监听
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                            String result = jsonObject.getString("Result");
                            if (result.equals("success")) {
                                name=jsonObject.getString("name");
                                sex=jsonObject.getString("sex");
                                onVerifySuccess();
                            }
                            else if(result.equals("exist")) {
                                Toast.makeText(getBaseContext(), "该用户已注册！", Toast.LENGTH_LONG).show();
                            }
                            else if(result.equals("failed")){
                                Toast.makeText(getBaseContext(), "未找到学生信息，请检查是否输入错误！", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {//本地连接抛出错误
                            Toast.makeText(getBaseContext(), "网络连接错误", Toast.LENGTH_LONG).show();
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {//服务器错误监听
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "服务器繁忙，请稍后再试！", Toast.LENGTH_LONG).show();
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("RequestType","confirm");
                params.put("schoolName",schoolName);
                params.put("studentId", studentId);
                return params;
            }
        };
        //设置Tag标签
        request.setTag(tag);
        //将请求添加到队列中
        requestQueue.add(request);
    }




    private void initListener() {

        /**
         * 禁止键盘弹起的时候可以滚动
         */
        scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        scroll.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    Log.e("wenzhihao", "up------>"+top+oldBottom+bottom+(oldBottom - bottom)+keyHeight+content.getBottom());
                    int dist = content.getBottom() - bottom;
                    if (dist>0){
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(content, "translationY", 0.0f, -dist);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        // zoomIn(logo, dist);
                    }
                    service.setVisibility(View.INVISIBLE);

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    Log.e("wenzhihao", "down------>"+oldBottom+bottom+(bottom - oldBottom));
                    if ((content.getBottom() - oldBottom)>0){
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(content, "translationY", content.getTranslationY(), 0);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        //键盘收回后，logo恢复原来大小，位置同样回到初始位置
                        //zoomOut(logo);
                    }
                    service.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
