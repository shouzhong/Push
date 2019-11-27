package com.shouzhong.push.demo;

import android.content.Context;

import com.shouzhong.push.BasePushReceiver;

public class PushReceiver extends BasePushReceiver {
    /**
     *
     *
     * @param context
     * @param type PushUtils.TYPE_XIAOMI：小米推送，PushUtils.TYPE_HUAWEI：华为推送
     * @param action PushUtils.ACTION_PASS_THROUGH_MESSAGE：透传消息
     *               PushUtils.ACTION_NOTIFICATION_MESSAGE：通知栏消息，华为推送不支持
     *               PushUtils.ACTION_NOTIFICATION_MESSAGE_CLICKED：通知栏点击，华为推送不支持
     *               PushUtils.ACTION_TOKEN：小米推送regId，华为推送是token
     * @param data action对应的数据，透传消息，通知栏消息，通知栏点击为json，token为字符串
     */
    @Override
    public void onReceive(Context context, int type, String action, String data) {

    }
}
