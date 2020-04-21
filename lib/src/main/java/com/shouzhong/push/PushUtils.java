package com.shouzhong.push;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Process;

import com.shouzhong.push.huawei.HuaweiPushUtils;
import com.shouzhong.push.xiaomi.MiPushUtils;

import java.util.List;

import static com.shouzhong.push.PushConstants.*;

public class PushUtils {

    private static int type;
    private static int defaultType;

    private static Application app;

    /**
     * 初始化
     *
     * @param context 对于华为推送，如果context为activity，则用户如果未安装hms或者hms版本过低时会弹框提示用户去下载
     * @param type 使用哪些推送,比如：TYPE_XIAOMI | TYPE_HUAWEI
     * @param defaultType 当和你设置的推送不匹配时，默认使用哪个推送，比如：TYPE_XIAOMI
     */
    public static void init(Context context, int type, int defaultType) {
        PushUtils.type = type;
        PushUtils.defaultType = defaultType;
        int currentType = currentPushType();
        if (currentType == TYPE_XIAOMI) {
            MiPushUtils.init(context);
            return;
        }
        if (currentType == TYPE_HUAWEI) {
            HuaweiPushUtils.init(context);
            return;
        }
    }

    /**
     * 获取所有别名
     *
     * @return
     */
    public static List<String> getAllAlias() {
        int type = currentPushType();
        if (type == TYPE_XIAOMI) {
            return MiPushUtils.getAllAlias(getApplication());
        }
        return null;
    }

    /**
     * 是不是别名
     *
     * @param alias
     * @return
     */
    public static boolean isAlias(String alias) {
        int type = currentPushType();
        if (type == TYPE_XIAOMI) {
            return MiPushUtils.isAlias(getApplication(), alias);
        }
        return false;
    }

    /**
     * 设置别名
     *
     * @param s
     */
    public static void setAlias(String s) {
        int type = currentPushType();
        if (type == TYPE_XIAOMI) {
            MiPushUtils.setAlias(getApplication(), s);
            return;
        }
    }

    /**
     * 取消别名
     *
     * @param s
     */
    public static void unsetAlias(String s) {
        int type = currentPushType();
        if (type == TYPE_XIAOMI) {
            MiPushUtils.unsetAlias(getApplication(), s);
            return;
        }
    }

    /**
     * 清空别名
     *
     */
    public static void clearAlias() {
        int type = currentPushType();
        if (type == TYPE_XIAOMI) {
            MiPushUtils.clearAlias(getApplication());
            return;
        }
    }

    /**
     * 获取所有账户
     *
     * @return
     */
    public static List<String> getAllUserAccount() {
        int type = currentPushType();
        if (type == TYPE_XIAOMI) {
            return MiPushUtils.getAllUserAccount(getApplication());
        }
        return null;
    }

    /**
     * 是不是账户
     *
     * @param account
     * @return
     */
    public static boolean isUserAccount(String account) {
        int type = currentPushType();
        if (type  == TYPE_XIAOMI) {
            return MiPushUtils.isUserAccount(getApplication(), account);
        }
        return false;
    }

    /**
     * 设置账户
     *
     * @param s
     */
    public static void setUserAccount(String s) {
        int type = currentPushType();
        if (type == TYPE_XIAOMI) {
            MiPushUtils.setUserAccount(getApplication(), s);
            return;
        }
    }

    /**
     * 取消账户
     *
     * @param s
     */
    public static void unsetUserAccount(String s) {
        int type = currentPushType();
        if (type == TYPE_XIAOMI) {
            MiPushUtils.unsetUserAccount(getApplication(), s);
            return;
        }
    }

    /**
     * 清空账户
     *
     */
    public static void clearUserAccount() {
        int type = currentPushType();
        if (type == TYPE_XIAOMI) {
            MiPushUtils.clearUserAccount(getApplication());
            return;
        }
    }

    /**
     * 当前推送类型
     *
     * @return
     */
    public static int currentPushType() {
        if ((type & TYPE_XIAOMI) == TYPE_XIAOMI && RomUtils.isMeizu()) return TYPE_MEIZU;
        if ((type & TYPE_HUAWEI) == TYPE_HUAWEI && RomUtils.isHuawei()) return TYPE_HUAWEI;
        if ((type & TYPE_OPPO) == TYPE_OPPO && RomUtils.isOppo()) return TYPE_OPPO;
        if ((type & TYPE_VIVO) == TYPE_VIVO && RomUtils.isVivo()) return TYPE_VIVO;
        if ((type & TYPE_MEIZU) == TYPE_MEIZU && RomUtils.isMeizu()) return TYPE_MEIZU;
        return defaultType;
    }

//    /**
//     * 判断是否可以使用华为推送
//     *
//     * @return
//     */
//    public static boolean canHuaweiPush() {
//        int emuiApiLevel = 0;
//        try {
//            Class cls = Class.forName("android.os.SystemProperties");
//            Method method = cls.getDeclaredMethod("get", new Class[]{String.class});
//            emuiApiLevel = Integer.parseInt((String) method.invoke(cls, new Object[]{"ro.build.hw_emui_api_level"}));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return emuiApiLevel > 5.0;
//    }

    public static Application getApplication() {
        if (app != null) return app;
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return PushUtils.app = (Application) app;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }

    /**
     * 判断当前是不是主进程
     *
     * @return
     */
    public static boolean isMainProcess() {
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

    /**
     * 发送广播公共方法
     *
     * @param type
     * @param action
     * @param data
     */
    public static void sendData(int type, String action, String data) {
        String pkg = getApplication().getPackageName();
        Intent intent = new Intent(pkg + ".RECEIVE_PUSH_MESSAGE");
        intent.setPackage(pkg);
        intent.putExtra("type", type);
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
}
