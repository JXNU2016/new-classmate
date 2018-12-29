package com.example.lenovo.newclassmate.View;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lenovo on 2018/12/5.
 */

public class IOThread {
    private static ExecutorService singleThread;
    static Executor getSingleThread(){
        if(singleThread==null){
            singleThread= Executors.newSingleThreadExecutor();
        }
        return singleThread;
    }
}
