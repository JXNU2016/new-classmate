package com.example.lenovo.newclassmate.Fragment.Test.TestResultFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lenovo.newclassmate.R;

/**
 * @author Eskii
 */

public class DormitoryTestResultFragment extends Fragment{

    private Button button1;
    private Button button2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dormitorytestresult,container,false);

        button1 = view.findViewById(R.id.btn1);
        button2 = view.findViewById(R.id.see_btn);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),DormitorActivity.class);
                startActivity(intent );
            }
        });


        return view;
    }
}
