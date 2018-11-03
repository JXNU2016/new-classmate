package com.example.lenovo.newclassmate.Fragment.Test.TestResultFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.newclassmate.Activity.TestActivity.DormitoryTestActivity;
import com.example.lenovo.newclassmate.R;

import java.util.List;

/**
 * @author Eskii
 */

public class DormitoryTestResultFragment extends Fragment{

    private TextView textView;
    private List<String> choice;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dormitorytestresult,container,false);

        textView = view.findViewById(R.id.result);
        choice = DormitoryTestActivity.choiceList;

        String s = "";

        for (String s1 : choice){

            s += s1 + "\n";

        }

        textView.setText(s);

        return view;
    }
}
