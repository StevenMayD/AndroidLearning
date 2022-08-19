package com.example.androidlearning.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService生命周期", "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService生命周期", "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("MyService生命周期", "onStart: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService生命周期", "onDestroy: ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MyService生命周期", "onBind: ");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("MyService生命周期", "onUnbind: ");
        return super.onUnbind(intent);
    }
}
