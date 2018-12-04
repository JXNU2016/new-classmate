package com.example.lenovo.newclassmate.Activity.UserActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.donkingliang.labels.LabelsView;
import com.example.lenovo.newclassmate.R;
import com.example.lenovo.newclassmate.TestBean;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 *关于个性标签的设置
 */
public class person_label_Activity extends AppCompatActivity {

    public static final int RESULT_CODE=123456;
    public static ArrayList<String> arrayList=new ArrayList<>();
    public static ArrayList<String> arrayList_bei=new ArrayList<>();
    public static  List<Integer> positions=new ArrayList<>();
    LabelsView labelsView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview_item);
        labelsView =  findViewById(R.id.labels);


        final ArrayList<TestBean> label = new ArrayList<>();
        label.add(new TestBean("K歌",1));
        label.add(new TestBean("轻音乐",2));
        label.add(new TestBean("汪星人",3));
        label.add(new TestBean("话痨",4));
        label.add(new TestBean("眼镜男",5));
        label.add(new TestBean("爱美剧",6));
        label.add(new TestBean("铲屎宫",7));
        label.add(new TestBean("选择恐惧症",8));
        label.add(new TestBean("果粉",9));
        label.add(new TestBean("宇宙第一帅",10));
        label.add(new TestBean("瑶湖第一虚",11));
        label.add(new TestBean("大叔",12));
        label.add(new TestBean("IT民工",13));
        label.add(new TestBean("篮球",14));
        label.add(new TestBean("民谣",15));
        label.add(new TestBean("减肥",16));
        label.add(new TestBean("翻山越岭只为吃",17));
        label.add(new TestBean("愤青",18));
        label.add(new TestBean("背包客",19));
        label.add(new TestBean("村上春树",20));
        labelsView.setLabels(label, new LabelsView.LabelTextProvider<TestBean>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, TestBean data) {
                return data.getName();
            }
        });
        labelsView.setSelects(positions);  //设置选中的标签

        //设置标题
        ((CommonTitleBar) findViewById(R.id.labels_title)).setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    onBackPressed();
                }
                else if(action==CommonTitleBar.ACTION_RIGHT_TEXT){
                    List<TestBean> data=labelsView.getSelectLabelDatas();  //获取被选择的标签
                    Iterator<TestBean> iterable=data.iterator();
                    while(iterable.hasNext()){
                      TestBean t=iterable.next();
                      String s=t.getName();
                      if(!arrayList.contains(s))
                      arrayList.add(s);
                    }
                    positions=labelsView.getSelectLabels();  //获取选中的标签
                     Intent intent = getIntent();
                    intent.putStringArrayListExtra("list",arrayList );  //保存这个集合的信息就行
                    setResult(RESULT_CODE,intent);
                    finish();
                }
            }
        });

        }
}
