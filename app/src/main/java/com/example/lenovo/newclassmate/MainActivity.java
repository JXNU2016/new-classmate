package com.example.lenovo.newclassmate;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lenovo.newclassmate.Activity.LoginActivity;
import com.example.lenovo.newclassmate.Activity.VerifyActivity;

public class MainActivity extends AppCompatActivity {

   // ImageView mImageView1;
    //ImageView mImageView2;
   private long exitTime;
    ImageView mImageView3;
    private Button mRegister;
    private Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AllActivity.getInstance().addActivity(this);   //添加此Activity到容器内
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  //充满全屏

        initViews();
        initAnims();
            mRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                   public void onClick(View v) {
                           Intent intent=new Intent(MainActivity.this, VerifyActivity.class);
                           startActivity(intent);
                  }
               });
            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });

    }

    private void initViews() {
        //mImageView1 = (ImageView) findViewById(R.id.image1);
        //mImageView2 = (ImageView) findViewById(R.id.image2);
        mImageView3 = (ImageView) findViewById(R.id.image3);
        mRegister = findViewById(R.id.btn_register);
        mLogin = findViewById(R.id.btn_login);
    }

    //启动动画
//    private void startAnimation() {
//        ObjectAnimator anim1 = new ObjectAnimator().ofFloat(mImageView1, "alpha", 1f, 0f).setDuration(5000);
//        ObjectAnimator anim2 = new ObjectAnimator().ofFloat(mImageView2, "alpha", 0f, 1f).setDuration(5000);
//        AnimatorSet set = new AnimatorSet();
//        set.playTogether(anim1, anim2);
//
//        ObjectAnimator anim3 = new ObjectAnimator().ofFloat(mImageView2, "alpha", 1f, 0f).setDuration(5000);
//        ObjectAnimator anim4 = new ObjectAnimator().ofFloat(mImageView3, "alpha", 0f, 1f).setDuration(5000);
//        AnimatorSet set1 = new AnimatorSet();
//        set1.playTogether(anim3, anim4);
//
//        ObjectAnimator anim5 = new ObjectAnimator().ofFloat(mImageView3, "alpha", 1f, 0f).setDuration(5000);
//        ObjectAnimator anim6 = new ObjectAnimator().ofFloat(mImageView1, "alpha", 0f, 1f).setDuration(5000);
//        AnimatorSet set2 = new AnimatorSet();
//        set2.playTogether(anim5, anim6);
//
//        AnimatorSet set3 = new AnimatorSet();
//        set3.playSequentially(set, set1, set2);
//        set3.addListener(new AnimatorListenerAdapter() {
//
//            private boolean mCanceled;
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                mCanceled = false;
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                mCanceled = true;
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if (!mCanceled) {
//                    animation.start();
//                }
//            }
//
//        });
//        set3.start();
//    }



    /**
     * 初始化logo图片以及底部注册、登录的按钮动画
     */
    private void initAnims() {
        //初始化底部注册、登录的按钮动画
        //以控件自身所在的位置为原点，从下方距离原点500像素]k的位置移动到原点
        ObjectAnimator tranLogin = ObjectAnimator.ofFloat(mRegister, "translationY", 500, 0);
        ObjectAnimator tranRegister = ObjectAnimator.ofFloat(mLogin, "translationY", 500, 0);
        //将注册、登录的控件alpha属性从0变到1
        ObjectAnimator alphaLogin = ObjectAnimator.ofFloat(mRegister, "alpha", 0, 1);
        ObjectAnimator alphaRegister = ObjectAnimator.ofFloat(mLogin, "alpha", 0, 1);
        final AnimatorSet bottomAnim = new AnimatorSet();
        bottomAnim.setDuration(500);
        //同时执行控件平移和alpha渐变动画
        bottomAnim.play(tranLogin).with(tranRegister).with(alphaLogin).with(alphaRegister);



//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        //由一屏幕显示的项数决定
//        columnWidth = dm.widthPixels;
        //获取屏幕高度
       // WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;

        //通过测量，获取ivLogo的高度
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mImageView3.measure(w, h);
        int logoHeight = mImageView3.getMeasuredHeight();

        //初始化ivLogo的移动和缩放动画
        float transY = (screenHeight - logoHeight);
        //ivLogo向上移动 transY 的距离
        ObjectAnimator tranLogo = ObjectAnimator.ofFloat(mImageView3, "translationY", 0, transY);
        //ivLogo在X轴和Y轴上都缩放0.75倍
        ObjectAnimator scaleXLogo = ObjectAnimator.ofFloat(mImageView3, "scaleX", 1f, 0.75f);
        ObjectAnimator scaleYLogo = ObjectAnimator.ofFloat(mImageView3, "scaleY", 1f, 0.75f);
        AnimatorSet logoAnim = new AnimatorSet();
        logoAnim.setDuration(500);
        logoAnim.play(tranLogo).with(scaleXLogo).with(scaleYLogo);
        logoAnim.start();
        logoAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //待ivLogo的动画结束后,开始播放底部注册、登录按钮的动画
                bottomAnim.start();
            }
        });
    }

}