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
 * 好友推荐测试
 */

public class FriendTestActivity extends FragmentActivity implements ViewPager.OnPageChangeListener{

    private final String toNext = "friendJumpToNext";
    private final String toPre = "friendJumpToPrevious";
    public static List<String> choiceList = new LinkedList<>();
    private ViewPager viewPager;
    private QuestionItemAdapter questionItemAdapter;
    private int index;
    private LoadQuestionDataDao loadQuestionDataDao;
    private BroadcastReceiver broadcastReceiver;
    public LocalBroadcastManager localBroadcastManager;
    public static List<QuestionBean> questionBeanList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_model);
        initData();
        initView();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(toNext)) {
                    String choice = intent.getStringExtra("optionValue");
                    choiceList.add(choice);
                    NextPage();
                } else if (intent.getAction().equals(toPre)) {
//                    choiceList.remove(choiceList.size() - 1);
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

    private void initData(){
        AssetManager assetManager = this.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("friendtest");
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadQuestionDataDao = new LoadQuestionDataDao(inputStream);
        questionBeanList = loadQuestionDataDao.setQuestionBeanList();
    }
    private void initView() {
        questionItemAdapter = new QuestionItemAdapter(getSupportFragmentManager());
        questionItemAdapter.setLength(questionBeanList.size());
        questionItemAdapter.setTag("friend");
        viewPager = findViewById(R.id.viewPager);
        viewPager.setCurrentItem(0);
        viewPager.setAdapter(questionItemAdapter);
        viewPager.setOnPageChangeListener(this);
    }


    private void NextPage() {
        int position = viewPager.getCurrentItem();  //获取当前碎片下标
        viewPager.setCurrentItem(position + 1);   //下标加一，实现跳转下一页
    }

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
