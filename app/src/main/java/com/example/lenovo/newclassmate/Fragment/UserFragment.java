package com.example.lenovo.newclassmate.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lenovo.newclassmate.Activity.UserActivity.PersonDataActivity;
import com.example.lenovo.newclassmate.Activity.UserActivity.opinionActivity;
import com.example.lenovo.newclassmate.Activity.UserActivity.settingActivity;
import com.example.lenovo.newclassmate.Activity.UserActivity.zanshang_Activity;
import com.example.lenovo.newclassmate.Activity.User_Activity_children;
import com.example.lenovo.newclassmate.Bean.Club;
import com.example.lenovo.newclassmate.Bean.Course;
import com.example.lenovo.newclassmate.Bean.School;
import com.example.lenovo.newclassmate.MyScrollView;
import com.example.lenovo.newclassmate.R;
import com.example.lenovo.newclassmate.StartActivity;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;


public class UserFragment extends Fragment {
    public  static Bitmap bit=null; //头像

    private float mSelfHeight = 0;//用以判断是否得到正确的宽高值
    private float mSubScribeScale;
    private float mSubScribeScaleX;
    private float mHeadImgScale;
    private float mViewScale;


    private View view;
    private View persondata;
    private View feedback;
    private View check_refsult;
    private View setting;
    private TextView nicheng;
    private ImageView head_round;
    private NestedScrollView scrollview_user;
  //  private CommonTitleBar titleBar_user;
    private View user_first_liner;
    private int imageHeight;
    private View library;
    private ImageView course;
    private View club;
    private View kefu;
    private View zanshang;
    private AppBarLayout appBarLayout;

    private CardView card_image;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_user, container, false);
        persondata = view.findViewById(R.id.user_person_data);
        feedback = view.findViewById(R.id.feedback_layout);
        check_refsult = view.findViewById(R.id.check_result_layout);
        setting = view.findViewById(R.id.setting_layout);
        nicheng = view.findViewById(R.id.nicheng);
        head_round = view.findViewById(R.id.head_roundImage);
        card_image = view.findViewById(R.id.Card_image);

        scrollview_user = view.findViewById(R.id.user_scrollview);
       // titleBar_user = view.findViewById(R.id.titlebar_user);
        user_first_liner = view.findViewById(R.id.user_first_linear);
        library = view.findViewById(R.id.library);
        course = view.findViewById(R.id.course);
        club = view.findViewById(R.id.club);
        kefu = view.findViewById(R.id.kefu);  //客服
        zanshang = view.findViewById(R.id.zanshang);      //赞赏
        appBarLayout = view.findViewById(R.id.appbar);
        Toolbar toolbar=view.findViewById(R.id.tb_toolbar);

        init();
        nicheng.setText(((StartActivity)getActivity()).getUserName());
        if(UserFragment.bit==null) {
            if (((StartActivity) getActivity()).getSex().equals("男")) {
                head_round.setImageResource(R.mipmap.boy);
            } else {
                head_round.setImageResource(R.mipmap.girl);
            }
        }
        zanshang.setOnClickListener(v->{
            startActivity(new Intent(getActivity(),zanshang_Activity.class));
        });
        kefu.setOnClickListener(v->{
            callphone();
        });

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
      //  titleBar_user.setAlpha(0);
//        scrollview_user.setScrollViewListener(new MyScrollView.ScrollViewListener() {
//            @Override
//            public void onScrollChanged(View scrollView, int x, int y, int oldx, int oldy) {
//                if (y <= 0) {
////                          设置背景，白色 透明
//                   // titleBar_user.setAlpha(0);
//                } else if (y > 20 ) {
////
//                    // 只是layout背景透明白色不透明
//                  //  titleBar_user.setAlpha(1);
//                } else {
////							白色透明
//                  //  titleBar_user.setAlpha(0);
//                }
//            }
//        }) ;
        return view;

    }


    public void init(){
        final float screenW = getResources().getDisplayMetrics().widthPixels;
        final float toolbarHeight = getResources().getDimension(R.dimen.toolbar_height);
        final float initHeight = getResources().getDimension(R.dimen.subscription_head);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(mSelfHeight==0){
                    mSelfHeight=nicheng.getHeight();  //获取昵称高度
                    float distanceName=nicheng.getY()+(nicheng.getHeight()-toolbarHeight)/2.0f;
                    float distanceHead_Image=card_image.getY()+(card_image.getHeight()-toolbarHeight)/2.0f;
                    float diatanceRela=user_first_liner.getY()+(user_first_liner.getHeight()-toolbarHeight)/2.0f;
                    float diatanceNameX=screenW/2.0f-(nicheng.getWidth()/2.0f+getResources().getDimension(R.dimen.normal_space));
                    mSubScribeScale = distanceName / (initHeight - toolbarHeight);
                    mHeadImgScale = distanceHead_Image / (initHeight - toolbarHeight);
                    mSubScribeScaleX = diatanceNameX / (initHeight - toolbarHeight);
                    mViewScale=diatanceRela/ (initHeight - toolbarHeight);
                }

                float scale = 1.0f - (-verticalOffset) / (initHeight - toolbarHeight);
                if(scale>=0.6){
                    card_image.setScaleX(scale);
                    card_image.setScaleY(scale);
                    card_image.setAlpha(scale);
                }else if(scale<0.6){
                    card_image.setAlpha(0);
                }

                //card_image.setTranslationY(mHeadImgScale * verticalOffset);
                nicheng.setTranslationY(-mSubScribeScale * verticalOffset/2.0f);
                nicheng.setTranslationX(-mSubScribeScaleX * verticalOffset);
                user_first_liner.setTranslationY(mViewScale*verticalOffset);
            }
        });
    }


/*
拨打电话(跳转到拨号界面，用户手动拨打)
 */
    private void callphone() {
        Intent intent=new Intent(Intent.ACTION_DIAL);
        Uri data=Uri.parse("tel:"+ "1008611");
        intent.setData(data);
        startActivity(intent);
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