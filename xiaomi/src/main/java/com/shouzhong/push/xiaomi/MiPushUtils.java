package com.shouzhong.push.xiaomi;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;

import com.xiaomi.mipush.sdk.MiPushClient;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MiPushUtils {

    private static Application app;

    public static void init(Application app) {
        MiPushUtils.app = app;
        try {
            if (isMainProcess()) {
                ApplicationInfo appInfo = getApplication().getPackageManager().getApplicationInfo(getApplication().getPackageName(), PackageManager.GET_META_DATA);
                String appId = appInfo.metaData.getString("MIPUSH_APP_ID");
                String appKey = appInfo.metaData.getString("MIPUSH_APP_KEY");
                MiPushClient.registerPush(getApplication(), appId, appKey);
            }
        } catch (Exception e) {}
    }

    /**
     * 获取所有别名
     *
     * @return
     */
    public static List<String> getAllAlias() {
        return MiPushClient.getAllAlias(getApplication());
    }

    /**
     * 是不是别名
     *
     * @param alias
     * @return
     */
    public static boolean isAlias(String alias) {
        if (TextUtils.isEmpty(alias)) return false;
        List<String> list = MiPushClient.getAllAlias(getApplication());
        if (list == null || list.size() == 0) return false;
        return list.contains(alias);
    }

    /**
     * 设置别名
     *
     * @param s
     */
    public static void setAlias(String s) {
        if (TextUtils.isEmpty(s)) return;
        if (TextUtils.isEmpty(MiPushClient.getRegId(getApplication()))) return;
        MiPushClient.setAlias(getApplication(), s, null);
    }

    /**
     * 取消别名
     *
     * @param s
     */
    public static void unsetAlias(String s) {
        if (TextUtils.isEmpty(s)) return;
        if (TextUtils.isEmpty(MiPushClient.getRegId(getApplication()))) return;
        MiPushClient.unsetAlias(getApplication(), s, null);
    }

    /**
     * 清空别名
     *
     */
    public static void clearAlias() {
        if (TextUtils.isEmpty(MiPushClient.getRegId(getApplication()))) return;
        List<String> list = MiPushClient.getAllAlias(getApplication());
        if (list == null || list.size() == 0) return;
        for (String s : list) {
            MiPushClient.unsetAlias(getApplication(), s, null);
        }
    }

    /**
     * 获取所有账户
     *
     * @return
     */
    public static List<String> getAllUserAccount() {
        return MiPushClient.getAllUserAccount(getApplication());
    }

    /**
     * 是不是账户
     *
     * @param account
     * @return
     */
    public static boolean isUserAccount(String account) {
        if (TextUtils.isEmpty(account)) return false;
        List<String> list = MiPushClient.getAllUserAccount(getApplication());
        if (list == null || list.size() == 0) return false;
        return list.contains(account);
    }

    /**
     * 设置账户
     *
     * @param s
     */
    public static void setUserAccount(String s) {
        if (TextUtils.isEmpty(s)) return;
        if (TextUtils.isEmpty(MiPushClient.getRegId(getApplication()))) return;
        MiPushClient.setUserAccount(getApplication(), s, null);
    }

    /**
     * 取消账户
     *
     * @param s
     */
    public static void unsetUserAccount(String s) {
        if (TextUtils.isEmpty(s)) return;
        if (TextUtils.isEmpty(MiPushClient.getRegId(getApplication()))) return;
        MiPushClient.unsetUserAccount(getApplication(), s, null);
    }

    /**
     * 清空账户
     *
     */
    public static void clearUserAccount() {
        if (TextUtils.isEmpty(MiPushClient.getRegId(getApplication()))) return;
        List<String> list = MiPushClient.getAllUserAccount(getApplication());
        for (String s : list) {
            MiPushClient.unsetUserAccount(getApplication(), s, null);
        }
    }

    static void sendData(String action, String data) {
        String pkg = getApplication().getPackageName();
        Intent intent = new Intent(pkg + ".RECEIVE_PUSH_MESSAGE");
        intent.setPackage(pkg);
        intent.putExtra("type", 0b1);
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
            return MiPushUtils.app = (Application) app;
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

    /**
     * 判断当前是不是主进程
     *
     * @return
     */
    private static boolean isMainProcess() {
        ActivityManager am = ((ActivityManager) getApplication().getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getApplication().getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
