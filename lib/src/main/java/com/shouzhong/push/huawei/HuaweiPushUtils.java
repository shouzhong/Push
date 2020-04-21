package com.shouzhong.push.huawei;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.huawei.hms.aaid.HmsInstanceId;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.shouzhong.push.PushConstants.*;
import static com.shouzhong.push.PushUtils.*;

public class HuaweiPushUtils {

    private static ExecutorService executor;

    /**
     * 华为推送初始化
     *
     * @param context 如果context为activity，则用户如果未安装hms或者hms版本过低时会弹框提示用户去下载
     */
    public static void init(final Context context) {
        if (executor == null) executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String token = null;
                try {
                    token = getToken(context);
                } catch (Exception e) {}
                if (TextUtils.isEmpty(token)) return;
                sendData(TYPE_HUAWEI, ACTION_TOKEN, token);
            }
        });
    }

    /**
     * 注意以下方法在子线程运行
     * @param context 如果context为activity，则用户如果未安装hms或者hms版本过低时会弹框提示用户去下载
     *
     * @return
     */
    public static String getToken(Context context) throws Exception {
        ApplicationInfo appInfo = getApplication().getPackageManager().getApplicationInfo(getApplication().getPackageName(), PackageManager.GET_META_DATA);
        String appId = appInfo.metaData.getString("com.huawei.hms.client.appid").replace("appid=", "");
        return HmsInstanceId.getInstance(context).getToken(appId, "HCM");
    }
}
