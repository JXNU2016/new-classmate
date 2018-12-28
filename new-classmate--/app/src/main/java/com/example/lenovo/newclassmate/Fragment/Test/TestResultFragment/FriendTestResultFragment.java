package com.example.lenovo.newclassmate.Fragment.Test.TestResultFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.newclassmate.Activity.TestActivity.DormitoryTestActivity;
import com.example.lenovo.newclassmate.Activity.TestActivity.FriendTestActivity;
import com.example.lenovo.newclassmate.R;

import java.util.List;


public class FriendTestResultFragment extends Fragment {
    private Button button;
  //  private ImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.friendtextresult,container,false);

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
