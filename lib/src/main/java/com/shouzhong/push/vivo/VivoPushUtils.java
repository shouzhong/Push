package com.shouzhong.push.vivo;

import android.content.Context;
import android.text.TextUtils;

import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;

import java.util.List;

import static com.shouzhong.push.PushUtils.*;

public class VivoPushUtils {
    /**
     * <meta-data
     *  android:name="com.vivo.push.api_key"
     *  android:value="xxxxxxxx"/>
     *  <meta-data
     *  android:name="com.vivo.push.app_id"
     *  android:value="xxxx"/>
     *
     * @param context
     */
    public static void init(Context context) {
        getClient(context).initialize();
    }

    private static PushClient getClient(Context context) {
        return PushClient.getInstance(context.getApplicationContext());
    }

    /**
     * 是否支持vivo推送
     *
     * @param context
     * @return
     */
    public static boolean isSupportPush(Context context) {
        return getClient(context).isSupport();
    }

    /**
     * 开启推送
     *
     * @param context
     */
    public static void turnOnPush(Context context) {
        getClient(context).turnOnPush(new IPushActionListener() {
            @Override
            public void onStateChanged(int i) {
            }
        });
    }

    /**
     * 关闭推送
     *
     * @param context
     */
    public static void turnOffPush(Context context) {
        getClient(context).turnOffPush(new IPushActionListener() {
            @Override
            public void onStateChanged(int i) {
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
        return getClient(context).getRegId();
    }

    /**
     * 设置别名
     *
     * @param context
     * @param s
     */
    public static void setAlias(Context context, String s) {
        if (TextUtils.isEmpty(s)) return;
        if (TextUtils.isEmpty(getRegId(context))) return;
        getClient(context).bindAlias(s, new IPushActionListener() {
            @Override
            public void onStateChanged(int i) {
            }
        });
    }

    /**
     * 删除别名
     *
     * @param context
     * @param s
     */
    public static void unsetAlias(Context context, String s) {
        if (TextUtils.isEmpty(s)) return;
        if (TextUtils.isEmpty(getRegId(context))) return;
        getClient(context).unBindAlias(s, new IPushActionListener() {
            @Override
            public void onStateChanged(int i) {
            }
        });
    }

    /**
     * 获取别名
     *
     * @param context
     * @return
     */
    public static String getAlias(Context context) {
        if (TextUtils.isEmpty(getRegId(context))) return null;
        return getClient(context).getAlias();
    }

    /**
     * 设置主题
     *
     * @param context
     * @param s
     */
    public static void setTopic(Context context, String s) {
        if (TextUtils.isEmpty(s)) return;
        getClient(context).setTopic(s, new IPushActionListener() {
            @Override
            public void onStateChanged(int i) {
            }
        });
    }

    /**
     * 删除主题
     *
     * @param context
     * @param s
     */
    public static void unsetTopic(Context context, String s) {
        if (TextUtils.isEmpty(s)) return;
        getClient(context).delTopic(s, new IPushActionListener() {
            @Override
            public void onStateChanged(int i) {
            }
        });
    }

    /**
     * 获取主题
     *
     * @param context
     * @return
     */
    public static List<String> getAllTopic(Context context) {
        return getClient(context).getTopics();
    }
}
