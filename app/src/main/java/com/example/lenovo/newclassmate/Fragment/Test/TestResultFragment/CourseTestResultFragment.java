package com.example.lenovo.newclassmate.Fragment.Test.TestResultFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.newclassmate.Activity.TestActivity.CourseTestActivity;
import com.example.lenovo.newclassmate.Activity.TestActivity.FriendTestActivity;
import com.example.lenovo.newclassmate.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CourseTestResultFragment extends Fragment {
    private Button button;
    private TextView textView;
    private List<String> choice;
    private String s;
    private String studentId;
    private String schoolName;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.coursetextresult,container,false);

        choice = CourseTestActivity.choiceList;

        schoolName=((CourseTestActivity)getActivity()).getSchoolName();
        studentId=((CourseTestActivity)getActivity()).getStudentId();

        button = view.findViewById(R.id.btn1);
        textView=view.findViewById(R.id.tv2);

        for(int n=0;n<choice.size();n++)
        {
            s+=choice.get(n)+",";
        }
        ClientThread clientThread=new ClientThread(s);
        new Thread(clientThread).start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }

    //数据上传失败
    public void SaveDataFalied()
    {
        textView.setText("数据提交失败，请检查网络是否良好，或者重新测试！");
    }

    //创建网络线程
    class ClientThread implements Runnable
    {
        String s;

        public ClientThread(String s) {
            this.s=s;
        }
        public void run()
        {
            InternetRequest(s);
        }

    }
    //创建http请求
    public void InternetRequest(final String s) {
        //请求地址
        String url = "http://47.107.48.62:8080/MyProject/SavaTestDataServlet";
        String tag = "zszszs";

        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

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
                            if (!result.equals("success")) {
                                SaveDataFalied();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_LONG).show();
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "服务器繁忙，请稍后再试！", Toast.LENGTH_LONG).show();
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Data",s);
                params.put("StudentId",studentId);
                params.put("SchoolName",schoolName);
                params.put("RequestType","SavaTestData");
                params.put("RequestType1","Course");
                return params;
            }
        };
        //设置Tag标签
        request.setTag(tag);
        //将请求添加到队列中
        requestQueue.add(request);
    }

}
