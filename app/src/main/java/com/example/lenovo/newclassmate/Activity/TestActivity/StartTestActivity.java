package com.example.lenovo.newclassmate.Activity.TestActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;


import com.example.lenovo.newclassmate.Adapter.QuestionItemAdapter;
import com.example.lenovo.newclassmate.Bean.QuestionBean;
import com.example.lenovo.newclassmate.DAO.LoadQuestionDataDao;
import com.example.lenovo.newclassmate.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Eskii
 *         分寝测试Activity
 */

public class StartTestActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

    private final String toNext = "startJumpToNext";    //跳到下一页的信号量
    private final String toPre = "startJumpToPrevious"; //跳到上一页的信号量
    public static List<Integer> scoreList = new LinkedList<>(); //获取每一题分数的分数队列
    private ViewPager viewPager;
    private QuestionItemAdapter questionItemAdapter;    //问题的适配器
    private int index = 0;
    private LoadQuestionDataDao loadQuestionDataDao;    //加载问题的方法类
    private BroadcastReceiver broadcastReceiver;    //广播接收器
    public LocalBroadcastManager localBroadcastManager; //广播经理 QAQ
    public static List<QuestionBean> questionBeanList;  //问题豆队列 QAQ

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.test_model);

        initData();
        initView();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(toNext)) {    //如果是跳到下一页
                    int score = intent.getIntExtra("optionValue", 0);   //获取当前问题的得分
                    scoreList.add(score);   //把分数放入分数队列
                    NextPage(); //跳到下一页
                } else if (intent.getAction().equals(toPre)) {  //如果是跳到上一页
//                    scoreList.remove(scoreList.size() - 1); //应该不用把分数吐出来，因为按下上一题的按钮时都没有把分数加进去
                    PreviousPage();
                }
            }
        };
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(toNext);
        filter.addAction(toPre);
        localBroadcastManager.registerReceiver(broadcastReceiver, filter);
    }

    /**
     * 初始化题目的数据
     */
    private void initData(){
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
        questionItemAdapter = new QuestionItemAdapter(getSupportFragmentManager());
        questionItemAdapter.setLength(questionBeanList.size());
        questionItemAdapter.setTag("start");    //设置当前activity的标签
        viewPager = findViewById(R.id.viewPager);
        viewPager.setCurrentItem(index);    //设置当前页数下标
        viewPager.setAdapter(questionItemAdapter);
        viewPager.setOnPageChangeListener(this);
    }

    /**
     * 跳转下一页
     */
    private void NextPage() {
        int position = viewPager.getCurrentItem();  //获取当前碎片下标
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
        if(localBroadcastManager != null) {
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
