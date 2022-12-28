package com.example.androidlearning.view.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearning.R;
import com.example.androidlearning.service.MyService;

/**
 * Service的学习：服务
 * */
public class ServiceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

    }

    public void startService(View view) {
//        启动服务（参数指定 服务组件） ：activity与service不搭嘎的关系
        startService(new Intent(this, MyService.class));
    }

    public void stopService(View view) {
//        停止服务（参数指定 服务组件）
        stopService(new Intent(this, MyService.class));
    }

    public void bindService(View view) {
//       绑定服务 ：activity与service是绑定的关系, 参数需要 连接的桥梁connection
        bindService(new Intent(this, MyService.class), connection, Context.BIND_AUTO_CREATE);
    }

    public void unbindService(View view) {
        unbindService(connection);
    }

    // Activity 与 MyService的桥梁
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }; // connection是个变量要以;结尾


    @Override
    // bindService形式： 在activity销毁时 解绑service
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}