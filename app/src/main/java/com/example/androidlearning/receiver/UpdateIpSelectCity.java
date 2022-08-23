package com.example.androidlearning.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

// 动态注册广播接收者
// (动态)第一步：定义广播接收者（Receiver）
public class UpdateIpSelectCity extends BroadcastReceiver {
    private static final String TAG = UpdateIpSelectCity.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "UpdateIpSelectCity onReceive: 已收到动态注册的广播");
    }
}
