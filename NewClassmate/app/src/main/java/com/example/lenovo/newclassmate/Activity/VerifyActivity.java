package com.example.lenovo.newclassmate.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.Spinner;

import com.example.lenovo.newclassmate.R;
import com.example.pickerview.widge.CommonTitleBar;

public class VerifyActivity extends Activity {

    private Button mNext;
    private Spinner spinner;


    static  String[] str=new String[]{
      "江西师范大学",
      "江西科技大学",
      "江西财经大学"
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_verify);
        mNext = findViewById(R.id.btn_next);
        spinner = findViewById(R.id.spiner_textview_Verify);
        spinner.setDropDownVerticalOffset(100);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,str);
        spinner.setAdapter(adapter);
        spinner.setSelection(0,false);
        mNext.setOnClickListener(new View.OnClickListener() {
        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(VerifyActivity.this,RegisterActivity.class));
                      }
                 });

        ((CommonTitleBar) findViewById(R.id.titlebar)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    onBackPressed();
                }
            }
        });
//        ((CommonTitleBar) findViewById(R.id.titlebar)).showCenterProgress();

        }

}
