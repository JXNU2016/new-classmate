package com.example.lenovo.newclassmate.Activity.UserActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.newclassmate.Activity.User_Activity_children;
import com.example.lenovo.newclassmate.AllActivity;
import com.example.lenovo.newclassmate.R;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 个性签名设置
 */

public class Person_signature_Activity extends Activity {
    public static final int RESULT_CODE=456;

    private EditText signature;
    private SharedPreferences preferences;
    SharedPreferences.Editor editor; //editor对象向SharedPreferences写入数据
    private String s;
    private String studentId;
    private String schoolName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_signature);

        preferences=getSharedPreferences("land", Context.MODE_PRIVATE);
        editor=preferences.edit();
        signature = findViewById(R.id.person_signature_edit);
        signature.setText(preferences.getString("signature",null));
        studentId=preferences.getString("studentId", null);
        schoolName=preferences.getString("schoolName",null);
        AllActivity.getInstance().addActivity(this);
        ((CommonTitleBar) findViewById(R.id.person_signature_title)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
                }if(action==CommonTitleBar.ACTION_RIGHT_TEXT){
                    //个性签名
                    s=signature.getText().toString();
                    if(s.length()>15)
                    {
                        Toast.makeText(getBaseContext(), "长度不能超过15个字符！", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        ClientThread clientThread=new ClientThread(s,studentId,schoolName);
                        new Thread(clientThread).start();
                    }
                }
            }
        });
    }


    //修改成功
    public void ChangeSignatureSuccess()
    {
        Intent intent=getIntent();
        intent.putExtra(PersonDataActivity.SIGNATURE,s);
        setResult(RESULT_CODE,intent);
        editor.putString("signature",s);
        editor.commit();
        finish();
    }
    //修改失败
    public void ChangeSignatureFalied()
    {
        Toast.makeText(getBaseContext(), "个性签名修改失败!", Toast.LENGTH_LONG).show();
    }


    //创建网络线程
    class ClientThread implements Runnable
    {
        String s;
        String studentId;
        String schoolName;

        public ClientThread(String s,String studentId,String schoolName) {
            this.s=s;
            this.studentId=studentId;
            this.schoolName=schoolName;
        }
        public void run()
        {
            InternetRequest(s,studentId,schoolName);
        }

    }
    //创建http请求
    public void InternetRequest(final String changeName,final  String studentId,final  String schoolName) {
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
                                ChangeSignatureSuccess();
                            } else {
                                ChangeSignatureFalied();
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
                params.put("Signature",s);
                params.put("StudentId",studentId);
                params.put("SchoolName",schoolName);
                params.put("RequestType","ChangeSignature");
                return params;
            }
        };
        //设置Tag标签
        request.setTag(tag);
        //将请求添加到队列中
        requestQueue.add(request);
    }
}
