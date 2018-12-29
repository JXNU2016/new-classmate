package com.example.lenovo.newclassmate.Activity.UserActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenovo.newclassmate.Activity.LoginActivity;
import com.example.lenovo.newclassmate.Activity.Main_first_activity;
import com.example.lenovo.newclassmate.AllActivity;
import com.example.lenovo.newclassmate.MainActivity;
import com.example.lenovo.newclassmate.R;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

/**
 * 个人设置界面
 */

public class settingActivity extends Activity implements View.OnClickListener {


    private View change_password;
    private View clean_cache;
    private View abous_us;
    private Button exit;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor; //editor对象向SharedPreferences写入数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        AllActivity.getInstance().addActivity(this);
        change_password = findViewById(R.id.change_password_layout);
        clean_cache = findViewById(R.id.clean_cache);
        abous_us = findViewById(R.id.about_us_layout);
        exit = findViewById(R.id.exit_user);
        preferences=getSharedPreferences("land", Context.MODE_PRIVATE);
        editor=preferences.edit();


        change_password.setOnClickListener(this);
        clean_cache.setOnClickListener(this);
        abous_us.setOnClickListener(this);
        exit.setOnClickListener(this);


        //设置标题
        ((CommonTitleBar) findViewById(R.id.setting_title)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.change_password_layout:
                startActivity(new Intent(settingActivity.this,change_passwordActivity.class));
                break;
            case R.id.clean_cache:
                Toast.makeText(settingActivity.this, "清除缓存成功", Toast.LENGTH_LONG).show();
                break;
            case R.id.about_us_layout:
                startActivity(new Intent(settingActivity.this,about_usActivity.class));
                break;
            case R.id.exit_user:
                editor.clear();
                editor.commit();
                startActivity(new Intent(settingActivity.this,MainActivity.class));
                finish();
                break;
        }
    }
}
