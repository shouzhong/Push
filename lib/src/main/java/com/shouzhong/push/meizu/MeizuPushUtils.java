package com.shouzhong.push.meizu;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.meizu.cloud.pushsdk.PushManager;

public class MeizuPushUtils {

    private static String appId;
    private static String appKey;

    private static String getAppId(Context context) {
        if (TextUtils.isEmpty(appId)) {
            try {
                Context cxt = context.getApplicationContext();
                ApplicationInfo appInfo = cxt.getPackageManager().getApplicationInfo(cxt.getPackageName(), PackageManager.GET_META_DATA);
                appId = appInfo.metaData.getString("MEIZU_PUSH_APP_ID").substring(6);
                appKey = appInfo.metaData.getString("MEIZU_PUSH_APP_KEY").substring(6);
            } catch (Exception e) {}
        }
        return appId;
    }

    private static String getAppKey(Context context) {
        if (TextUtils.isEmpty(appKey)) {
            try {
                Context cxt = context.getApplicationContext();
                ApplicationInfo appInfo = cxt.getPackageManager().getApplicationInfo(cxt.getPackageName(), PackageManager.GET_META_DATA);
                appId = appInfo.metaData.getString("MEIZU_PUSH_APP_ID").substring(6);
                appKey = appInfo.metaData.getString("MEIZU_PUSH_APP_KEY").substring(6);
            } catch (Exception e) {}
        }
        return appKey;
    }

    /**
     * <meta-data
     *  android:name="MEIZU_PUSH_APP_ID"
     *  android:value="value=xxxxx"/>
     * <meta-data
     *  android:name="MEIZU_PUSH_APP_KEY"
     *  android:value="value=xxxxx"/>
     *
     * @param context
     */
    public static void init(Context context) {
        PushManager.register(context.getApplicationContext(), getAppId(context), getAppKey(context));
    }

    /**
     * 开启推送
     *
     * @param context
     */
    public static void turnOnPush(Context context) {
        PushManager.switchPush(context.getApplicationContext(), getAppId(context), getAppKey(context), getPushId(context), true);
    }

    /**
     * 关闭推送
     *
     * @param context
     */
    public static void turnOffPush(Context context) {
        PushManager.switchPush(context.getApplicationContext(), getAppId(context), getAppKey(context), getPushId(context), true);
    }

    /**
     * 获取推送唯一标识
     *
     * @param context
     * @return
     */
    public static String getPushId(Context context) {
        return PushManager.getPushId(context.getApplicationContext());
    }

    /**
     * 设置别名
     *
     * @param context
     * @param s
     */
    public static void setAlias(Context context, String s) {
        PushManager.subScribeAlias(context.getApplicationContext(), getAppId(context), getAppKey(context), getPushId(context), s);
    }

    /**
     * 删除别名
     *
     * @param context
     * @param s
     */
    public static void unsetAlias(Context context, String s) {
        PushManager.unSubScribeAlias(context.getApplicationContext(), getAppId(context), getAppKey(context), getPushId(context), s);
    }

    /**
     * 设置标签
     *
     * @param context
     * @param s
     */
    public static void setTag(Context context, String s) {
        PushManager.subScribeTags(context.getApplicationContext(), getAppId(context), getAppKey(context), getPushId(context), s);
    }

    /**
     * 删除标签
     *
     * @param context
     * @param s
     */
    public static void unsetTag(Context context, String s) {
        PushManager.unSubScribeTags(context.getApplicationContext(), getAppId(context), getAppKey(context), getPushId(context), s);
    }

    /**
     * 清空标签
     *
     * @param context
     */
    public static void clearTag(Context context) {
        PushManager.unSubScribeAllTags(context.getApplicationContext(), getAppId(context), getAppKey(context), getPushId(context));
    }
}
