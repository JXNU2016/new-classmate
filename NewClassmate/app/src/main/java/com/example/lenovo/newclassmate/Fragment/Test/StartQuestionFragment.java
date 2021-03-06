package com.example.lenovo.newclassmate.Fragment.Test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.lenovo.newclassmate.Activity.TestActivity.StartTestActivity;
import com.example.lenovo.newclassmate.Adapter.OptionsGridViewAdapter;
import com.example.lenovo.newclassmate.Bean.QuestionBean;
import com.example.lenovo.newclassmate.Bean.QusetionOptionBean;
import com.example.lenovo.newclassmate.R;

import java.util.List;

/**
 * @author Eskii
 *         初始测试碎片
 */
public class StartQuestionFragment extends Fragment {
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
    private TextView doneQ,allQ;
    private TextView questionText;
    private Button backButton, nextButton;
    private int index;
    private QuestionBean questionBean;
    private OptionsGridViewAdapter optionsGridViewAdapter;
    private String type;
    private List<QusetionOptionBean> qusetionOptionBeanList;
    public LocalBroadcastManager localBroadcastManager;
    private final String toNext = "startJumpToNext";
    private final String toPre = "startJumpToPrevious";

    public StartQuestionFragment() {
    }

    @SuppressLint("ValidFragment")
    public StartQuestionFragment(int index) {
        this.index = index;
        questionBean = StartTestActivity.questionBeanList.get(index);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.test_item, container, false);

        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());

        gridView = view.findViewById(R.id.options);
        doneQ = view.findViewById(R.id.doneQ);
        allQ = view.findViewById(R.id.allQ);
        questionText = view.findViewById(R.id.questionText);
        backButton = view.findViewById(R.id.backButton);
        nextButton = view.findViewById(R.id.nextButton);
        qusetionOptionBeanList = questionBean.getQusetionOptionBeanList();
        optionsGridViewAdapter = new OptionsGridViewAdapter(questionBean.getQusetionOptionBeanList(), getActivity());

        allQ.setText(StartTestActivity.questionBeanList.size()+"");
        type = questionBean.getType();
        setQuestionType(type);  //根据题目类型设置选项可选数量
        setButton();    //设置按钮
        gridView.setAdapter(optionsGridViewAdapter);
        doneQ.setText((index+1) + "");    //更新当前题号

        return view;
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
                questionText.setText(questionBean.getDetails());
                gridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        optionsGridViewAdapter.notifyDataSetChanged();
                        Intent intent = new Intent(toNext);   //跳到下一页
                        intent.putExtra("optionValue", qusetionOptionBeanList.get(i).getValue());   //放入选择的选项
                        localBroadcastManager.sendBroadcast(intent);
                    }
                });
                break;
            case "multiple":    //多选
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("（多选）");
                spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableStringBuilder.append(questionBean.getDetails());
                questionText.setText(spannableStringBuilder);
                gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        optionsGridViewAdapter.notifyDataSetChanged();
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

        if (questionBean.getType() == "multiple" && index < 15) {
            nextButton.setVisibility(View.VISIBLE);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(toNext);   //跳到下一页
                    long ids[] = gridView.getCheckedItemIds();
                    int length = ids.length;
                    int score = 0;

                    for (int i = 0; i < length; i++) {
                        score += qusetionOptionBeanList.get((int)ids[i]).getValue();
                    }
                    intent.putExtra("optionValue", score);   //放入选择的选项
                    localBroadcastManager.sendBroadcast(intent);
                }
            });
        } else {
            nextButton.setVisibility(View.GONE);
        }
    }
}
