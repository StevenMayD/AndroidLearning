package com.example.androidlearning.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

// 接收者
public class StaticReceiver extends BroadcastReceiver {
    private static final String TAG = StaticReceiver.class.getSimpleName();

    @Override
//   第三步： 接收者 接收广播
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "StaticReceiver onReceive: 已接收到静态广播");
    }
}
