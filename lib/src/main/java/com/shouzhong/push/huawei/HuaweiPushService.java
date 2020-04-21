package com.shouzhong.push.huawei;

import android.text.TextUtils;

import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;

import static com.shouzhong.push.PushConstants.*;
import static com.shouzhong.push.PushUtils.*;

public class HuaweiPushService extends HmsMessageService {
    /**
     * 发生变化时或emui10.0以下会调这个方法
     *
     * @param token
     */
    @Override
    public void onNewToken(String token) {
        sendData(TYPE_HUAWEI, ACTION_TOKEN, token);
    }

    @Override
    public void onMessageReceived(RemoteMessage msg) {
        try {
            if (msg == null) return;
            StringBuffer sb = new StringBuffer("{");
            sb.append("\"title\":\"").append(msg.getNotification().getTitle()).append("\",");
            sb.append("\"content\":\"").append(msg.getNotification().getBody()).append("\",");
            sb.append("\"message_id\":\"").append(msg.getMessageId()).append("\",");
            sb.append("\"notify_id\":").append(msg.getNotification().getNotifyId());
            String data = msg.getData();
            if (!TextUtils.isEmpty(data) && data.startsWith("{")) {
                sb.append(",\"extra\":").append(data);
            }
            sb.append("}");
            sendData(TYPE_HUAWEI, ACTION_PASS_THROUGH_MESSAGE, sb.toString());
        } catch (Exception e) {}
    }
}
