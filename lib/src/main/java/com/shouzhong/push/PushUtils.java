package com.shouzhong.push;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;

import com.shouzhong.push.huawei.HuaweiPushUtils;
import com.shouzhong.push.xiaomi.MiPushUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class PushUtils {

    // 小米
    public static final int TYPE_XIAOMI = 0b1;
    // 华为
    public static final int TYPE_HUAWEI = 0b10;
    // 混合
    public static final int TYPE_MIX = 0b11;
    // 透传消息
    public static final String ACTION_PASS_THROUGH_MESSAGE = "action.PASS_THROUGH_MESSAGE";
    // 通知栏消息
    public static final String ACTION_NOTIFICATION_MESSAGE = "action.NOTIFICATION_MESSAGE";
    // 通知栏点击
    public static final String ACTION_NOTIFICATION_MESSAGE_CLICKED = "action.NOTIFICATION_MESSAGE_CLICKED";
    // token
    public static final String ACTION_TOKEN = "action.TOKEN";

    private static int type;
    private static Application app;

    /**
     * 初始化，在application的onCreate
     *
     * @param app
     * @param type
     */
    public static void init(Application app, int type) {
        PushUtils.type = type;
        PushUtils.app = app;
        if ((type & TYPE_MIX) == TYPE_MIX) {
            if (canHuaweiPush()) HuaweiPushUtils.init(getApplication());
            else MiPushUtils.init(getApplication());
            return;
        }
        if ((type & TYPE_XIAOMI) == TYPE_XIAOMI) {
            MiPushUtils.init(getApplication());
        }
        if ((type & TYPE_HUAWEI) == TYPE_HUAWEI) {
            HuaweiPushUtils.init(getApplication());
        }
    }

    /**
     * 初始化，最好在第一个activity的onCreate，不用华为推送忽略
     *
     * @param activity
     */
    public static void init(Activity activity) {
        if ((type & TYPE_MIX) == TYPE_MIX) {
            if (canHuaweiPush()) HuaweiPushUtils.getToken(activity);
            return;
        }
        if ((type & TYPE_XIAOMI) == TYPE_XIAOMI) {

        }
        if ((type & TYPE_HUAWEI) == TYPE_HUAWEI) {
            HuaweiPushUtils.getToken(activity);
        }
    }

    /**
     * 重新连接
     *
     * @param activity
     */
    public static void reconnect(Activity activity) {
        if ((type & TYPE_MIX) == TYPE_MIX) {
            if (canHuaweiPush()) HuaweiPushUtils.getToken(activity);
            return;
        }
        if ((type & TYPE_XIAOMI) == TYPE_XIAOMI) {
            MiPushUtils.init(getApplication());
        }
        if ((type & TYPE_HUAWEI) == TYPE_HUAWEI) {
            HuaweiPushUtils.getToken(activity);
        }
    }

    /**
     * 获取所有别名
     *
     * @return
     */
    public static List<String> getAllAlias() {
        if ((type & TYPE_MIX) == TYPE_MIX) {
            if (canHuaweiPush()) return null;
            return MiPushUtils.getAllAlias();
        }
        if ((type & TYPE_XIAOMI) == TYPE_XIAOMI) {
            return MiPushUtils.getAllAlias();
        }
        if ((type & TYPE_HUAWEI) == TYPE_HUAWEI) {
            return null;
        }
        return null;
    }

    /**
     * 是不是别名
     *
     * @param alias
     * @return
     */
    public static boolean isAlias(String alias) {
        if ((type & TYPE_MIX) == TYPE_MIX) {
            if (canHuaweiPush()) return false;
            return MiPushUtils.isAlias(alias);
        }
        if ((type & TYPE_XIAOMI) == TYPE_XIAOMI) {
            return MiPushUtils.isAlias(alias);
        }
        if ((type & TYPE_HUAWEI) == TYPE_HUAWEI) {
            return false;
        }
        return false;
    }

    /**
     * 设置别名
     *
     * @param s
     */
    public static void setAlias(String s) {
        if ((type & TYPE_MIX) == TYPE_MIX) {
            if (canHuaweiPush()) return;
            MiPushUtils.setAlias(s);
            return;
        }
        if ((type & TYPE_XIAOMI) == TYPE_XIAOMI) {
            MiPushUtils.setAlias(s);
            return;
        }
        if ((type & TYPE_HUAWEI) == TYPE_HUAWEI) {

        }
    }

    /**
     * 取消别名
     *
     * @param s
     */
    public static void unsetAlias(String s) {
        if ((type & TYPE_MIX) == TYPE_MIX) {
            if (canHuaweiPush()) return;
            MiPushUtils.unsetAlias(s);
            return;
        }
        if ((type & TYPE_XIAOMI) == TYPE_XIAOMI) {
            MiPushUtils.unsetAlias(s);
            return;
        }
        if ((type & TYPE_HUAWEI) == TYPE_HUAWEI) {

        }
    }

    /**
     * 清空别名
     *
     */
    public static void clearAlias() {
        if ((type & TYPE_MIX) == TYPE_MIX) {
            if (canHuaweiPush()) return;
            MiPushUtils.clearAlias();
            return;
        }
        if ((type & TYPE_XIAOMI) == TYPE_XIAOMI) {
            MiPushUtils.clearAlias();
            return;
        }
        if ((type & TYPE_HUAWEI) == TYPE_HUAWEI) {

        }
    }

    /**
     * 获取所有账户
     *
     * @return
     */
    public static List<String> getAllUserAccount() {
        if ((type & TYPE_MIX) == TYPE_MIX) {
            if (canHuaweiPush()) return null;
            return MiPushUtils.getAllUserAccount();
        }
        if ((type & TYPE_XIAOMI) == TYPE_XIAOMI) {
            return MiPushUtils.getAllUserAccount();
        }
        if ((type & TYPE_HUAWEI) == TYPE_HUAWEI) {
            return null;
        }
        return null;
    }

    /**
     * 是不是账户
     *
     * @param account
     * @return
     */
    public static boolean isUserAccount(String account) {
        if ((type & TYPE_MIX) == TYPE_MIX) {
            if (canHuaweiPush()) return false;
            return MiPushUtils.isUserAccount(account);
        }
        if ((type & TYPE_XIAOMI) == TYPE_XIAOMI) {
            return MiPushUtils.isUserAccount(account);
        }
        if ((type & TYPE_HUAWEI) == TYPE_HUAWEI) {
            return false;
        }
        return false;
    }

    /**
     * 设置账户
     *
     * @param s
     */
    public static void setUserAccount(String s) {
        if ((type & TYPE_MIX) == TYPE_MIX) {
            if (canHuaweiPush()) return;
            MiPushUtils.setUserAccount(s);
            return;
        }
        if ((type & TYPE_XIAOMI) == TYPE_XIAOMI) {
            MiPushUtils.setUserAccount(s);
            return;
        }
        if ((type & TYPE_HUAWEI) == TYPE_HUAWEI) {

        }
    }

    /**
     * 取消账户
     *
     * @param s
     */
    public static void unsetUserAccount(String s) {
        if ((type & TYPE_MIX) == TYPE_MIX) {
            if (canHuaweiPush()) return;
            MiPushUtils.unsetUserAccount(s);
            return;
        }
        if ((type & TYPE_XIAOMI) == TYPE_XIAOMI) {
            MiPushUtils.unsetUserAccount(s);
            return;
        }
        if ((type & TYPE_HUAWEI) == TYPE_HUAWEI) {

        }
    }

    /**
     * 清空账户
     *
     */
    public static void clearUserAccount() {
        if ((type & TYPE_MIX) == TYPE_MIX) {
            if (canHuaweiPush()) return;
            MiPushUtils.clearUserAccount();
            return;
        }
        if ((type & TYPE_XIAOMI) == TYPE_XIAOMI) {
            MiPushUtils.clearUserAccount();
            return;
        }
        if ((type & TYPE_HUAWEI) == TYPE_HUAWEI) {

        }
    }

    /**
     * 判断是否可以使用华为推送
     *
     * @return
     */
    public static boolean canHuaweiPush() {
        int emuiApiLevel = 0;
        try {
            Class cls = Class.forName("android.os.SystemProperties");
            Method method = cls.getDeclaredMethod("get", new Class[]{String.class});
            emuiApiLevel = Integer.parseInt((String) method.invoke(cls, new Object[]{"ro.build.hw_emui_api_level"}));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return emuiApiLevel > 5.0;
    }

    private static Application getApplication() {
        if (app != null) return app;
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return PushUtils.app = (Application) app;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }
}
