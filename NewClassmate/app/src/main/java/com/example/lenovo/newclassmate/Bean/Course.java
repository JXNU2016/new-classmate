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

import com.example.lenovo.newclassmate.Adapter.MyExpandableAdapter;
import com.example.lenovo.newclassmate.R;

import java.util.ArrayList;
import java.util.List;

public class Course extends Activity {

    private ExpandableListView expandable;
    private MyExpandableAdapter myExpandableAdapter;
    List<List<String>> list = new ArrayList<>();
    List<Group> groups = new ArrayList<>();
    private TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course);

        button = (Button) findViewById(R.id.imagbtu_2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        initView();

    }

    //初始化ExpandableListView控件与Adapter
    private void initView() {
        //每一个二级列表的内容都用一个数组存放
        String[] s1 = {"政治学", "西藏与旅游", "历史学", "人际心理学", "音乐治疗", "风水环境与地理", "电影鉴赏"};
        String[] s2 = {"生物学", "化学工艺", "天文", "工艺品"};
        String[] s3 = {"唱歌与发音", "乒乓球", "三人篮球", "排球"};
        String[] s4 = {"游泳与健身", "乒乓球", "三人篮球", "棒球", "网球"};
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

        groups.add(new Group(R.drawable.humanity, "社会人文科学类", R.drawable.downacross));
        groups.add(new Group(R.drawable.technology, "科学技术类", R.drawable.downacross));
        groups.add(new Group(R.drawable.art, "音乐艺术类", R.drawable.downacross));
        groups.add(new Group(R.drawable.sports, "体育运动类", R.drawable.downacross));
        expandable = findViewById(R.id.expandableListView2);
        myExpandableAdapter = new MyExpandableAdapter(this, list, groups);
        expandable.setAdapter(myExpandableAdapter);
    }


    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.couse_alertdialog, null);//获取自定义布局
        builder.setView(layout);
        builder.setIcon(R.drawable.xcourse);//设置标题图标
        builder.setTitle(R.string.coursename);//设置标题内容
        // builder.setMessage("");//显示自定义布局内容
        final TextView textView = (TextView) layout.findViewById(R.id.course_adtv2);


        //确认按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplication(), "快看看有哪些选修课吧", Toast.LENGTH_SHORT).show();
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



