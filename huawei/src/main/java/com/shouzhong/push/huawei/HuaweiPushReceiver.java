package com.shouzhong.push.huawei;

import android.content.Context;
import android.os.Bundle;

import com.huawei.hms.support.api.push.PushReceiver;

public class HuaweiPushReceiver extends PushReceiver {

    @Override
    public void onToken(Context context, String token) {
        HuaweiPushUtils.sendData("action.TOKEN", token);
    }

    /**
     * 该方法不可用
     *
     * @param context
     * @param event
     * @param extras
     */
    @Override
    public void onEvent(Context context, Event event, Bundle extras) {
    }

    @Override
    public void onPushMsg(Context context, byte[] msg, String token) {
        try {
            HuaweiPushUtils.sendData("action.PASS_THROUGH_MESSAGE", new String(msg, "UTF-8"));
        } catch (Exception e) {}
    }
}
