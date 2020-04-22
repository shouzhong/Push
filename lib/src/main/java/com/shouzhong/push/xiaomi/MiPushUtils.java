package com.shouzhong.push.xiaomi;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

import static com.shouzhong.push.PushUtils.*;

public class MiPushUtils {

    /**
     * <meta-data
     *  android:name="XIAOMI_PUSH_APP_ID"
     *  android:value="value=xxxxxxx"/>
     *  <meta-data
     *  android:name="XIAOMI_PUSH_APP_KEY"
     *  android:value="value=xxxxxxx"/>
     *
     * @param context
     */
    public static void init(Context context) {
        try {
            if (isMainProcess()) {
                Context cxt = context.getApplicationContext();
                ApplicationInfo appInfo = cxt.getPackageManager().getApplicationInfo(cxt.getPackageName(), PackageManager.GET_META_DATA);
                String appId = appInfo.metaData.getString("XIAOMI_PUSH_APP_ID").substring(6);
                String appKey = appInfo.metaData.getString("XIAOMI_PUSH_APP_KEY").substring(6);
                MiPushClient.registerPush(cxt, appId, appKey);
            }
        } catch (Exception e) {}
    }

    /**
     * 开启推送
     *
     * @param context
     */
    public static void turnOnPush(Context context) {
        MiPushClient.turnOnPush(context.getApplicationContext(), new MiPushClient.UPSTurnCallBack() {
            @Override
            public void onResult(MiPushClient.CodeResult codeResult) {
            }
        });
    }

    /**
     * 关闭推送
     *
     * @param context
     */
    public static void turnOffPush(Context context) {
        MiPushClient.turnOffPush(context.getApplicationContext(), new MiPushClient.UPSTurnCallBack() {
            @Override
            public void onResult(MiPushClient.CodeResult codeResult) {
            }
        });
    }

    /**
     * 获取推送唯一标识
     *
     * @param context
     * @return
     */
    public static String getRegId(Context context) {
        return MiPushClient.getRegId(context.getApplicationContext());
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
        if (TextUtils.isEmpty(getRegId(context))) return;
        MiPushClient.setAlias(context.getApplicationContext(), s, null);
    }

    /**
     * 取消别名
     *
     * @param s
     */
    public static void unsetAlias(Context context, String s) {
        if (TextUtils.isEmpty(s)) return;
        if (TextUtils.isEmpty(getRegId(context))) return;
        MiPushClient.unsetAlias(context.getApplicationContext(), s, null);
    }

    /**
     * 清空别名
     *
     */
    public static void clearAlias(Context context) {
        if (TextUtils.isEmpty(getRegId(context))) return;
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
        if (TextUtils.isEmpty(getRegId(context))) return;
        MiPushClient.setUserAccount(context.getApplicationContext(), s, null);
    }

    /**
     * 取消账户
     *
     * @param s
     */
    public static void unsetUserAccount(Context context, String s) {
        if (TextUtils.isEmpty(s)) return;
        if (TextUtils.isEmpty(getRegId(context))) return;
        MiPushClient.unsetUserAccount(context.getApplicationContext(), s, null);
    }

    /**
     * 清空账户
     *
     */
    public static void clearUserAccount(Context context) {
        if (TextUtils.isEmpty(getRegId(context))) return;
        List<String> list = MiPushClient.getAllUserAccount(context.getApplicationContext());
        for (String s : list) {
            MiPushClient.unsetUserAccount(context.getApplicationContext(), s, null);
        }
    }
}
