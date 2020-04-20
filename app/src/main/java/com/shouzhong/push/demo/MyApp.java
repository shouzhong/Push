package com.shouzhong.push.demo;

import android.app.Application;

import com.shouzhong.push.PushUtils;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PushUtils.init(PushUtils.TYPE_HUAWEI);
    }
}
