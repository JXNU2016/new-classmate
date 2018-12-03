package com.example.lenovo.newclassmate.Fragment.Test.TestResultFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.newclassmate.Activity.TestActivity.StartTestActivity;
import com.example.lenovo.newclassmate.R;

import java.util.List;

/**
 * @author Eskii
 *         测试结果碎片
 */

public class StartTestResultFragment extends Fragment {

    private ImageView headImg;
    private TextView myAttr, result2;
    private LinearLayout share;
    private String result;
    private Button back;
    private List<Integer> score;

    public StartTestResultFragment() {

    }

    @SuppressLint("ValidFragment")
    public StartTestResultFragment(String result) {
        this.result = result;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.test_result, container, false);

        headImg = view.findViewById(R.id.headImg);
        myAttr = view.findViewById(R.id.myAttr);
        result2 = view.findViewById(R.id.ressult2);
        share = view.findViewById(R.id.share);
        back = view.findViewById(R.id.backButton);
        score = StartTestActivity.scoreList;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return view;
    }

    private String caculate(List<Integer> list){
        String result = null;

        int sum = 0;
        for (int n : list){
            sum += n;
        }

        if (sum >= 180){

            result = "意志力强，头脑冷静，有较强的领导欲，事业心强，不达目的不罢休。外表和善，内心自傲，对有利于自己的人际关系比较看重，有时显得性格急噪，咄咄逼人，得理不饶人，不利于自己时顽强抗争，不轻易认输。思维理性，对爱情和婚姻的看法很现实，对金钱的欲望一般。";

        }else if (sum >= 140 && sum <= 179){

            result = "聪明，性格活泼，人缘好，善于交朋友，心机较深。事业心强，渴望成功。思维较理性，崇尚爱情，但当爱情与婚姻发生冲突时会选择有利于自己的婚姻。金钱欲望强烈。";

        }else if(sum >= 100 && sum <= 139){

            result = "爱幻想，思维较感性，以是否与自己投缘为标准来选择朋友。性格显得较孤傲，有时较急噪，有时优柔寡断。事业心较强，喜欢有创造性的工作，不喜欢按常规办事。性格倔强，言语犀利，不善于妥协。崇尚浪漫的爱情，但想法往往不切合实际。金钱欲望一般。";

        }else if(sum >= 70 && sum <= 99){

            result = "好奇心强，喜欢冒险，人缘较好。事业心一般，对待工作，随遇而安，善于妥协。善于发现有趣的事情，但耐心较差，敢于冒险，但有时较胆小。渴望浪漫的爱情，但对婚姻的要求比较现实。不善理财。";

        }else if (sum >= 40 && sum <= 69){

            result = "性情温良，重友谊，性格塌实稳重，但有时也比较狡黠。事业心一般，对本职工作能认真对待，但对自己专业以外事物没有太大兴趣，喜欢有规律的工作和生活，不喜欢冒险，家庭观念强，比较善于理财。";

        }else{
            result = "散漫，爱玩，富于幻想。聪明机灵，待人热情，爱交朋友，但对朋友没有严格的选择标准。事业心较差，更善于享受生活，意志力和耐心都较差，我行我素。有较好的异性缘，但对爱情不够坚持认真，容易妥协。没有财产观念。";

        }

        return result;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
