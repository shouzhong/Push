package com.shouzhong.push.xiaomi;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

import static com.shouzhong.push.PushUtils.*;

public class MiPushUtils {

    public static void init(Context context) {
        try {
            if (isMainProcess()) {
                Context cxt = context.getApplicationContext();
                ApplicationInfo appInfo = cxt.getPackageManager().getApplicationInfo(cxt.getPackageName(), PackageManager.GET_META_DATA);
                String appId = appInfo.metaData.getString("MIPUSH_APP_ID");
                String appKey = appInfo.metaData.getString("MIPUSH_APP_KEY");
                MiPushClient.registerPush(cxt, appId, appKey);
            }
        } catch (Exception e) {}
    }

    /**
     * 获取所有别名
     *
     * @return
     */
    public static List<String> getAllAlias(Context context) {
        return MiPushClient.getAllAlias(context.getApplicationContext());
    }

    /**
     * 是不是别名
     *
     * @param alias
     * @return
     */
    public static boolean isAlias(Context context, String alias) {
        if (TextUtils.isEmpty(alias)) return false;
        List<String> list = MiPushClient.getAllAlias(context.getApplicationContext());
        if (list == null || list.size() == 0) return false;
        return list.contains(alias);
    }

    /**
     * 设置别名
     *
     * @param s
     */
    public static void setAlias(Context context, String s) {
        if (TextUtils.isEmpty(s)) return;
        if (TextUtils.isEmpty(MiPushClient.getRegId(context.getApplicationContext()))) return;
        MiPushClient.setAlias(context.getApplicationContext(), s, null);
    }

    /**
     * 取消别名
     *
     * @param s
     */
    public static void unsetAlias(Context context, String s) {
        if (TextUtils.isEmpty(s)) return;
        if (TextUtils.isEmpty(MiPushClient.getRegId(context.getApplicationContext()))) return;
        MiPushClient.unsetAlias(context.getApplicationContext(), s, null);
    }

    /**
     * 清空别名
     *
     */
    public static void clearAlias(Context context) {
        if (TextUtils.isEmpty(MiPushClient.getRegId(context.getApplicationContext()))) return;
        List<String> list = MiPushClient.getAllAlias(context.getApplicationContext());
        if (list == null || list.size() == 0) return;
        for (String s : list) {
            MiPushClient.unsetAlias(context.getApplicationContext(), s, null);
        }
    }

    /**
     * 获取所有账户
     *
     * @return
     */
    public static List<String> getAllUserAccount(Context context) {
        return MiPushClient.getAllUserAccount(context.getApplicationContext());
    }

    /**
     * 是不是账户
     *
     * @param account
     * @return
     */
    public static boolean isUserAccount(Context context, String account) {
        if (TextUtils.isEmpty(account)) return false;
        List<String> list = MiPushClient.getAllUserAccount(context.getApplicationContext());
        if (list == null || list.size() == 0) return false;
        return list.contains(account);
    }

    /**
     * 设置账户
     *
     * @param s
     */
    public static void setUserAccount(Context context, String s) {
        if (TextUtils.isEmpty(s)) return;
        if (TextUtils.isEmpty(MiPushClient.getRegId(context.getApplicationContext()))) return;
        MiPushClient.setUserAccount(context.getApplicationContext(), s, null);
    }

    /**
     * 取消账户
     *
     * @param s
     */
    public static void unsetUserAccount(Context context, String s) {
        if (TextUtils.isEmpty(s)) return;
        if (TextUtils.isEmpty(MiPushClient.getRegId(context.getApplicationContext()))) return;
        MiPushClient.unsetUserAccount(context.getApplicationContext(), s, null);
    }

    /**
     * 清空账户
     *
     */
    public static void clearUserAccount(Context context) {
        if (TextUtils.isEmpty(MiPushClient.getRegId(context.getApplicationContext()))) return;
        List<String> list = MiPushClient.getAllUserAccount(context.getApplicationContext());
        for (String s : list) {
            MiPushClient.unsetUserAccount(context.getApplicationContext(), s, null);
        }
    }
}
