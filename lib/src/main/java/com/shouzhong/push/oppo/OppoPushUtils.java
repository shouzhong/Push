package com.shouzhong.push.oppo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.heytap.mcssdk.PushManager;
import com.heytap.mcssdk.callback.PushAdapter;

import static com.shouzhong.push.PushConstants.*;
import static com.shouzhong.push.PushUtils.*;

public class OppoPushUtils {

    public void init(Context context) {
        try {
            Context cxt = context.getApplicationContext();
            ApplicationInfo appInfo = cxt.getPackageManager().getApplicationInfo(cxt.getPackageName(), PackageManager.GET_META_DATA);
            String appKey = appInfo.metaData.getString("OPPOPUSH_APP_KEY");
            String appSecret = appInfo.metaData.getString("OPPOPUSH_APP_SECRET");
            PushManager.getInstance().register(context.getApplicationContext(), appKey, appSecret, new PushAdapter() {
                @Override
                public void onRegister(int i, String s) {
                    if (i == 0 && !TextUtils.isEmpty(s)) sendData(TYPE_OPPO, ACTION_TOKEN, s);
                }
            });
        } catch (Exception e) {}
    }

}
