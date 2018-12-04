package com.example.lenovo.newclassmate.Activity.UserActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import com.example.lenovo.newclassmate.R;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

/**
 * 修改昵称
 */

public class change_name_Activity extends Activity {
    public static final int RESULT_CODE=123;
    private EditText name;
    SharedPreferences preferences;//创建SharedPreferences保存登录学号和密码
    SharedPreferences.Editor editor; //editor对象向SharedPreferences写入数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        //获取只能被本地应用程序读写的SharedPreferences对象
        preferences= getSharedPreferences("land", Context.MODE_PRIVATE);
        editor=preferences.edit();

        name = findViewById(R.id.name);

        //设置标题
        ((CommonTitleBar) findViewById(R.id.change_name_title)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    onBackPressed();
                }if(action==CommonTitleBar.ACTION_RIGHT_TEXT){
                    //将修改的昵称传回
                    String changeName=name.getText().toString();
                    Intent intent=getIntent();
                    intent.putExtra(PersonDataActivity.NAME,changeName);
                    setResult(RESULT_CODE,intent);
                    editor.putString("userName",changeName);
                    editor.commit();
                    finish();
                }
            }
        });

    }
}
