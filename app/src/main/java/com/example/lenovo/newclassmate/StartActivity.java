package com.example.lenovo.newclassmate;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.newclassmate.Fragment.ChatFragment;
import com.example.lenovo.newclassmate.Fragment.HomeFragment;
import com.example.lenovo.newclassmate.Fragment.TestFragment;
import com.example.lenovo.newclassmate.Fragment.UserFragment;
import com.gyf.barlibrary.ImmersionBar;
import com.hmy.popwindow.PopItemAction;
import com.hmy.popwindow.PopWindow;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.ycl.tabview.library.TabView;
import com.ycl.tabview.library.TabViewChild;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/*
底部导航栏启动页
 */
public class StartActivity extends FragmentActivity {
    TabView tabView;
    Fragment homeActivity;
    Fragment testActivity;
    Fragment chatActivity;
    Fragment userActivity;
    private long firstTime = 0;
    private SharedPreferences preferences;

    private String name;
    private String schoolName;
    private String studentId;
    private String password;
    private String sex;
    private String year;
    private String month;
    private String day;
    private String province;
    private String city;
    private String county;
    private String phone;
    private String userName;  //昵称
    private boolean result;
    Bitmap bitmap=null;
    private ShareAction mShareAction;
    private UMImage image;
    private ShareBoardConfig config;

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //读取本地缓存的用户数据
        preferences=getSharedPreferences("land", Context.MODE_PRIVATE);
        studentId = preferences.getString("studentId", null);
        password = preferences.getString("password", null);
        name=preferences.getString("name",null);
        schoolName=preferences.getString("schoolName",null);
        sex=preferences.getString("sex",null);
        year=preferences.getString("year",null);
        month=preferences.getString("month",null);
        day=preferences.getString("day",null);
        province=preferences.getString("province",null);
        city=preferences.getString("city",null);
        county=preferences.getString("county",null);
        phone=preferences.getString("phone",null);
        userName=preferences.getString("userName",null);
        if (studentId == null || password == null) {
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_start);
        ImmersionBar.with(this).init();

        //动态获取权限
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }

        //创建底部导航栏Fragment
        tabView = findViewById(R.id.tabView);
        homeActivity = new HomeFragment();
        testActivity = new TestFragment();
        chatActivity = new ChatFragment();
        userActivity = new UserFragment();

        //绑定底部导航子控件（获得焦点图标，未获得焦点图标，目标Fragment对象）
        List<TabViewChild> tabViewChildList = new ArrayList<>();
        TabViewChild tabViewChild01 = new TabViewChild(R.drawable.home2, R.drawable.home1, "首页", homeActivity);
        TabViewChild tabViewChild02 = new TabViewChild(R.drawable.test2, R.drawable.test1, "测试", testActivity);
        TabViewChild tabViewChild03 = new TabViewChild(R.drawable.chat2, R.drawable.chat1, "消息", chatActivity);
        TabViewChild tabViewChild04 = new TabViewChild(R.drawable.user2, R.drawable.user1, "我的", userActivity);
        tabViewChildList.add(tabViewChild01);
        tabViewChildList.add(tabViewChild02);
        tabViewChildList.add(tabViewChild03);
        tabViewChildList.add(tabViewChild04);

        tabView.setTextViewSelectedColor(Color.parseColor("#2CA6E0"));  //设置获得焦点文字颜色
        tabView.setTabViewDefaultPosition(0);      //设置启动页面
        tabView.setImageViewHeight(100);         //设置图片高度
        tabView.setTextViewSize(10);
        tabView.setTabViewChild(tabViewChildList, getSupportFragmentManager());
        tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int position, ImageView currentImageIcon, TextView currentTextView) {  //绑定监听器

            }
        });


        //绑定分享按钮事件
        mShareAction = new ShareAction(StartActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
        .setShareboardclickCallback(shareBoardlistener);
        config=new ShareBoardConfig();
        config.setShareboardBackgroundColor(Color.parseColor("#F5F5F5"));//分享板块背景颜色
        config.setMenuItemBackgroundShape(1);//分享item背景形状（圆形）
    }

    @Override
    protected void onResume() {
        super.onResume();
        preferences=getSharedPreferences("land", Context.MODE_PRIVATE);
        userName=preferences.getString("userName",null);
    }

    //设置双击退出
        public boolean onKeyUp(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(StartActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                    return true;
                } else {
                    AllActivity.getInstance().exit();
                    finish();
                }
            }

            return super.onKeyUp(keyCode, event);
        }

        //为Fragment获取数据提供方法
    public String getSchoolName() {
        return schoolName;
    }
    public String getUserName(){return userName;}  //返回昵称
    public String getSex(){return sex;}  //返回性别

    //下弹菜单（保存图片/分享）
    public void PopWindow(final String type, final String url)
    {
        PopWindow popWindow = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .setTitle(type)
                .addItemAction(new PopItemAction("保存图片", PopItemAction.PopItemStyle.Normal,new PopItemAction.OnClickListener()
                {
                    @Override
                    public void onClick() {
                        //设置存储路径
                        String dir1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ZhongXun/";
                        String dir2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ZhongXun/ZhongXun_images/";
                        //获取内部存储状态
                        String state = Environment.getExternalStorageState();
                        //如果状态不是mounted，无法读写
                        if (!state.equals(Environment.MEDIA_MOUNTED)) {
                            Toast.makeText(StartActivity.this, "保存失败，请检查存储！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            bitmap=null;
                            result=true;
                            String fileName=type;
                            setBitmap(url);
                            while (bitmap==null&&result==true)
                            {
                            }
                            if(result==false)
                            {
                                Toast.makeText(StartActivity.this, "请检查网络！", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else{
                                try {
                                    File file = new File(dir1);
                                    if (!file.exists()) {
                                        file.mkdir();
                                    }
                                    File file1=new File(dir2);
                                    if(!file1.exists())
                                    {
                                        file1.mkdir();
                                    }
                                    File picture = new File(dir2 + fileName + ".jpg");
                                    FileOutputStream out = new FileOutputStream(picture);
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                    out.flush();
                                    out.close();
                                    Toast.makeText(StartActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(StartActivity.this, "保存失败！", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                                try {
                                    File file = new File(dir2 + fileName + ".jpg");
                                    FileOutputStream out = new FileOutputStream(file);
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                    out.flush();
                                    out.close();
                                    //保存图片后发送广播通知更新数据库
                                    Uri uri = Uri.fromFile(file);
                                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return;
                            }
                        }
                    }
                }))
                .addItemAction(new PopItemAction("分享", PopItemAction.PopItemStyle.Normal, new PopItemAction.OnClickListener() {
                    @Override
                    public void onClick() {
                        image=new UMImage(StartActivity.this, url);
                        mShareAction.open(config);
                    }
                }))
                .addItemAction(new PopItemAction("取消", PopItemAction.PopItemStyle.Cancel))
                .create();
        popWindow.show();
    }


    //初始化QQ分享
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }



    //分享的反馈
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(StartActivity.this,"分享成功！",Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(StartActivity.this,"分享失败："+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(StartActivity.this,"分享取消",Toast.LENGTH_LONG).show();
        }
    };

    //分享面板的监听器
    private ShareBoardlistener shareBoardlistener = new  ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform,SHARE_MEDIA share_media) {
                new ShareAction(StartActivity.this)
                        .setPlatform(share_media)
                        .setCallback(shareListener)
                        .withMedia(image)
                        .share();
            }
    };


    //获取Bitmap对象
    private void setBitmap(final String url){
        bitmap=null;
        //主要是把网络图片的数据流读入到内存中
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Android4.0以后，网络请求一定要在子线程中进行
                final byte[] data = getImages(url);
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                }
            }
        ).start();
}

    //获取网络图片转换为Byte[]
    private byte[] getImages(String path){
        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(6*1000);
            InputStream inputStream = null;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                while ((len = inputStream.read(buffer)) !=-1){
                    outputStream.write(buffer,0,len);
                }
                outputStream.close();
                inputStream.close();
            }
            result=true;
            return outputStream.toByteArray();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            result=false;
        } catch (IOException e) {
            e.printStackTrace();
            result=false;
        }
        return null;
    }
    /**
     * 解决界面重叠
     * 当前的界面的保存状态，只是从新让新的Fragment指向了原本未被销毁的fragment，它就是onAttach方法对应的Fragment对象
     * @param fragment
     */
    @Override
    public void onAttachFragment(Fragment fragment) {
        if (homeActivity == null && fragment instanceof HomeFragment){
            homeActivity = new HomeFragment();
        }else if (testActivity == null && fragment instanceof TestFragment) {
            testActivity = new TestFragment();
        }else if (chatActivity == null && fragment instanceof ChatFragment){
            chatActivity = new ChatFragment();
        }else if (userActivity == null && fragment instanceof UserFragment){
            userActivity = new UserFragment();
        }
    }
}