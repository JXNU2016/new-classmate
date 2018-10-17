package com.example.lenovo.newclassmate.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
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
import com.example.lenovo.newclassmate.MainActivity;
import com.example.lenovo.newclassmate.R;
import com.example.lenovo.newclassmate.suggest.AndroidBug5497Workaround;
import com.example.pickerview.PickerView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.example.pickerview.entity.PickerData;
import com.example.pickerview.listener.OnPickerClickListener;
import com.example.pickerview.widge.CommonTitleBar;

import org.json.JSONException;
import org.json.JSONObject;

/*
注册界面Activity
 */
public class RegisterActivity extends BaseActivity {

    private static final String TAG = "SignupActivity";
    private String schoolName=null;
    private String name=null;
    private String studentId=null;
    private String sex=null;
    private String password=null;
    private String year=null;
    private String month=null;
    private String day=null;
    private String province=null;
    private String city=null;
    private String county=null;
    private String phone=null;
    private String username=null;
    private ClientThread clientThread;

    @BindView(R.id.input_mobile) EditText mPhone ;
    @BindView(R.id.input_password) EditText mPsaaword;
    @BindView(R.id.input_reEnterPassword) EditText mRePassWord;
    @BindView(R.id.link_login) TextView mloginLink;
    @BindView(R.id.btn_signup)Button mbtn_signup;
    @BindView(R.id.text_address) EditText mTextAddress;
    @BindView(R.id.input_name)  EditText mInput_name;
    @BindView(R.id.input_time) EditText mInput_time;
    @BindView(R.id.scroll_register) ScrollView mSc;
    @BindView(R.id.school) TextView school;
    @BindView(R.id.userId) TextView userId;
    @BindView(R.id.userName) TextView userName;
    PickerView pickerView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = getIntent().getStringExtra("name");
        sex = getIntent().getStringExtra("sex");
        schoolName = getIntent().getStringExtra("schoolName");
        studentId = getIntent().getStringExtra("studentId");

        getSupportActionBar().hide();
        ButterKnife.bind(this);
        initProvinceDatas();
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content));

        //设置用户属性
        school.setText(schoolName);
        userId.setText(studentId);
        userName.setText(name);

        mTextAddress.setFocusable(false);//让EditText失去焦点，然后获取点击事件
        mInput_time.setFocusable(false);


        //为（注册）按钮绑定监听器
        mbtn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });


        //为（已有帐号？登录）按钮绑定监听器
        mloginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //实现手势侧滑
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });


        //设置选择地址模块
        PickerData data=new PickerData();       //选择器数据实体类封装 ,设置pickerview
        data.setFirstDatas(mProvinceDatas);     //设置数据，有多少层级自己确定
        data.setSecondDatas(mCitisDatasMap);
        data.setThirdDatas(mDistrictDatasMap);
        data.setFourthDatas(new HashMap<String, String[]>());
        pickerView=new PickerView(this,data);       //初始化选择器
        mTextAddress.setOnClickListener(new View.OnClickListener() { //设置选择地址监听事件
            @Override
            public void onClick(View v) {
                pickerView.show(mTextAddress);          //显示选择器
            }
        });
        pickerView.setOnPickerClickListener(new OnPickerClickListener() {       //选择器完成三级选择后点击回调
            @Override
            public void OnPickerClick(PickerData pickerData) {              //选择列表时触发的事件
                mTextAddress.setText(pickerData.getSelectText());           //想获取单个选择项 PickerData内也有方法（弹出框手动关闭）
                province=pickerData.getFirstText();
                city=pickerData.getSecondText();
                county=pickerData.getThirdText();
                pickerView.dismiss();//关闭选择器

            }
            @Override
            public void OnPickerConfirmClick(PickerData pickerData) {       //点击确定按钮触发的事件（自动关闭）
                mTextAddress.setText(pickerData.getSelectText());
                province=pickerData.getFirstText();
                city=pickerData.getSecondText();
                county=pickerData.getThirdText();
            }
        });


        //设置title标题栏
        ((CommonTitleBar) findViewById(R.id.titlebar_register)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    onBackPressed();
                }
            }
        });


        //设置选择出生年月日模块
        mInput_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c=Calendar.getInstance();
                new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        mInput_time.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        RegisterActivity.this.year=String.valueOf(year);
                        RegisterActivity.this.month=String.valueOf(month+1);
                        RegisterActivity.this.day=String.valueOf(dayOfMonth);
                    }
                },c.get(Calendar.YEAR)
                 ,c.get(Calendar.MONTH)
                 ,c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    //登录键触发函数
    public void signup() {
        Log.d(TAG, "Signup");

        //检测各个EditText的数据是否符合输入标准
        if (validate()) {
            clientThread=new ClientThread(schoolName,username,studentId,password,year,month,day,province,city,county,phone);     //注册失败
            new Thread(clientThread).start();
            return;
        }
        else if(!validate()){
            onSignupFailed();   //注册失败
            return;
        }
    }


    //注册成功
    public void onSignupSuccess() {
        Toast.makeText(getBaseContext(), "注册成功！", Toast.LENGTH_LONG).show();
        //页面跳转
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

    //注册失败
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "请检查输入内容！", Toast.LENGTH_LONG).show();
    }


    //检查输入的合法性
    public boolean validate() {
        boolean valid = true;

        username=mInput_name.getText().toString();
        phone = mPhone.getText().toString();
        password = mPsaaword.getText().toString();
        String reEnterPassword = mRePassWord.getText().toString();

        //检查电话
        if (phone.isEmpty() || phone.length()!=11) {
            mPhone.setError("请输入有效的电话号码");
            valid = false;
        } else {
            mPhone.setError(null);
        }

        //检查用户名
        if (username.length()>0) {
            if (username.length()>=2)
            {
                if (username.length()<10)
                {
                    mInput_name.setError(null);
                }
                else {
                    mInput_name.setError("昵称长度不能超过10位");
                    valid = false;
                }
            }
            else {
                mInput_name.setError("昵称长度不能小于2位");
                valid = false;
            }
        } else {
            mInput_name.setError("昵称不能为空");
            valid = false;
        }

        //检查地址
        if (county==null || city==null || province==null)
        {
            mTextAddress.setError("未选择地址");
            valid = false;
        }else{
            mTextAddress.setError(null);
        }

        //检查出生日期
        if (year==null || month==null || day==null)
        {
            mInput_time.setError("未选择出生日期");
            valid = false;
        }else{
            mInput_time.setError(null);
        }

        //检查密码
        if (password.isEmpty() || password.length() < 6 ) {
            mPsaaword.setError("密码不能为空或者小于六位");
            valid = false;
        } else {
            mPsaaword.setError(null);

            if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
                mRePassWord.setError("两次输入密码不相同");
                valid = false;
            } else {
                mRePassWord.setError(null);
            }
        }
        return valid;
    }


    //创建线程处理http请求
    class ClientThread implements Runnable
    {
        String schoolName=null;
        String userName=null;
        String studentId=null;
        String password=null;
        String year=null;
        String month=null;
        String day=null;
        String province=null;
        String city=null;
        String county=null;
        String phone=null;

        public ClientThread(String schoolName,String userName,String studentId,String password,String year,String month,String day,String province,String city,String county,String phone) {
            this.schoolName=schoolName;
            this.userName=userName;
            this.studentId=studentId;
            this.password=password;
            this.year=year;
            this.month=month;
            this.day=day;
            this.province=province;
            this.city=city;
            this.county=county;
            this.phone=phone;
        }
        public void run()
        {
            InternetRequest(schoolName,userName,studentId,password,year,month,day,province,city,county,phone);
        }
    }


    //创建http请求
    public void InternetRequest(final String schoolName, final String userName, final String studentId, final String password, final String year, final String month, final String day, final String province, final String city, final String county, final String phone) {
        //请求地址
        String url = "http://47.107.48.62:8080/MyProject/RegisterServlet";
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
                                onSignupSuccess();
                            } else {
                                onSignupFailed();
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
                params.put("RequestType","register");
                params.put("studentId",studentId);
                params.put("schoolName",schoolName);
                params.put("userName",userName);
                params.put("password",password);
                params.put("year",year);
                params.put("month",month);
                params.put("day",day);
                params.put("province",province);
                params.put("city",city);
                params.put("county",county);
                params.put("phone",phone);
                return params;
            }
        };
        //设置Tag标签
        request.setTag(tag);
        //将请求添加到队列中
        requestQueue.add(request);
    }
}