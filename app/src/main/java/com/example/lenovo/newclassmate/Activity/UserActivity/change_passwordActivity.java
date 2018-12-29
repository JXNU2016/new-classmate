package com.example.lenovo.newclassmate.Activity.UserActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.newclassmate.R;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改密码
 */

public class change_passwordActivity extends Activity {


    private EditText old_password;
    private EditText new_password;
    private EditText confirm_password;
    private Button confirm;
    SharedPreferences preferences;//创建SharedPreferences保存登录学号和密码
    SharedPreferences.Editor editor; //editor对象向SharedPreferences写入数据
    private String studentId=null;
    private String newPassword=null;
    private String schoolName=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passsword);
        //旧密码
        old_password = findViewById(R.id.old_password);
        //新密码
        new_password = findViewById(R.id.new_password);
        //确认密码
        confirm_password = findViewById(R.id.confirm_password);
        //确认按钮
        confirm = findViewById(R.id.exit_user);
        //绑定确认按钮监听器
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = old_password.getText().toString();
                newPassword = new_password.getText().toString();
                String confirmPassword = confirm_password.getText().toString();
                preferences = getSharedPreferences("land", Context.MODE_PRIVATE);
                editor = preferences.edit();
                studentId = preferences.getString("studentId", null);
                schoolName = preferences.getString("schoolName",null);
                if (!validate(newPassword, confirmPassword))    //两次密码不匹配或者新密码输入错误
                {
                    Toast.makeText(getBaseContext(), "输入错误!", Toast.LENGTH_LONG).show();
                } else {
                    verifyOldpassword(oldPassword, studentId);   //匹配旧密码
                }
            }
        });


        //设置标题
        ((CommonTitleBar) findViewById(R.id.change_password_title)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) { onBackPressed(); }
            }
        });
    }


    //检查输入的合法性
    public boolean validate(String newPassword,String confirmPassword) {
        boolean valid = true;

        //检查密码
        if (newPassword.isEmpty() || newPassword.length() < 6 ) {
            new_password.setError("密码不能为空或者小于六位");
            valid = false;
        } else {
            new_password.setError(null);
            if (confirmPassword.isEmpty() || confirmPassword.length() < 6  || !(confirmPassword.equals(newPassword))) {
                confirm_password.setError("两次输入密码不相同");
                valid = false;
            } else {
                confirm_password.setError(null);
            }
        }
        return valid;
    }


    //匹配旧密码是否正确
    public void verifyOldpassword(String oldPassword,String studentId)
    {
        ClientThread1 clientThread1=new ClientThread1(oldPassword,studentId);
        new Thread(clientThread1).start();
    }


    //旧密码输入正确
    public void oldPasswordRight()
    {
        ClientThread2 clientThread2=new ClientThread2(newPassword,studentId);
        new Thread(clientThread2).start();
    }
    //旧密码输入错误
    public void oldPasswordWrong()
    {
        old_password.setError("原密码输入错误！");
        Toast.makeText(getBaseContext(), "输入错误!", Toast.LENGTH_LONG).show();
    }


    //密码修改成功
    public void ChangePsswordSuccess()
    {
        editor.putString("password",newPassword);
        editor.commit();
        Intent intent = new Intent(change_passwordActivity.this, settingActivity.class);
        startActivity(intent);
        finish();
    }
    //密码修改失败
    public void ChangePasswordFalse()
    {
        Toast.makeText(getBaseContext(), "密码修改失败!", Toast.LENGTH_LONG).show();
    }


    //创建匹配旧密码的网络线程
    class ClientThread1 implements Runnable
    {
        String oldPassword;
        String studentId;

        public ClientThread1(String oldPassword,String studentId) {
            this.oldPassword=oldPassword;
            this.studentId=studentId;
        }
        public void run()
        {
            InternetRequest1(oldPassword,studentId);
        }

    }
    //创建匹配旧密码的http请求
    public void InternetRequest1(final String oldPassword,final  String studentId) {
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
                                oldPasswordRight();
                            } else {
                                oldPasswordWrong();
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
                params.put("OldPassword",oldPassword);
                params.put("StudentId",studentId);
                params.put("SchoolName",schoolName);
                params.put("RequestType","ChangePassword");
                params.put("RequestType1","VerifyOldpassword");
                return params;
            }
        };
        //设置Tag标签
        request.setTag(tag);
        //将请求添加到队列中
        requestQueue.add(request);
    }


    //创建修改密码的网络线程
    class ClientThread2 implements Runnable
    {
        String newPassword;
        String studentId;

        public ClientThread2(String newPassword,String studentId) {
            this.newPassword=newPassword;
            this.studentId=studentId;
        }
        public void run()
        {
            InternetRequest2(newPassword,studentId);
        }

    }
    //创建修改密码的http请求
    public void InternetRequest2(final String newPassword,final  String studentId) {
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
                                ChangePsswordSuccess();
                            } else {
                                ChangePasswordFalse();
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
                params.put("NewPassword",newPassword);
                params.put("StudentId",studentId);
                params.put("RequestType","ChangePassword");
                params.put("RequestType1","ChangePassword");
                return params;
            }
        };
        //设置Tag标签
        request.setTag(tag);
        //将请求添加到队列中
        requestQueue.add(request);
    }

}
