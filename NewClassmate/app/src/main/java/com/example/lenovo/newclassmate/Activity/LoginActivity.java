package com.example.lenovo.newclassmate.Activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ExpandableListActivity;
import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.lenovo.newclassmate.R;
import com.example.pickerview.widge.CommonTitleBar;
import com.example.lenovo.newclassmate.suggest.*;
/*
登录界面Activity

所有注释代码是我在调试使用的，可忽略 可以点击减号隐藏
 */
public class LoginActivity extends AppCompatActivity{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_number) EditText mInput_number;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;
    @BindView(R.id.spiner_textview)Spinner mSpiner_textView;
    @BindView(R.id.logo) ImageView mLogo;
    @BindView(R.id.scrollView_login) ScrollView mScrollView;
    @BindView(R.id.content) View mContent;
    @BindView(R.id.root) View mLayout;
    private int screenHeight = 0;//屏幕高度

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //initListener();
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content)); //修复沉浸式状态栏所带来的adjustResize属性所带来的失效问题

        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,VerifyActivity.str);
        mSpiner_textView.setAdapter(adapter);
        mSpiner_textView.setDropDownVerticalOffset(100);//设置Spiner向下偏移量
       getSupportActionBar().hide(); //隐藏标题栏

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
                if(editable.toString().length()>0)
                    _loginButton.setEnabled(true);
            }
        };
        mInput_number.addTextChangedListener(watcher);

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
            onLoginFailed();
            return;
        }
        else if(validate())
        {
            onLoginSuccess();
            return;
        }

    }

    //登录成功
    public void onLoginSuccess() {
        Toast.makeText(getBaseContext(), "登录成功", Toast.LENGTH_LONG).show();
    }
    //登录失败
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "登录失败", Toast.LENGTH_LONG).show();
    }

    //判断输入的合法性
    public boolean validate() {
        boolean valid = true;

        String number = mInput_number.getText().toString();
        String password = _passwordText.getText().toString();

        if (number.isEmpty() || number.length()<10) {
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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }
}
