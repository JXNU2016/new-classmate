package com.example.lenovo.newclassmate;

import android.app.ActivityManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.newclassmate.Activity.BaseActivity;
import com.example.lenovo.newclassmate.Activity.ChatFragment;
import com.example.lenovo.newclassmate.Activity.HomeFragment;
import com.example.lenovo.newclassmate.Activity.TestFragment;
import com.example.lenovo.newclassmate.Activity.UserFragment;
import com.gyf.barlibrary.ImmersionBar;
import com.ycl.tabview.library.TabView;
import com.ycl.tabview.library.TabViewChild;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/*
底部导航栏启动页
 */
public class StartActivity extends FragmentActivity {
    TabView tabView;
    Fragment homeActivity;
    Fragment testActivity;
    Fragment chatActivity;
    Fragment userActivity;
    private long firstTime = 0;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ImmersionBar.with(this).init();


        tabView = findViewById(R.id.tabView);
        homeActivity = new HomeFragment();
        testActivity = new TestFragment();
        chatActivity = new ChatFragment();
        userActivity = new UserFragment();

        //绑定底部导航子控件（获得焦点图标，未获得焦点图标，目标Fragment对象）
        List<TabViewChild> tabViewChildList = new ArrayList<>();
        TabViewChild tabViewChild01 = new TabViewChild(R.drawable.home2, R.drawable.home1, "首页", homeActivity);
        TabViewChild tabViewChild02 = new TabViewChild(R.drawable.test2, R.drawable.test1, "测试", testActivity);
        TabViewChild tabViewChild03 = new TabViewChild(R.drawable.chat2, R.drawable.chat1, "消息", chatActivity);
        TabViewChild tabViewChild04 = new TabViewChild(R.drawable.user2, R.drawable.user1, "我的", userActivity);
        tabViewChildList.add(tabViewChild01);
        tabViewChildList.add(tabViewChild02);
        tabViewChildList.add(tabViewChild03);
        tabViewChildList.add(tabViewChild04);

        tabView.setTextViewSelectedColor(Color.parseColor("#2CA6E0"));  //设置获得焦点文字颜色
        tabView.setTabViewDefaultPosition(0);      //设置启动页面
        tabView.setImageViewHeight(100);         //设置图片高度
        tabView.setTextViewSize(10);
        tabView.setTabViewChild(tabViewChildList, getSupportFragmentManager());
        tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int position, ImageView currentImageIcon, TextView currentTextView) {  //绑定监听器

            }
        });
    }

    //双击返回退出程序
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(StartActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                AllActivity.getInstance().exit();
                finish();
            }
        }

        return super.onKeyUp(keyCode, event);
    }
}