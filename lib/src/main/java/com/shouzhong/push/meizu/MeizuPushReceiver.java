package com.shouzhong.push.meizu;

import android.content.Context;
import android.text.TextUtils;

import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;

import static com.shouzhong.push.PushConstants.*;
import static com.shouzhong.push.PushUtils.*;

public class MeizuPushReceiver extends MzPushMessageReceiver {
    @Override
    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
        if (TextUtils.isEmpty(registerStatus.getPushId())) return;
        sendData(TYPE_MEIZU, ACTION_TOKEN, registerStatus.getPushId());
    }

    @Override
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {

    }

    @Override
    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {

    }

    @Override
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {

    }

    @Override
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {

    }

    @Override
    public void onNotificationClicked(Context context, MzPushMessage mzPushMessage) {
        sendData(TYPE_MEIZU, ACTION_NOTIFICATION_MESSAGE_CLICKED, toJson(mzPushMessage));
    }

    @Override
    public void onNotificationArrived(Context context, MzPushMessage mzPushMessage) {
        sendData(TYPE_MEIZU, ACTION_NOTIFICATION_MESSAGE, toJson(mzPushMessage));
    }

    private String toJson(MzPushMessage msg) {
        if (msg == null) return null;
        try {
            StringBuffer sb = new StringBuffer("{");
            sb.append("\"title\":\"").append(msg.getTitle()).append("\",");
            sb.append("\"content\":\"").append(msg.getContent()).append("\",");
            sb.append("\"notify_id\":").append(msg.getNotifyId()).append("}");
            return sb.toString();
        } catch (Exception e) {}
        return null;
    }
}
