package com.example.lenovo.newclassmate.Activity.UserActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.newclassmate.R;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

/**
 * Created by lenovo on 2018/11/12.
 */

public class change_passwordActivity extends Activity {


    private EditText old_password;
    private EditText new_password;
    private EditText confirm_password;

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
        //设置标题
        ((CommonTitleBar) findViewById(R.id.change_password_title)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    onBackPressed();
                }if(action==CommonTitleBar.ACTION_RIGHT_TEXT){
                    String oldpassword=old_password.getText().toString();
                    String newpassword=new_password.getText().toString();
                    String confirmpassword=confirm_password.getText().toString();

                    ///后续操作
                }
            }
        });
    }
}
