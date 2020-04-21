package com.shouzhong.push.demo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.shouzhong.push.huawei.HuaweiPushUtils;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LogUtils.e(HuaweiPushUtils.getToken(MainActivity.this));
                } catch (Exception e) {
                    LogUtils.e(e.getClass().getName() + "->" + e.getMessage());
                }
            }
        }).start();
    }
}
