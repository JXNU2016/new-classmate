package com.example.lenovo.newclassmate.Activity.UserActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.example.lenovo.newclassmate.Activity.User_Activity_children;
import com.example.lenovo.newclassmate.R;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

/**
 * 个性签名设置
 */

public class Person_signature_Activity extends Activity {
    public static final int RESULT_CODE=456;

    private EditText signature;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_signature);
        signature = findViewById(R.id.person_signature_edit);
        ((CommonTitleBar) findViewById(R.id.person_signature_title)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    onBackPressed();
                }if(action==CommonTitleBar.ACTION_RIGHT_TEXT){
                    //个性签名
                    String s=signature.getText().toString();
                    Intent intent=getIntent();
                    intent.putExtra(PersonDataActivity.SIGNATURE,s);
                    setResult(RESULT_CODE,intent);
                    User_Activity_children.Person_signature=s;
                    finish();
                }
            }
        });

    }
}
