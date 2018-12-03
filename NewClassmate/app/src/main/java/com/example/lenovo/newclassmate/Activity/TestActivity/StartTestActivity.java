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

    private final String toNext = "startJumpToNext";
    private final String toPre = "startJumpToPrevious";
    public static List<Integer> scoreList = new LinkedList<>();
    private ViewPager viewPager;
    private QuestionItemAdapter questionItemAdapter;
    private int index = 0;
    private LoadQuestionDataDao loadQuestionDataDao;
    private BroadcastReceiver broadcastReceiver;
    public LocalBroadcastManager localBroadcastManager;
    public static List<QuestionBean> questionBeanList;

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
                if (intent.getAction().equals(toNext)) {
                    int score = intent.getIntExtra("optionValue", 0);
                    scoreList.add(score);
                    NextPage();
                } else if (intent.getAction().equals(toPre)) {
                    scoreList.remove(scoreList.size() - 1);
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
