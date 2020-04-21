package com.shouzhong.push;

public class PushConstants {
    // 小米推送
    public static final int TYPE_XIAOMI = 1;
    // 华为推送
    public static final int TYPE_HUAWEI = 1 << 1;
    // oppo推送
    public static final int TYPE_OPPO = 1 << 2;
    // vivo推送
    public static final int TYPE_VIVO = 1 << 3;
    // 魅族推送
    public static final int TYPE_MEIZU = 1 << 4;
    // 透传消息
    public static final String ACTION_PASS_THROUGH_MESSAGE = "action.PASS_THROUGH_MESSAGE";
    // 通知栏消息
    public static final String ACTION_NOTIFICATION_MESSAGE = "action.NOTIFICATION_MESSAGE";
    // 通知栏点击
    public static final String ACTION_NOTIFICATION_MESSAGE_CLICKED = "action.NOTIFICATION_MESSAGE_CLICKED";
    // token
    public static final String ACTION_TOKEN = "action.TOKEN";
}
