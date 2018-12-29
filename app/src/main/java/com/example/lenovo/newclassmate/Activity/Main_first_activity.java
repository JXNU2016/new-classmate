package com.example.lenovo.newclassmate.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.example.lenovo.newclassmate.AllActivity;
import com.example.lenovo.newclassmate.MainActivity;
import com.example.lenovo.newclassmate.R;

/**
 * Created by lenovo on 2018/12/21.
 */

public class Main_first_activity extends Activity {

    private Handler handler=new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_first);
        AllActivity.getInstance().addActivity(this);   //添加此Activity到容器内
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  //充满全屏
    handler.postDelayed(new Runnable() {
    @Override
    public void run() {
        gotoLoginRegister();
       }
     },800);
    }
    /**
     * 前往注册、登录主页
     */
    private void gotoLoginRegister() {
        Intent intent = new Intent(Main_first_activity.this, MainActivity.class);
        startActivity(intent);
        finish();
        //取消界面跳转时的动画，使启动页的logo图片与注册、登录主页的logo图片完美衔接
        overridePendingTransition(0, 0);
    }

    /*
    屏蔽物理键盘
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy(){
        if (handler != null) {      //移除handler的所有消息，避免内存泄漏。参数为null时,删除所有回调函数和message
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
}
