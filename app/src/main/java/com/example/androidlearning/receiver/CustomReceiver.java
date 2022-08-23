package com.example.androidlearning.receiver;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

// 接收者
public class CustomReceiver extends BroadcastReceiver {
    private static final String TAG = CustomReceiver.class.getSimpleName();

    @Override
//   第三步： 接收者 接收广播
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "CustomReceiver onReceive: 已接收到广播");
    }
}
