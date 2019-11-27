package com.shouzhong.push.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            ApplicationInfo appInfo = getApplication().getPackageManager().getApplicationInfo(getApplication().getPackageName(), PackageManager.GET_META_DATA);
            String test1 = appInfo.metaData.getString("test1");
            String test2 = appInfo.metaData.getString("test2");
            Log.e("MainActivity", test1.startsWith("3") + "");
            Log.e("MainActivity", test2.startsWith("d") + "");
            Log.e("MainActivity", test1);
            Log.e("MainActivity", test2);
        } catch (Exception e) {}
    }
}
