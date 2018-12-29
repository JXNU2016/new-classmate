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
import com.example.lenovo.newclassmate.Activity.LoginActivity;
import com.example.lenovo.newclassmate.R;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改昵称
 */

public class change_name_Activity extends Activity {
    public static final int RESULT_CODE=123;
    private EditText name;
    SharedPreferences preferences;//创建SharedPreferences保存登录学号和密码
    SharedPreferences.Editor editor; //editor对象向SharedPreferences写入数据
    private String changeName;
    private String studentId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        //获取只能被本地应用程序读写的SharedPreferences对象
        preferences= getSharedPreferences("land", Context.MODE_PRIVATE);
        editor=preferences.edit();
        studentId = preferences.getString("studentId", null);
        name = findViewById(R.id.name);

        //设置标题
        ((CommonTitleBar) findViewById(R.id.change_name_title)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    onBackPressed();
                }if(action==CommonTitleBar.ACTION_RIGHT_TEXT){
                    //将修改的昵称传回
                    changeName=name.getText().toString();
                    if (!validate(changeName))
                    {
                        Toast.makeText(getBaseContext(), "输入错误!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        ClientThread clientThread=new ClientThread(changeName,studentId);
                        new Thread(clientThread).start();
                    }
                }
            }
        });
    }

    //修改成功
    public void ChangeNameSuccess()
    {
        Intent intent=getIntent();
        intent.putExtra(PersonDataActivity.NAME,changeName);
        setResult(RESULT_CODE,intent);
        editor.putString("userName",changeName);
        editor.commit();
        finish();
    }
    //修改失败
    public void ChangeNameFalied()
    {
        Toast.makeText(getBaseContext(), "名称修改失败!", Toast.LENGTH_LONG).show();
    }


    //检查输入的合法性
    public boolean validate(String changeName) {
        boolean valid = true;

        //检查用户名
        if (changeName.length()>0) {
            if (changeName.length()>=2)
            {
                if (changeName.length()<10)
                {
                    name.setError(null);
                }
                else {
                    name.setError("昵称长度不能超过10位");
                    valid = false;
                }
            }
            else {
                name.setError("昵称长度不能小于2位");
                valid = false;
            }
        } else {
            name.setError("昵称不能为空");
            valid = false;
        }
        return valid;
    }


    //创建网络线程
    class ClientThread implements Runnable
    {
        String changeName;
        String studentId;

        public ClientThread(String changeName,String studentId) {
            this.changeName=changeName;
            this.studentId=studentId;
        }
        public void run()
        {
            InternetRequest(changeName,studentId);
        }

    }
    //创建http请求
    public void InternetRequest(final String changeName,final  String studentId) {
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
                                ChangeNameSuccess();
                            } else {
                                ChangeNameFalied();
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
                params.put("ChangeName",changeName);
                params.put("StudentId",studentId);
                params.put("RequestType","changeName");
                return params;
            }
        };
        //设置Tag标签
        request.setTag(tag);
        //将请求添加到队列中
        requestQueue.add(request);
    }

}
