package com.example.lenovo.newclassmate.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.newclassmate.Activity.TestActivity.CourseTestActivity;
import com.example.lenovo.newclassmate.Activity.TestActivity.DormitoryTestActivity;
import com.example.lenovo.newclassmate.Activity.TestActivity.FriendTestActivity;
import com.example.lenovo.newclassmate.Activity.TestActivity.OrganizationTestActivity;
import com.example.lenovo.newclassmate.Activity.TestActivity.StartTestActivity;
import com.example.lenovo.newclassmate.MyScrollView;
import com.example.lenovo.newclassmate.R;
import com.example.pickerview.widge.CommonTitleBar;

public class TestFragment extends Fragment{

    private MyScrollView msc;
    private CommonTitleBar title;
    private Button b0,b1,b2,b3,b4;
    private TextView startTv,dormitoryTv,friendTv,organizationTv,courseTv;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_test, container, false);

        msc=view.findViewById(R.id.MyScroll);
        title=view.findViewById(R.id.titlebar_test);

        b0 = view.findViewById(R.id.startT0);
        b1 = view.findViewById(R.id.startT1);
        b2 = view.findViewById(R.id.startT2);
        b3 = view.findViewById(R.id.startT3);
        b4 = view.findViewById(R.id.startT4);
        startTv = view.findViewById(R.id.tv0);
        dormitoryTv = view.findViewById(R.id.tv1);
        friendTv = view.findViewById(R.id.tv2);
        organizationTv = view.findViewById(R.id.tv3);
        courseTv = view.findViewById(R.id.tv4);

        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StartTestActivity.class);
                startActivity(intent);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DormitoryTestActivity.class);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FriendTestActivity.class);
                startActivity(intent);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrganizationTestActivity.class);
                startActivity(intent);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CourseTestActivity.class);
                startActivity(intent);
            }
        });
        //设置滑动隐藏标题栏
        msc.setScrollViewListener(new MyScrollView.ScrollViewListener() {
            //            y表示当前滑动条的纵坐标
            //            oldy表示前一次滑动的纵坐标
            @Override
            public void onScrollChanged(View scrollView, int x, int y, int oldx, int oldy) {
                if (y < 300) {
                    float alpha = 1 - ((float) y) / 300;
                    title.setAlpha(alpha);
                    if (alpha==0)
                    {
                        title.setClickable(false);
                    }else
                    {
                        title.setClickable(true);
                    }
                } else {
                    //下滑显示标题栏
                    if (oldy > y) {
                        title.setAlpha(1);
                        title.setClickable(true);
                    } else {
                        title.setAlpha(0);
                        title.setClickable(false);
                    }
                }
            }
        });

        return view;

    }
}
