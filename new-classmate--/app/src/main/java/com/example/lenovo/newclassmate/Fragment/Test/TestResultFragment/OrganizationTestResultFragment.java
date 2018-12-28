package com.example.lenovo.newclassmate.Fragment.Test.TestResultFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.newclassmate.Activity.TestActivity.OrganizationTestActivity;
import com.example.lenovo.newclassmate.R;

import java.util.List;

public class OrganizationTestResultFragment extends Fragment {

    private Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.organizationtextresult,container,false);

        button = view.findViewById(R.id.btn1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


        return view;
    }

}
