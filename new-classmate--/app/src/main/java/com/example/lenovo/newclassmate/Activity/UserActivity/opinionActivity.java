package com.example.lenovo.newclassmate.Activity.UserActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.newclassmate.Activity.User_Activity_children;
import com.example.lenovo.newclassmate.R;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

/**
 * Created by lenovo on 2018/11/12.
 */

public class opinionActivity extends Activity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opinion_send);
        //设置标题
        ((CommonTitleBar) findViewById(R.id.opinion_send_title)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    onBackPressed();
                }if(action==CommonTitleBar.ACTION_RIGHT_TEXT){
                    Toast.makeText(opinionActivity.this, "提交成功", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//      int id=v.getId();
//  switch (id){
//      case R.id.opinion_back_textView:
//          finish();
//          break;
//      case R.id.opinion_send:
//          Toast.makeText(opinionActivity.this, "提交成功", Toast.LENGTH_LONG).show();
//          finish();
//  }
//
//    }
}
