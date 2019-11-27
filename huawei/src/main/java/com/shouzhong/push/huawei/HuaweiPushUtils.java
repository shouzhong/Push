package com.shouzhong.push.huawei;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;

import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class HuaweiPushUtils {

    private static Application app;

    public static void init(Application app) {
        HuaweiPushUtils.app = app;
        HMSAgent.init(app);
    }

    public static void getToken(Activity activity) {
        HMSAgent.connect(activity, new ConnectHandler() {
            @Override
            public void onConnect(int rst) {
            }
        });
        HMSAgent.Push.getToken(new GetTokenHandler() {
            @Override
            public void onResult(int rst) {
            }
        });
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
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }
}
