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
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

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
 * 社团推荐测试
 */

public class OrganizationTestActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private final String toNext = "organizationJumpToNext";
    private final String toPre = "organizationJumpToPrevious";
    public static List<String> choiceList = new LinkedList<>();
    private CustomViewPager viewPager;
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
            inputStream = assetManager.open("organizationtest");
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadQuestionDataDao = new LoadQuestionDataDao(inputStream);
        questionBeanList = loadQuestionDataDao.setQuestionBeanList();
//        if (choiceList != null){
//            for (String s:choiceList){
//                choiceList.remove(s);
//            }
//        }
    }
    private void initView() {
        questionItemAdapter = new QuestionItemAdapter(getSupportFragmentManager());
        questionItemAdapter.setLength(questionBeanList.size());
        questionItemAdapter.setTag("organization");
        viewPager = findViewById(R.id.viewPager);
        viewPager.setScanScroll(false); //设置页面不能滑动
        viewPager.setCurrentItem(index);
        viewPager.setAdapter(questionItemAdapter);   //viewPager 需要一个adapter 来管理
        viewPager.setOnPageChangeListener(this);
    }
    /**
     * 监听返回键，防止误碰返回键退出测试，触碰返回键弹出对话框
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
            AlertDialog.Builder dialog = new AlertDialog.Builder(OrganizationTestActivity.this);//新建一个对话框
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
        index = state - 1;
    }
}

