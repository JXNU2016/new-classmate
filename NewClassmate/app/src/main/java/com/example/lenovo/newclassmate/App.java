package com.example.lenovo.newclassmate;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.lang.reflect.Field;

public class App extends Application {

    public static final String UPDATE_STATUS_ACTION = "com.umeng.message.example.action.UPDATE_STATUS";
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.init(this, "5bd17c73f1f5564f42000033"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    {
        PlatformConfig.setWeixin("wx09c4599c1354b79c", "1e9083bd25553f2264abcd21ccec9de7");
        PlatformConfig.setQQZone("101517110", "2864fbcec19d722b9e2819d0f07383cd");
    }
}

