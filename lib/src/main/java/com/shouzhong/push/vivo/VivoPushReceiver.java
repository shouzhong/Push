package com.shouzhong.push.vivo;

import android.content.Context;

import com.vivo.push.model.UPSNotificationMessage;
import com.vivo.push.sdk.OpenClientPushMessageReceiver;

import java.util.Map;

import static com.shouzhong.push.PushConstants.*;
import static com.shouzhong.push.PushUtils.*;

public class VivoPushReceiver extends OpenClientPushMessageReceiver {
    @Override
    public void onNotificationMessageClicked(Context context, UPSNotificationMessage upsNotificationMessage) {
        sendData(TYPE_VIVO, ACTION_NOTIFICATION_MESSAGE_CLICKED, toJson(upsNotificationMessage));
    }

    @Override
    public void onReceiveRegId(Context context, String s) {
        sendData(TYPE_VIVO, ACTION_TOKEN, s);
    }

    private String toJson(UPSNotificationMessage msg) {
        if (msg == null) return null;
        try {
            StringBuffer sb = new StringBuffer("{");
            sb.append("\"title\":\"").append(msg.getTitle()).append("\",");
            sb.append("\"content\":\"").append(msg.getContent()).append("\",");
            sb.append("\"message_id\":\"").append(msg.getMsgId()).append("\"");
            Map<String, String> map = msg.getParams();
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
