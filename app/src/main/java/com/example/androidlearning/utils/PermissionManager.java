package com.example.androidlearning.utils;

import android.app.Activity;

import com.blankj.utilcode.util.LogUtils; // 'com.blankj:utilcodex:1.28.4'
import com.yanzhenjie.permission.AndPermission; // 'com.yanzhenjie:permission:2.0.3'
import com.yanzhenjie.permission.runtime.PermissionDef;

public class PermissionManager {
    public static void checkPermissions(Activity activity, PermissionStateListener listener, @PermissionDef String... permissions) {
        boolean has = AndPermission.hasPermissions(activity, permissions);
        if (has) {
            listener.onGranted();
        } else {
            AndPermission.with(activity)
                    .runtime()
                    .permission(permissions)
                    .onGranted(pms -> {
                        String name = Thread.currentThread().getName(); // 获取当前线程
                        LogUtils.e("thread name : " + name); // 自带样式的打印工具
                        /*
                        2022-10-17 16:07:11.471 3921-3921/com.example.androidlearning E/PermissionManager:
                        ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
                        │ main, com.example.androidlearning.utils.PermissionManager.lambda$checkPermissions$0(PermissionManager.java:21)
                        ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
                        │ thread name : main
                        └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
                        * */
                        listener.onGranted();
                    })
                    .onDenied(pms -> {
                        listener.onDenied();
                    })
                    .start();
        }
    }
}
