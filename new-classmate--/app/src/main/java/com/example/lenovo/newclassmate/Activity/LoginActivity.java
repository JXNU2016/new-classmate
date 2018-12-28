package com.example.lenovo.newclassmate.Activity;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.newclassmate.wxapi.AllActivity;
import com.example.lenovo.newclassmate.R;
import com.example.lenovo.newclassmate.wxapi.StartActivity;
import com.example.lenovo.newclassmate.suggest.*;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/*
登录界面
 */
public class LoginActivity extends Activity implements  View.OnClickListener{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private ClientThread clientThread;
    private Handler handler = new Handler();
    private String name;
    private String schoolName;
    private String studentId;
    private String password;
    private String sex;
    private String year;
    private String month;
    private String day;
    private String province;
    private String city;
    private String county;
    private String phone;

    @BindView(R.id.input_number) EditText mInput_number;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;
    @BindView(R.id.spiner_textview)Spinner mSpiner_textView;
    @BindView(R.id.scrollView_login) ScrollView mScrollView;
    @BindView(R.id.content) View mContent;
    @BindView(R.id.service) View service;
    @BindView(R.id.iv_clean_number) ImageView iv_clean_number;
    @BindView(R.id.clean_password) ImageView clean_password;
    @BindView(R.id.iv_show_pwd) ImageView iv_show_pwd;

    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度
    private float scale = 0.5f; //logo缩放比例

    SharedPreferences preferences;//创建SharedPreferences保存登录学号和密码
    SharedPreferences.Editor editor; //editor对象向SharedPreferences写入数据
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AllActivity.getInstance().addActivity(this);   //添加此Activity到容器内

        ButterKnife.bind(this);
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content)); //修复沉浸式状态栏所带来的adjustResize属性所带来的失效问题
        initListener();
        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,VerifyActivity.str);
        mSpiner_textView.setAdapter(adapter);
        mSpiner_textView.setDropDownVerticalOffset(150);//设置Spiner向下偏移量

        //获取只能被本地应用程序读写的SharedPreferences对象
        preferences= getSharedPreferences("land", Context.MODE_PRIVATE);
        editor=preferences.edit();

        //为学校下拉框绑定监听器获取学校名字
        AdapterView.OnItemSelectedListener mOnItemSelectedListener = new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                                       long arg3) {
                schoolName=arg0.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        };
        mSpiner_textView.setOnItemSelectedListener(mOnItemSelectedListener);


        //监听输入框（得到登录非焦点效果）
        _loginButton.setEnabled(false);
        TextWatcher   watcher =  new TextWatcher (){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable) && iv_clean_number.getVisibility() == View.GONE) {  //如果不是空的且视图不可见  TextUtils为字符串处理类
                    iv_clean_number.setVisibility(View.VISIBLE);  //设置可见
                } else if (TextUtils.isEmpty(editable)) {
                    iv_clean_number.setVisibility(View.GONE);
                }
                if(editable.toString().length()>0)
                    _loginButton.setEnabled(true);
            }
        };
        mInput_number.addTextChangedListener(watcher);
        // 监听密码框
        _passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && clean_password.getVisibility() == View.GONE) {
                    clean_password.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    clean_password.setVisibility(View.GONE);
                }
            }
        });
        //点击登陆按钮触发事件
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        //点击注册按钮触发事件
        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), VerifyActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        //设置标题
        ((CommonTitleBar) findViewById(R.id.titlebar_login)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    onBackPressed();
                }
            }
        });
    }


    //登录触发函数
    public void login() {
        Log.d(TAG, "Login");
        if (!validate()) { //检测输入是否有效函数
            Toast.makeText(getBaseContext(), "输入错误！", Toast.LENGTH_LONG).show();
            return;
        }
        else if(validate())
        {
            clientThread=new ClientThread(schoolName,studentId,password);
            new Thread(clientThread).start();
        }

    }

    //登录成功
    public void onLoginSuccess(String userName) {
        Toast.makeText(getBaseContext(), "欢迎您："+userName, Toast.LENGTH_LONG).show();
        //登录成功保存学号和密码在SharedPreferences，通过editor来向preference写入数据
        editor.putString("studentId",studentId);
        editor.putString("password",password);
        editor.putString("schoolName",schoolName);
        editor.putString("userName",userName);
        editor.putString("sex",sex);
        editor.putString("name",name);
        editor.putString("year",year);
        editor.putString("month",month);
        editor.putString("day",day);
        editor.putString("province",province);
        editor.putString("city",city);
        editor.putString("county",county);
        editor.putString("phone",phone);
        //提交所有数据
        editor.commit();

        Intent intent = new Intent(LoginActivity.this, StartActivity.class);
        startActivity(intent);
        finish();
    }
    //登录失败
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "账号或密码错误！", Toast.LENGTH_LONG).show();
    }


    //判断输入的合法性
    public boolean validate() {
        boolean valid = true;

        studentId = mInput_number.getText().toString();
        password = _passwordText.getText().toString();

        if (studentId.isEmpty() || studentId.length()<8) {
            mInput_number.setError("输入有效的学号");
            valid = false;
        } else {
            mInput_number.setError(null);
            if (password.isEmpty() || password.length() < 4 ) {
                _passwordText.setError("密码错误");
                valid = false;
            } else {
                _passwordText.setError(null);
            }
        }
        return valid;
    }


    //创建线程处理http请求
    class ClientThread implements Runnable
    {
        String schoolName;
        String studentId;
        String password;

        public ClientThread(String schoolName,String studentId,String password) {
            this.schoolName=schoolName;
            this.studentId=studentId;
            this.password=password;
        }
        public void run()
        {
            InternetRequest(schoolName,studentId,password);
        }

    }


    //创建http请求
    public void InternetRequest(final String schoolName, final String studentId, final String password) {
        //请求地址
        String url = "http://47.107.48.62:8080/MyProject/LandServlet";
        String tag = "zszszs";

        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                            String result = jsonObject.getString("Result");
                            if (result.equals("success")) {
                                String name=jsonObject.getString("Name");
                                String userName=jsonObject.getString("userName");
                                sex=jsonObject.getString("sex");
                                province=jsonObject.getString("province");
                                city=jsonObject.getString("city");
                                county=jsonObject.getString("county");
                                year=jsonObject.getString("year");
                                month=jsonObject.getString("month");
                                day=jsonObject.getString("day");
                                phone=jsonObject.getString("phone");
                                name=jsonObject.getString("Name");
                                onLoginSuccess(userName);
                            } else {
                                onLoginFailed();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getBaseContext(), "网络连接错误", Toast.LENGTH_LONG).show();
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "服务器繁忙，请稍后再试！", Toast.LENGTH_LONG).show();
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("SchoolName",schoolName);
                params.put("StudentId", studentId);
                params.put("Password", password);
                return params;
            }
        };
        //设置Tag标签
        request.setTag(tag);
        //将请求添加到队列中
        requestQueue.add(request);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }


    private void initListener() {
        iv_clean_number.setOnClickListener(this);
        clean_password.setOnClickListener(this);
        iv_show_pwd.setOnClickListener(this);
        /**
         * 禁止键盘弹起的时候可以滚动
         */
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        mScrollView.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    Log.e("wenzhihao", "up------>"+(oldBottom - bottom));
                    int dist = mContent.getBottom() - bottom;
                    if (dist>0){
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY", 0.0f, -dist);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                    }
                    service.setVisibility(View.INVISIBLE);

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    Log.e("wenzhihao", "down------>"+(bottom - oldBottom));
                    if ((mContent.getBottom() - oldBottom)>0){
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY", mContent.getTranslationY(), 0);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                    }
                    service.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_clean_number:
                mInput_number.setText(""); //清除学号框内容
                break;
            case R.id.clean_password:
                _passwordText.setText("");  //清除密码框内容
                break;
            case R.id.iv_show_pwd:
                if (_passwordText.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    _passwordText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_show_pwd.setImageResource(R.drawable.pass_visuable);
                } else {
                    _passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_show_pwd.setImageResource(R.drawable.pass_gone);
                }
                String pwd = _passwordText.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    _passwordText.setSelection(pwd.length());
                break;
        }
    }

}
