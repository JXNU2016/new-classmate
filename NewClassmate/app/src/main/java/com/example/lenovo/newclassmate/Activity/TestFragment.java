package com.example.lenovo.newclassmate.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.newclassmate.MyScrollView;
import com.example.lenovo.newclassmate.R;
import com.example.pickerview.widge.CommonTitleBar;

public class TestFragment extends Fragment {

    private MyScrollView msc;
    private CommonTitleBar title;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_test, container, false);

        msc=view.findViewById(R.id.MyScroll);
        title=view.findViewById(R.id.titlebar_test);


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
