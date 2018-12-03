package com.example.lenovo.newclassmate.Activity.UserActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.lenovo.newclassmate.R;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

/**
 * 关于我们
 */

public class about_usActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        //设置标题
        ((CommonTitleBar) findViewById(R.id.about_us_text)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    onBackPressed();
                }
            }
        });
    }
}
