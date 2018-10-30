package com.example.lenovo.newclassmate.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.lenovo.newclassmate.Adapter.NoticeAdapter;
import com.example.lenovo.newclassmate.CardStackView.TouristSpot;
import com.example.lenovo.newclassmate.CardStackView.TouristSpotCardAdapter;
import com.example.lenovo.newclassmate.GlideImageLoader;
import com.example.lenovo.newclassmate.MyScrollView;
import com.example.lenovo.newclassmate.R;
import com.example.lenovo.newclassmate.StartActivity;
import com.example.lenovo.newclassmate.VerticalScrollLayout;
import com.example.pickerview.widge.CommonTitleBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.SwipeDirection;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    protected Activity mActivity;
    protected View view;
    protected MyScrollView myScrollView;
    protected CommonTitleBar title;

    private ProgressBar progressBar;
    private CardStackView cardStackView;
    private TouristSpotCardAdapter adapter;
    private  VerticalScrollLayout vScrollLayout;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    private String url[]=new String[5];
    private String type[]=new String[5];
    private static boolean mBackKeyPressed = false;//记录是否有首次按键

    private String schoolName;
    private String userName;
    private String htmlurl[];

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, container, false);



        myScrollView=view.findViewById(R.id.MyScroll);
        title=view.findViewById(R.id.titlebar_home);

        initVScrollLayout();

        schoolName=((StartActivity)getActivity()).getSchoolName();
        userName=((StartActivity)getActivity()).getUserName();

        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();

        htmlurl=new String[4];
        htmlurl[0]="http://zs.jxnu.edu.cn/sj/bzslm.asp?id=7550";
        htmlurl[1]="http://zs.jxnu.edu.cn/sj/bzslm.asp?id=1186";
        htmlurl[2]="http://zs.jxnu.edu.cn/sj/bzslist.asp?lm=7";
        htmlurl[3]="http://zs.jxnu.edu.cn/sj/bzslm.asp?id=3319";

        //设置滑动隐藏标题栏
        myScrollView.setScrollViewListener(new MyScrollView.ScrollViewListener() {
            //            y表示当前滑动条的纵坐标
            //            oldy表示前一次滑动的纵坐标
            @Override
            public void onScrollChanged(View scrollView, int x, int y, int oldx, int oldy) {
                if (y < 800) {
                    float alpha = 1 - ((float) y) / 800;
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


        //设置轮播资源
        list_path.add("http://47.107.48.62:8080/p1.jpg");
        list_path.add("http://47.107.48.62:8080/p2.jpg");
        list_path.add("http://47.107.48.62:8080/p3.jpg");
        list_path.add("http://47.107.48.62:8080/p4.jpg");
        list_title.add("江西师范大学简介");
        list_title.add("江西师范大学专业介绍");
        list_title.add("江西师范大学新闻公告");
        list_title.add("江西师范大学导航");
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
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getActivity(), HtmlActivity.class);
                intent.putExtra("url",htmlurl[position]);
                startActivity(intent);
            }
        });

        //启动测试结果卡片
        setup();
        reload();
        return view;
    }


    //通知滚动效果
    private void initVScrollLayout() {
        vScrollLayout = (VerticalScrollLayout) view.findViewById(R.id.scroll_layout);
        NoticeAdapter adapter = new NoticeAdapter();
        vScrollLayout.setAdapter(adapter);
        List<NoticeAdapter.Item> items = new ArrayList<>();
        items=addItem(items,"通知1","2018—2019学年第一学期教学周历表");
        items=addItem(items,"通知2","江西师范大学2018年下半年硕士招聘公告");
        items=addItem(items,"通知3","关于做好2018年创新人才推进计划暨国家 “万人计划“科技创新领军人才、科技创业领军人才推荐选拔工作的通知");
        items=addItem(items,"通知4","关于组织申报2018年度教育部哲学社会科学研究后期资助项目的通知");
        adapter.setList(items);
    }

    //给通知绑定List
    private List<NoticeAdapter.Item> addItem(List<NoticeAdapter.Item> items,String title,String text)
    {
        NoticeAdapter.Item item = new NoticeAdapter.Item();
        if(text.length()>23)
        {
            text=text.substring(0,22);
            text+="...";
        }
        item.title = title;
        item.text = text;
        items.add(item);
        return items;
    }

    //绑定【测试结果】资源
    private List<TouristSpot> createTouristSpots() {
        List<TouristSpot> spots = new ArrayList<>();
        url[0]="https://source.unsplash.com/AWh9C-QjhE4/600x800";
        url[1]="https://source.unsplash.com/Xq1ntWruZQI/600x800";
        url[2]="https://source.unsplash.com/NYyCqdBOKwc/600x800";
        url[3]="https://source.unsplash.com/buF62ewDLcQ/600x800";
        url[4]="https://source.unsplash.com/THozNzxEP3g/600x800";
        type[0]="灵魂测试";
        type[1]="分寝推荐测试";
        type[2]="好友推荐测试";
        type[3]="社团推荐测试";
        type[4]="课程推荐测试";
        spots.add(new TouristSpot("灵魂测试", "@"+userName, url[0]));
        spots.add(new TouristSpot("分寝推荐测试", "@"+userName, url[1]));
        spots.add(new TouristSpot("好友推荐测试", "@"+userName, url[2]));
        spots.add(new TouristSpot("社团推荐测试", "@"+userName, url[3]));
        spots.add(new TouristSpot("课程推荐测试", "@"+userName, url[4]));
        return spots;
    }
    //设置【测试结果】的adapter
    private TouristSpotCardAdapter createTouristSpotCardAdapter() {
        final TouristSpotCardAdapter adapter = new TouristSpotCardAdapter(view.getContext());
        adapter.addAll(createTouristSpots());
        return adapter;
    }

    //【测试结果】的组织
    private void setup() {

        cardStackView = (CardStackView) view.findViewById(R.id.cardStackView);
        cardStackView.setSwipeDirection(SwipeDirection.HORIZONTAL);
        cardStackView.setSwipeThreshold(0.3f);

        //绑定监听器
        cardStackView.setCardEventListener(new CardStackView.CardEventListener() {
            //拖拽
            @Override
            public void onCardDragging(float percentX, float percentY) {
                Log.d("CardStackView", "onCardDragging");
            }

            //方向
            @Override
            public void onCardSwiped(SwipeDirection direction) {
                Log.d("CardStackView", "onCardSwiped: " + direction.toString());
                Log.d("CardStackView", "topIndex: " + cardStackView.getTopIndex());
                if (cardStackView.getTopIndex() == adapter.getCount() - 2) {
                    Log.d("CardStackView", "Paginate: " + cardStackView.getTopIndex());
                    paginate();
                }
            }

            @Override
            public void onCardReversed() {
                Log.d("CardStackView", "onCardReversed");
            }

            //回到原处
            @Override
            public void onCardMovedToOrigin() {
                Log.d("CardStackView", "onCardMovedToOrigin");
            }

            //点击
            @Override
            public void onCardClicked(int index) {
                Log.d("CardStackView", "onCardClicked: " + index);
                ((StartActivity)getActivity()).PopWindow(type[index%5],url[index%5]);
            }
        });
    }

    //【测试结果】的重载
    private void reload() {
        cardStackView.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = createTouristSpotCardAdapter();
                cardStackView.setAdapter(adapter);
                cardStackView.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }

    //【测试结果】的页码
    private void paginate() {
        cardStackView.setPaginationReserved();
        adapter.addAll(createTouristSpots());
        adapter.notifyDataSetChanged();
    }
}
