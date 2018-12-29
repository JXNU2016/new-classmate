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

import com.example.lenovo.newclassmate.Adapter.MyExpandableAdapter;
import com.example.lenovo.newclassmate.AllActivity;
import com.example.lenovo.newclassmate.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

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
        AllActivity.getInstance().addActivity(this);
        button = (Button) findViewById(R.id.imagbtu_2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        initView();


        //设置标题
        ((CommonTitleBar) findViewById(R.id.Course_title)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    finish();
                }

            }
        });

    }

    //初始化ExpandableListView控件与Adapter
    private void initView() {
        //每一个二级列表的内容都用一个数组存放
        String[] s1 = {"政治学", "西藏与旅游", "历史学", "人际交往心理学","幸福心理学", "风水环境与地理","喝的学问","世界名校历史" };
        String[] s2 = {"生物制药学", "化学工艺", "天文", "工艺品","计算机组成原理","电脑维护","初学Python","web网页设计","计算机英语"};
        String[] s3 = {"唱歌与发音", "爵士舞", "Breaking", "Pop-ping","国画","电影鉴赏","音乐治疗","西方音乐史","古典音乐赏析"};
        String[] s4 = {"游泳与健身", "乒乓球", "三人篮球", "棒球", "网球","田径","足球","高尔夫球"};
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

        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);

        Context context;
        dialogBuilder
                .withTitle("大学选修课是什么？")
                .withTitleColor("#000000")
                .withIcon(getResources().getDrawable(R.drawable.xcourse))
                .withMessage("       大学里面的选修课是学校帮助学生丰富知识，拓宽知识面所开设的课程，课程丰富多彩，各个领域都有所涉略，学生可以自由选择课程和任课老师。大学选修课一般可以概括分为两类：公共选修课和专业选修课 ，选修课的学分要求一般是毕业的硬性指标，在修满学分后才有毕业资格，部分学校的学费与所选选修课的学分数相关。选课开始时间一般是大一下学期或者大二上学期，之后每个学期都可以选择选修课。")
                .withMessageColor("#000000")
                .withDialogColor("#FFFFF0")
//                .setCustomView(viewresId, textView.getContext())
                .withEffect(Effectstype.Newspager)
                .withButton1Text("确定")
                .withButton2Text("取消")
                .isCancelableOnTouchOutside(false)

                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialogBuilder.dismiss();
                        Toast.makeText(v.getContext(), "快去看看有哪些修选课吧", Toast.LENGTH_SHORT).show();
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



