package com.example.lenovo.newclassmate.Activity.UserActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donkingliang.labels.LabelsView;
import com.example.lenovo.newclassmate.Activity.User_Activity_children;
import com.example.lenovo.newclassmate.R;
import com.example.lenovo.newclassmate.TestBean;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *关于个性标签的设置
 */
public class person_label_Activity extends AppCompatActivity {

    public static final int RESULT_CODE=123456;
    public static ArrayList<String> arrayList=new ArrayList<>();
    public static ArrayList<String> arrayList_bei=new ArrayList<>();
    public static  List<Integer> positions=new ArrayList<>();
    LabelsView labelsView;
    private String label_all_name="";
    private String studentId;
    private String schoolName;

    SharedPreferences preferences;//创建SharedPreferences保存登录学号和密码
    SharedPreferences.Editor editor; //editor对象向SharedPreferences写入数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview_item);
        labelsView =  findViewById(R.id.labels);
        preferences= getSharedPreferences("land", Context.MODE_PRIVATE);
        editor=preferences.edit();

        studentId=preferences.getString("studentId", null);
        schoolName=preferences.getString("schoolName",null);

        final ArrayList<TestBean> label = new ArrayList<>();
        label.add(new TestBean("K歌",1));
        label.add(new TestBean("轻音乐",2));
        label.add(new TestBean("汪星人",3));
        label.add(new TestBean("话痨",4));
        label.add(new TestBean("眼镜男",5));
        label.add(new TestBean("爱美剧",6));
        label.add(new TestBean("铲屎宫",7));
        label.add(new TestBean("选择恐惧症",8));
        label.add(new TestBean("果粉",9));
        label.add(new TestBean("宇宙第一帅",10));
        label.add(new TestBean("瑶湖第一虚",11));
        label.add(new TestBean("大叔",12));
        label.add(new TestBean("IT民工",13));
        label.add(new TestBean("篮球",14));
        label.add(new TestBean("民谣",15));
        label.add(new TestBean("减肥",16));
        label.add(new TestBean("翻山越岭只为吃",17));
        label.add(new TestBean("愤青",18));
        label.add(new TestBean("背包客",19));
        label.add(new TestBean("村上春树",20));
        labelsView.setLabels(label, new LabelsView.LabelTextProvider<TestBean>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, TestBean data) {
                return data.getName();
            }
        });
        labelsView.setSelects(positions);  //设置选中的标签

        //设置标题
        ((CommonTitleBar) findViewById(R.id.labels_title)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    onBackPressed();
                }
                else if(action==CommonTitleBar.ACTION_RIGHT_TEXT){
                    arrayList.clear();
                    List<TestBean> data=labelsView.getSelectLabelDatas();  //获取被选择的标签
                    Iterator<TestBean> iterable=data.iterator();
                    while(iterable.hasNext()){
                        TestBean t=iterable.next();
                        String s=t.getName();
                        arrayList.add(s);
                    }
                    positions=labelsView.getSelectLabels();  //获取选中的标签
                    for(int n=0;n<arrayList.size();n++)
                    {
                        label_all_name+=arrayList.get(n)+",";
                    }
                    ClientThread clientThread=new ClientThread(label_all_name);
                    new Thread(clientThread).start();
                }
            }
        });
    }

    //标签保存成功
    public void SaveLabelSuccess()
    {
        editor.putString("label",label_all_name);
        editor.commit();
//        Intent intent = getIntent();
//        setResult(RESULT_CODE,intent);
//        finish();
        startActivity(new Intent(person_label_Activity.this, User_Activity_children.class));
        finish();
    }
    //标签保存失败
    public void SaveLabelFalied()
    {
        Toast.makeText(getBaseContext(), "标签保存失败!", Toast.LENGTH_LONG).show();
    }

    //创建网络线程
    class ClientThread implements Runnable
    {
        String label;

        public ClientThread(String label) {
            this.label=label;
        }
        public void run()
        {
            InternetRequest(label);
        }

    }
    //创建http请求
    public void InternetRequest(final String label) {
        //请求地址
        String url = "http://47.107.48.62:8080/MyProject/DispatcherServlet";
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
                                SaveLabelSuccess();
                            } else {
                                SaveLabelFalied();
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
                params.put("Label",label);
                params.put("StudentId",studentId);
                params.put("SchoolName",schoolName);
                params.put("RequestType","saveLabel");
                return params;
            }
        };
        //设置Tag标签
        request.setTag(tag);
        //将请求添加到队列中
        requestQueue.add(request);
    }
}
