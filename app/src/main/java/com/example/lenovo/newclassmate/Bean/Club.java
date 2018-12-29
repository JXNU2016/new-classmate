package com.example.lenovo.newclassmate.Bean;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.newclassmate.Adapter.MyExpandableListViewAdapter;
import com.example.lenovo.newclassmate.AllActivity;
import com.example.lenovo.newclassmate.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

public class







Club extends Activity {

    private ExpandableListView listView;
    private MyExpandableListViewAdapter adapter;
    List<List<String>> list = new ArrayList<>();//创建两个列表，一个用于存放数组，另一个用于存放含有数组的列表
    List<Group> groups = new ArrayList<>();
    Button button;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club);
        AllActivity.getInstance().addActivity(this);
        button = findViewById(R.id.imagbtu_3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        initview();

        //设置标题
        ((CommonTitleBar) findViewById(R.id.Club_title)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
                }

            }
        });
    }

    private void initview() {

        listView = findViewById(R.id.st_view);


        // groups = getGroups();

        String[] s1 = {"传统文化发展研究会", "地产财富研究社", "江西历史与省情学友会", "法学社","心理协会","青年马克思主义研究会","三国与人才研究会","时杰建筑学社","图书馆读者协会","数学俱乐部 ","博雅图书漂流社","教育研究社","汉服协会","扶桑文化研究协会","IT爱好者协会 ","数学建模协会","科幻协会"};
        String[] s2 = {"话剧团","青蓝文学社","外语话剧社","学生京剧团","东北文化艺术社","泰豪合唱团","书画协会","动漫社","白鹿国学社","学生辩论团","印友社","大学生艺术团"};
        String[] s3 = {"影评协会","羽毛球协会","魔术好者协会","跑吧","VDS震舞糖街舞社","旅行者社团","超越乒乓球协会","幻影轮滑社","九天跆拳道","电子竞技协会","健美操协会","篮球协会","足球协会","网球协会","棋牌协会","武术协会","乐舞堂街舞社","JDC街舞社团","台球协会"};
        String[] s4 = {"和风日语俱乐部","国际交流联谊社","心语手语社团","乐迷协会","教育时空社","演辩协会","吉他协会","外语爱好者协会","市场营销俱乐部","手工艺社团","家电维修协会","金融业务技能协会","迷音社 ","摄影协会","材料科学协会","微博协会","我的网","桌游社","电子商务协会"};
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

        groups.add(new Group(R.drawable.learn,"理论研究类社团",R.drawable.downacross));
        groups.add(new Group(R.drawable.science,"文学艺术类社团",R.drawable.downacross));
        groups.add(new Group(R.drawable.hobby,"体育休闲类社团",R.drawable.downacross));
        groups.add(new Group(R.drawable.commonweal,"技能培养类社团",R.drawable.downacross));
        adapter = new MyExpandableListViewAdapter(this, list,groups);
        listView.setAdapter(adapter);
    }


    public void showDialog() {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);

        Context context;
        dialogBuilder
                .withTitle("大学社团是什么？")
                .withTitleColor("#000000")
                .withIcon(getResources().getDrawable(R.drawable.xclub))
                .withMessage("       大学社团是指学生在自愿基础上结成的各种群众性文化、艺术、学术团体。不分年级、系科甚至学校的界限，由兴趣爱好相近的同学组成的兴趣团体。在保证学生完成学习任务和不影响学校正常教学秩序的前提下开展各种活动。目的是活跃学校学习空气，提高学生自治能力，丰富课余生活；交流思想，切磋技艺，互相启迪，增进友谊。")
                .withMessageColor("#000000")
                .withDialogColor("#FFFFF0")
                .withEffect(Effectstype.Newspager)
                .withButton1Text("确定")
                .withButton2Text("取消")
                .isCancelableOnTouchOutside(false)

                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialogBuilder.dismiss();

                        Toast.makeText(v.getContext(), "快去看看有哪些社团吧", Toast.LENGTH_SHORT).show();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
    }


}


