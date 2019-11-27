package com.shouzhong.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public abstract class BasePushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int type = intent.getIntExtra("type", 0);
        String action = intent.getStringExtra("action");
        String data = intent.getStringExtra("data");
        onReceive(context, type, action, data);
    }

    public abstract void onReceive(Context context, int type, String action, String data);
}
