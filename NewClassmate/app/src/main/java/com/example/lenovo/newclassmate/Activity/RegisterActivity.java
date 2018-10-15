package com.example.lenovo.newclassmate.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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

import com.example.lenovo.newclassmate.R;
import com.example.lenovo.newclassmate.suggest.AndroidBug5497Workaround;
import com.example.pickerview.PickerView;

import java.util.Calendar;
import java.util.HashMap;
import com.example.pickerview.entity.PickerData;
import com.example.pickerview.listener.OnPickerClickListener;
import com.example.pickerview.widge.CommonTitleBar;


public class RegisterActivity extends BaseActivity {

    private static final String TAG = "SignupActivity";

@BindView(R.id.input_mobile) EditText mPhone ;
@BindView(R.id.input_password) EditText mPsaaword;
@BindView(R.id.input_reEnterPassword) EditText mRePassWord;
@BindView(R.id.link_login) TextView mloginLink;
@BindView(R.id.btn_signup)Button mbtn_signup;
@BindView(R.id.text_address) EditText mTextAddress;
@BindView(R.id.input_name)  EditText mInput_name;
@BindView(R.id.input_time) EditText mInput_time;
@BindView(R.id.scroll_register) ScrollView mSc;
    PickerView pickerView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        initProvinceDatas();
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content));
        mbtn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        mTextAddress.setFocusable(false);//让EditText失去焦点，然后获取点击事件
        mInput_time.setFocusable(false);
        mloginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //实现手势侧滑
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });


        //选择器数据实体类封装 ,设置pickerview
        PickerData data=new PickerData();
        //设置数据，有多少层级自己确定
        data.setFirstDatas(mProvinceDatas);
        data.setSecondDatas(mCitisDatasMap);
        data.setThirdDatas(mDistrictDatasMap);
        data.setFourthDatas(new HashMap<String, String[]>());
        //设置初始化默认显示的三级菜单(此方法可以选择传参数量1到4个)
//        data.setInitSelectText("河北省","石家庄市","平山县");
        //初始化选择器
        pickerView=new PickerView(this,data);
        mTextAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示选择器
                pickerView.show(mTextAddress);
            }
        });
        //选择器完成三级选择后点击回调
        pickerView.setOnPickerClickListener(new OnPickerClickListener() {
            //选择列表时触发的事件
            @Override
            public void OnPickerClick(PickerData pickerData) {
                //想获取单个选择项 PickerData内也有方法（弹出框手动关闭）
                mTextAddress.setText(pickerData.getSelectText());
                pickerView.dismiss();//关闭选择器
            }
            //点击确定按钮触发的事件（自动关闭）
            @Override
            public void OnPickerConfirmClick(PickerData pickerData) {
                mTextAddress.setText(pickerData.getSelectText());
            }
        });

        ((CommonTitleBar) findViewById(R.id.titlebar_register)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    onBackPressed();
                }
            }
        });
//        ((CommonTitleBar) findViewById(R.id.titlebar_register)).showCenterProgress();

        //选择出生年月日
        mInput_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c=Calendar.getInstance();
                new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                       mInput_time.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                },c.get(Calendar.YEAR)
                 ,c.get(Calendar.MONTH)
                 ,c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        mbtn_signup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        mbtn_signup.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "注册失败", Toast.LENGTH_LONG).show();

        mbtn_signup.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String mobile = mPhone.getText().toString();
        String password = mPsaaword.getText().toString();
        String reEnterPassword = mRePassWord.getText().toString();

        if (mobile.isEmpty() || mobile.length()!=11) {
            mPhone.setError("请输入有效的电话号码");
            valid = false;
        } else {
            mPhone.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 ) {
            mPsaaword.setError("密码不能为空或者小于四位");
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
}
