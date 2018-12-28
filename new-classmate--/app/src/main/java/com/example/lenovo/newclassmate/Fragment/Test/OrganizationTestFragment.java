package com.example.lenovo.newclassmate.Fragment.Test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.lenovo.newclassmate.Activity.TestActivity.OrganizationTestActivity;
import com.example.lenovo.newclassmate.Adapter.OptionsGridViewAdapter;
import com.example.lenovo.newclassmate.Adapter.myArrayAdapter;
import com.example.lenovo.newclassmate.Bean.QuestionBean;
import com.example.lenovo.newclassmate.Bean.QusetionOptionBean;
import com.example.lenovo.newclassmate.R;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Eskii
 */

public class OrganizationTestFragment extends Fragment {
    /**
     * gridView:选项格子布局
     * doneQ:已做题目数量
     * questionText:题目文本
     * backButton:上一题按钮
     * nextButton:下一题按钮
     * index:当前碎片下标
     * questionBean:当前题目内容
     * optionGridViewAdapter:选项格子布局的适配器
     * type:题目类型（多选/单选）
     * questionOptionBeanList:当前题目的选项队列
     */
    private GridView gridView;
    private ListView listView;
    private TextView doneQ, allQ;
    private TextView questionText;
    private Button backButton, nextButton;
    private int index;
    private QuestionBean questionBean;
    private OptionsGridViewAdapter optionsGridViewAdapter;
    private String type;
    private List<QusetionOptionBean> qusetionOptionBeanList;
    public LocalBroadcastManager localBroadcastManager;
    private final String toNext = "organizationJumpToNext";
    private final String toPre = "organizationJumpToPrevious";
    private myArrayAdapter myArrayAdapter;


    public OrganizationTestFragment() {
    }

    @SuppressLint("ValidFragment")
    public OrganizationTestFragment(int index) {
        this.index = index;
        questionBean = OrganizationTestActivity.questionBeanList.get(index);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (questionBean.getType().equals("simple")) {

        View view = inflater.inflate(R.layout.test_item, container, false);
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());

        gridView = view.findViewById(R.id.options);
        allQ = view.findViewById(R.id.allQ);
        doneQ = view.findViewById(R.id.doneQ);
        questionText = view.findViewById(R.id.questionText);
        backButton = view.findViewById(R.id.backButton);
        nextButton = view.findViewById(R.id.nextButton);
        qusetionOptionBeanList = questionBean.getQusetionOptionBeanList();
        optionsGridViewAdapter = new OptionsGridViewAdapter(qusetionOptionBeanList, getActivity());

        allQ.setText(OrganizationTestActivity.questionBeanList.size()+"");
        type = questionBean.getType();
        setQuestionType(type);  //根据题目类型设置选项可选数量
        setButton();    //设置按钮
        doneQ.setText((index + 1) + "");    //更新当前题号

        return view;

    } else {
        View view = inflater.inflate(R.layout.multiple_item, container, false);
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        listView = view.findViewById(R.id.options);
        allQ = view.findViewById(R.id.allQ);
        doneQ = view.findViewById(R.id.doneQ);
        questionText = view.findViewById(R.id.questionText);
        backButton = view.findViewById(R.id.backButton);
        nextButton = view.findViewById(R.id.nextButton);
        qusetionOptionBeanList = questionBean.getQusetionOptionBeanList();

        allQ.setText(OrganizationTestActivity.questionBeanList.size() + "");
        type = questionBean.getType();
        setQuestionType(type);  //根据题目类型设置选项可选数量
        setButton();    //设置按钮;
        doneQ.setText((index + 1) + "");    //更新当前题号
        return view;
    }
}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    /**
     * @param type：题目类型 根据题目类型设置选项可选数量
     */
    private void setQuestionType(String type) {

        switch (type) {

            case "simple":  //单选
                gridView.setAdapter(optionsGridViewAdapter);
                questionText.setText(questionBean.getDetails());
                gridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE); //单选
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        optionsGridViewAdapter.notifyDataSetChanged();
                        Intent intent = new Intent(toNext);   //跳到下一页
                        intent.putExtra("optionValue", qusetionOptionBeanList.get(i).getDetails());   //放入选择的选项
                        localBroadcastManager.sendBroadcast(intent);
                    }
                });
                break;
            case "multiple":    //多选
                List<String> questionList = new ArrayList<>();
                for (QusetionOptionBean qusetionOptionBean : qusetionOptionBeanList) {
                    questionList.add(qusetionOptionBean.getDetails());
                }
                myArrayAdapter = new myArrayAdapter(getActivity(),R.layout.option_multiple);
                listView.setAdapter(myArrayAdapter);
                myArrayAdapter.addAll(questionList);
                questionText.setText(questionBean.getDetails() + "（多选）");
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                    }
                });
                break;
        }
    }

    /**
     * 根据题目类型和碎片下标设置按钮
     */
    private void setButton() {

        if (index == 0) {
            backButton.setVisibility(View.INVISIBLE);
        } else {
            backButton.setVisibility(View.VISIBLE);

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(toPre);   //跳到上一页
                    localBroadcastManager.sendBroadcast(intent);
                }
            });
        }

        if (questionBean.getType().equals("multiple")) {
            nextButton.setVisibility(View.VISIBLE);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(toNext);   //跳到下一页
                    long ids[] = listView.getCheckedItemIds();
                    int length = ids.length;
                    String choice = "";
                    for (int i = 0; i < length; i++) {
                       choice += qusetionOptionBeanList.get((int) ids[i]).getDetails();
                    }
                    intent.putExtra("optionValue", choice);   //放入选择的选项
                    localBroadcastManager.sendBroadcast(intent);
                }
            });
        } else {
            nextButton.setVisibility(View.GONE);
        }
    }

}

