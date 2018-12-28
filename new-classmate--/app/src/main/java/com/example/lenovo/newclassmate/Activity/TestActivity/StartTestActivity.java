package com.example.lenovo.newclassmate.Activity.TestActivity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.WindowManager;


import com.example.lenovo.newclassmate.Adapter.QuestionItemAdapter;
import com.example.lenovo.newclassmate.Bean.QuestionBean;
import com.example.lenovo.newclassmate.DAO.CustomViewPager;
import com.example.lenovo.newclassmate.DAO.LoadQuestionDataDao;
import com.example.lenovo.newclassmate.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Eskii
 * 分寝测试Activity
 */

public class StartTestActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

    private final String toNext = "startJumpToNext";    //跳到下一页的信号量
    private final String toPre = "startJumpToPrevious"; //跳到上一页的信号量
    public static List<Integer> scoreList = new LinkedList<>(); //获取每一题分数的分数队列
    private CustomViewPager viewPager;
    private QuestionItemAdapter questionItemAdapter;    //问题的适配器
    private int index = 0;
    private LoadQuestionDataDao loadQuestionDataDao;    //加载问题的方法类
    private BroadcastReceiver broadcastReceiver;    //广播接收器
    public LocalBroadcastManager localBroadcastManager; //广播经理 QAQ
    public static List<QuestionBean> questionBeanList;  //问题队列

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.test_model);

        initData();
        initView();

        broadcastReceiver = new BroadcastReceiver() {    //建一个广播接收器
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(toNext)) {    //广播接收信号如果是跳到下一页
                    int score = intent.getIntExtra("optionValue", 0);   //获取广播中当前问题的得分
                    scoreList.add(score);   //把分数放入分数队列
                    NextPage(); //跳到下一页
                } else if (intent.getAction().equals(toPre)) {  //如果是跳到上一页
                    PreviousPage();
                }
            }
        };
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(toNext);                            //filter  是广播信号过滤器，把想要的广播加上去用于接收
        filter.addAction(toPre);
        localBroadcastManager.registerReceiver(broadcastReceiver, filter);
    }

    /**
     * 初始化题目的数据
     */
    private void initData() {
        AssetManager assetManager = this.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("firsttest");   //assets目录下的文件
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadQuestionDataDao = new LoadQuestionDataDao(inputStream); //解析资源文件
        questionBeanList = loadQuestionDataDao.setQuestionBeanList();   //获得题目队列
    }

    private void initView() {
        questionItemAdapter = new QuestionItemAdapter(getSupportFragmentManager()); //问题适配器获得一个支持管理fragment，自带的方法
        questionItemAdapter.setLength(questionBeanList.size());//questionBeanList.size()是总共有多少个问题
        questionItemAdapter.setTag("start");    //设置当前activity的标签
        viewPager = findViewById(R.id.viewPager);
        viewPager.setScanScroll(false);
        viewPager.setCurrentItem(index);    //设置当前页数下标
        viewPager.setAdapter(questionItemAdapter);
        viewPager.setOnPageChangeListener(this); //设置页面改变监听
    }

    /**
     * 监听返回键，防止误碰返回键退出测试，触碰返回键弹出对话框
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            AlertDialog.Builder dialog = new AlertDialog.Builder(StartTestActivity.this);//新建一个对话框
            dialog.setMessage("确定要退出测试吗?");//设置提示信息
            //设置确定按钮并监听
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();//结束当前Activity
                }
            });
            //设置取消按钮并监听
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //这里什么也不用做
                }
            });
            dialog.show();//最后不要忘记把对话框显示出来
        }
        return false;
    }

    /**
     * 跳转下一页
     */
    private void NextPage() {
        int position = viewPager.getCurrentItem();  //获取当前碎片下标 getCurrentItem 是viewPager获取当前下标的方法
        viewPager.setCurrentItem(position + 1);   //下标加一，实现跳转下一页
    }

    /**
     * 跳转上一页
     */
    private void PreviousPage() {
        int position = viewPager.getCurrentItem();  //获取当前碎片下标
        viewPager.setCurrentItem(position - 1);   //下标减一，实现跳转上一页
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (localBroadcastManager != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        index = state;
    }
}
