package com.example.lenovo.newclassmate.Bean;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.newclassmate.Adapter.MyExpandableListViewAdapter;
import com.example.lenovo.newclassmate.R;

import java.util.ArrayList;
import java.util.List;

public class Club extends Activity {

    private ExpandableListView listView;
    private MyExpandableListViewAdapter adapter;
    List<List<String>> list = new ArrayList<>();//创建两个列表，一个用于存放数组，另一个用于存放含有数组的列表
    List<Group> groups = new ArrayList<>();
    Button button;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club);

        button = findViewById(R.id.imagbtu_3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        initview();
    }

    private void initview() {

        listView = findViewById(R.id.st_view);


       // groups = getGroups();

        String[] s1 = {"青年文学社", "演讲与辩论", "日语社", "书法社"};
        String[] s2 = {"天文社"};
        String[] s3 = {"VDS舞蹈社"};
        String[] s4 = {"蓝天环保社团"};
        List<String> list1 = new ArrayList<>();
        for (String s : s1) {
            list1.add(s);
        }
        List<String> list2 = new ArrayList<>();
        for (String s : s2) {
            list2.add(s);
        }
        List<String> list3 = new ArrayList<>();
        for (String s : s3) {
            list3.add(s);
        }
        List<String> list4 = new ArrayList<>();
        for (String s : s4) {
            list4.add(s);
        }
        list.add(list1);
        list.add(list2);
        list.add(list3);
        list.add(list4);

        groups.add(new Group(R.drawable.learn,"理论学习类社团",R.drawable.downacross));
        groups.add(new Group(R.drawable.science,"科学教育类社团",R.drawable.downacross));
        groups.add(new Group(R.drawable.hobby,"兴趣爱好类社团",R.drawable.downacross));
        groups.add(new Group(R.drawable.commonweal,"社会公益类社团",R.drawable.downacross));
        adapter = new MyExpandableListViewAdapter(this, list,groups);
        listView.setAdapter(adapter);
    }


    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.club_alertdialog, null);//获取自定义布局
        builder.setView(layout);
        builder.setIcon(R.drawable.xclub);//设置标题图标
        builder.setTitle(R.string.clubname);//设置标题内容
        // builder.setMessage("");//显示自定义布局内容
        final TextView textView = (TextView) layout.findViewById(R.id.club_adtv1);


        //确认按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplication(), "快去看看有哪些社团吧", Toast.LENGTH_SHORT).show();
            }
        });

        //取消
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
            }
        });
        final AlertDialog dlg = builder.create();
        dlg.show();
    }





}


