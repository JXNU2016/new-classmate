package com.example.lenovo.newclassmate.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.example.lenovo.newclassmate.GlideImageLoader;
import com.example.lenovo.newclassmate.MyScrollView;
import com.example.lenovo.newclassmate.R;
import com.example.pickerview.widge.CommonTitleBar;
import com.gyf.barlibrary.ImmersionBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    protected Activity mActivity;
    protected View view;
    protected MyScrollView myScrollView;
    protected CommonTitleBar title;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    private static boolean mBackKeyPressed = false;//记录是否有首次按键



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, container, false);
        myScrollView=view.findViewById(R.id.MyScroll);
        title=view.findViewById(R.id.titlebar_home);
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();


        //设置滑动隐藏标题栏
        myScrollView.setScrollViewListener(new MyScrollView.ScrollViewListener() {
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

        list_path.add("http://47.107.48.62:8080/home_1.jpg");
        list_path.add("http://47.107.48.62:8080/home_2.jpg");
        list_path.add("http://47.107.48.62:8080/home_3.jpg");
        list_path.add("http://47.107.48.62:8080/home_4.jpg");
        list_path.add("http://47.107.48.62:8080/home_5.jpg");
        list_title.add("钟烁");
        list_title.add("李祥");
        list_title.add("李雯晴");
        list_title.add("林观彪");
        list_title.add("刘畅");
        Banner banner = view.findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(list_path);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(list_title);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();



        return view;
    }

}
