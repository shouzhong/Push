package com.shouzhong.push.xiaomi;

import android.content.Context;
import android.text.TextUtils;

import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import org.json.JSONObject;

import java.util.Map;

public class MiPushReceiver extends PushMessageReceiver {
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        MiPushUtils.sendData("ACTION_PASS_THROUGH_MESSAGE", toJson(miPushMessage));
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        MiPushUtils.sendData("ACTION_NOTIFICATION_MESSAGE", toJson(miPushMessage));
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        MiPushUtils.sendData("action.NOTIFICATION_MESSAGE_CLICKED", toJson(miPushMessage));
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        if (TextUtils.equals(miPushCommandMessage.getCommand(), MiPushClient.COMMAND_REGISTER)) {
            String token = MiPushClient.getRegId(context);
            MiPushUtils.sendData("ACTION_TOKEN", token);
        }
    }

    private String toJson(MiPushMessage msg) {
        if (msg == null || msg.getExtra() == null || msg.getExtra().size() == 0) return null;
        try {
            Map<String, String> map = msg.getExtra();
            JSONObject jo = new JSONObject();
            for (String s : map.keySet()) {
                jo.put(s, map.get(s));
            }
            return jo.toString();
        } catch (Exception e) {}
        return null;
    }
}
