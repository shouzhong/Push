package com.shouzhong.push.xiaomi;

import android.content.Context;
import android.text.TextUtils;

import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.Map;

import static com.shouzhong.push.PushConstants.*;
import static com.shouzhong.push.PushUtils.*;

public class MiPushReceiver extends PushMessageReceiver {
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        sendData(TYPE_XIAOMI, ACTION_PASS_THROUGH_MESSAGE, toJson(miPushMessage));
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        sendData(TYPE_XIAOMI, ACTION_NOTIFICATION_MESSAGE, toJson(miPushMessage));
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        sendData(TYPE_XIAOMI, ACTION_NOTIFICATION_MESSAGE_CLICKED, toJson(miPushMessage));
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        if (TextUtils.equals(miPushCommandMessage.getCommand(), MiPushClient.COMMAND_REGISTER)) {
            String token = MiPushClient.getRegId(context);
            sendData(TYPE_XIAOMI, ACTION_TOKEN, token);
        }
    }

    private String toJson(MiPushMessage msg) {
        if (msg == null) return null;
        try {
            StringBuffer sb = new StringBuffer("{");
            sb.append("\"title\":\"").append(msg.getTitle()).append("\",");
            sb.append("\"content\":\"").append(msg.getContent()).append("\",");
            sb.append("\"description\":\"").append(msg.getDescription()).append("\",");
            sb.append("\"message_id\":\"").append(msg.getMessageId()).append("\",");
            sb.append("\"notify_id\":").append(msg.getNotifyId());
            Map<String, String> map = msg.getExtra();
            if (map != null && map.size() > 0) {
                sb.append(",\"extra\":{");
                for (String s : map.keySet()) {
                    sb.append("\"").append(s).append("\":\"").append(map.get(s)).append("\",");
                }
                sb.setCharAt(sb.length() - 1, '}');
            }
            sb.append("}");
            return sb.toString();
        } catch (Exception e) {}
        return null;
    }
}
