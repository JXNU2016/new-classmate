package com.example.lenovo.newclassmate;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;


/*
Activity容器

所有启动过的Activity储存在这个容器，便于finish（）
 */

public class AllActivity extends Application {
    private List<Activity> activitys = null;
    private static AllActivity instance;

    private AllActivity() {
        activitys = new LinkedList<Activity>();
    }

    /**
     * 单例模式中获取唯一的MyApplication实例
     *
     * @return
     */
    public static AllActivity getInstance() {
        if (null == instance) {
            instance = new AllActivity();
        }
        return instance;

    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        if (activitys != null && activitys.size() > 0) {
            if(!activitys.contains(activity)){
                activitys.add(activity);
            }
        }else{
            activitys.add(activity);
        }

    }

    // 遍历所有Activity并finish
    public void exit() {
        if (activitys != null && activitys.size() > 0) {
            for (Activity activity : activitys) {
                activity.finish();
            }
        }
        System.exit(0);
    }

}
