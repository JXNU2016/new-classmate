package com.example.lenovo.newclassmate.Fragment.Test.TestResultFragment;

import android.app.Activity;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.lenovo.newclassmate.Activity.TestActivity.DormitoryTestActivity;
import com.example.lenovo.newclassmate.R;

import java.util.List;

public class DormitorActivity extends AppCompatActivity{
    private List<String> choice;
    private TextView textView;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dortestresult);

         textView = findViewById(R.id.result);
         choice = DormitoryTestActivity.choiceList;

        String s = "";

       for (String s1 : choice){

            s += s1 + "\n";

        }

       textView.setText(s);

    }
}
