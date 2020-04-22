package com.shouzhong.push.oppo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;


import com.heytap.msp.push.HeytapPushManager;
import com.heytap.msp.push.callback.ICallBackResultService;

import static com.shouzhong.push.PushConstants.*;
import static com.shouzhong.push.PushUtils.*;

public class OppoPushUtils {
    /**
     * <meta-data
     *  android:name="OPPO_PUSH_APP_KEY"
     *  android:value="value=xxxxxxxx"/>
     *  <meta-data
     *  android:name="OPPOPUSH_APP_SECRET"
     *  android:value="value=xxxxxxxx"/>
     *
     * @param context
     */
    public static void init(Context context) {
        try {
            Context cxt = context.getApplicationContext();
            ApplicationInfo appInfo = cxt.getPackageManager().getApplicationInfo(cxt.getPackageName(), PackageManager.GET_META_DATA);
            String appKey = appInfo.metaData.getString("OPPO_PUSH_APP_KEY").substring(6);
            String appSecret = appInfo.metaData.getString("OPPO_PUSH_APP_SECRET").substring(6);
            HeytapPushManager.register(context.getApplicationContext(), appKey, appSecret, new ICallBackResultService() {
                @Override
                public void onRegister(int i, String s) {
                    if (i == 0 && !TextUtils.isEmpty(s)) sendData(TYPE_OPPO, ACTION_TOKEN, s);
                }

                @Override
                public void onUnRegister(int i) {

                }

                @Override
                public void onSetPushTime(int i, String s) {

                }

                @Override
                public void onGetPushStatus(int i, int i1) {

                }

                @Override
                public void onGetNotificationStatus(int i, int i1) {

                }
            });
        } catch (Exception e) {}
    }

    /**
     * 获取推送唯一标识
     *
     * @return
     */
    public static String getRegId() {
        return HeytapPushManager.getRegisterID();
    }

    /**
     * 是否支持oppo推送
     *
     * @return
     */
    public static boolean isSupportPush() {
        return HeytapPushManager.isSupportPush();
    }

    /**
     * 开启推送
     *
     */
    public static void turnOnPush() {
        HeytapPushManager.resumePush();
    }

    /**
     * 关闭推送
     *
     */
    public static void turnOffPush() {
        HeytapPushManager.pausePush();
    }
}
