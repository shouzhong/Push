package com.shouzhong.push.huawei;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.text.TextUtils;

import com.huawei.hms.aaid.HmsInstanceId;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HuaweiPushUtils {

    private static Application app;
    private static ExecutorService executor;

    public static void init() {
        if (executor == null) executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String token = getToken();
                if (TextUtils.isEmpty(token)) return;
                HuaweiPushUtils.sendData("action.TOKEN", token);
            }
        });
    }

    /**
     * 注意以下方法在子线程运行
     *
     * @return
     */
    public static String getToken() {
        try {
            ApplicationInfo appInfo = getApplication().getPackageManager().getApplicationInfo(getApplication().getPackageName(), PackageManager.GET_META_DATA);
            String appId = appInfo.metaData.getString("com.huawei.hms.client.appid");
            return HmsInstanceId.getInstance(getApplication()).getToken(appId.replace("appid=", ""), "HCM");
        } catch (Exception e) {
        }
        return null;
    }

    static void sendData(String action, String data) {
        String pkg = getApplication().getPackageName();
        Intent intent = new Intent(pkg + ".RECEIVE_PUSH_MESSAGE");
        intent.setPackage(pkg);
        intent.putExtra("type", 0b10);
        intent.putExtra("action", action);
        intent.putExtra("data", data);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getApplication().sendBroadcast(intent, pkg + ".permission.PUSH_RECEIVE");
        } else {
            PackageManager pm = getApplication().getPackageManager();
            List<ResolveInfo> matches = pm.queryBroadcastReceivers(intent, 0);
            for (ResolveInfo resolveInfo : matches) {
                Intent explicit = new Intent(intent);
                ComponentName cn = new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName, resolveInfo.activityInfo.name);
                explicit.setPackage(resolveInfo.activityInfo.applicationInfo.packageName);
                explicit.setComponent(cn);
                getApplication().sendBroadcast(explicit, pkg + ".permission.PUSH_RECEIVE");
            }
        }
    }

    private static Application getApplication() {
        if (app != null) return app;
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return HuaweiPushUtils.app = (Application) app;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }
}
