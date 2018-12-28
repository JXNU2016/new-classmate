package com.example.lenovo.newclassmate.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.newclassmate.Activity.UserActivity.PersonDataActivity;
import com.example.lenovo.newclassmate.Activity.UserActivity.opinionActivity;
import com.example.lenovo.newclassmate.Activity.UserActivity.settingActivity;
import com.example.lenovo.newclassmate.Activity.User_Activity_children;
import com.example.lenovo.newclassmate.Bean.Club;
import com.example.lenovo.newclassmate.Bean.Course;
import com.example.lenovo.newclassmate.Bean.School;
import com.example.lenovo.newclassmate.MyScrollView;
import com.example.lenovo.newclassmate.R;
import com.example.lenovo.newclassmate.wxapi.StartActivity;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

public class UserFragment extends Fragment {
    public  static Bitmap bit=null; //头像

    private View view;
    private View persondata;
    private View feedback;
    private View check_refsult;
    private View setting;
    private TextView nicheng;
    private ImageView head_round;
    private MyScrollView scrollview_user;
    private CommonTitleBar titleBar_user;
    private View user_first_liner;
    private int imageHeight;
    private View library;
    private ImageView course;
    private View club;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_user, container, false);
        persondata = view.findViewById(R.id.user_person_data);
        feedback = view.findViewById(R.id.feedback_layout);
        check_refsult = view.findViewById(R.id.check_result_layout);
        setting = view.findViewById(R.id.setting_layout);
        nicheng = view.findViewById(R.id.nicheng);
        head_round = view.findViewById(R.id.head_roundImage);
        scrollview_user = view.findViewById(R.id.user_scrollview);
        titleBar_user = view.findViewById(R.id.titlebar_user);
        user_first_liner = view.findViewById(R.id.user_first_linear);
        library = view.findViewById(R.id.library);
        course = view.findViewById(R.id.course);
        club = view.findViewById(R.id.club);
        nicheng.setText(((StartActivity)getActivity()).getUserName());
        if(UserFragment.bit==null) {
            if (((StartActivity) getActivity()).getSex().equals("男")) {
                head_round.setImageResource(R.drawable.boy3);
            } else {
                head_round.setImageResource(R.drawable.girl2);
            }
        }
        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),School.class);
                startActivity(intent);

            }
        });
        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Course.class);
                startActivity(intent);
            }
        });
        club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Club.class);
                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),settingActivity.class));
            }
        });
        check_refsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "众寻:已经是最新版本", Toast.LENGTH_LONG).show();
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),opinionActivity.class));
            }
        });
        user_first_liner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),User_Activity_children.class));
            }
        });
        persondata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),PersonDataActivity.class));
            }
        });
        titleBar_user.setAlpha(0);
        scrollview_user.setScrollViewListener(new MyScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(View scrollView, int x, int y, int oldx, int oldy) {
                if (y <= 0) {
//                          设置背景，白色 透明
                    titleBar_user.setAlpha(0);
                } else if (y > 20 ) {
//
                    // 只是layout背景透明白色不透明
                    titleBar_user.setAlpha(1);
                } else {
//							白色透明
                    titleBar_user.setAlpha(0);
                }
            }
        }) ;
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        if(bit!=null){
            head_round.setImageBitmap(bit);
        }

        nicheng.setText(((StartActivity)getActivity()).getUserName());

    }
}