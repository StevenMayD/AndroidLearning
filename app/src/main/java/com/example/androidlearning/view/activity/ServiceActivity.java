package com.example.androidlearning.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.androidlearning.R;
import com.example.androidlearning.service.MyService;

public class ServiceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

    }

    public void startService(View view) {
//        启动服务（参数指定 服务组件）
        startService(new Intent(this, MyService.class));
    }

    public void stopService(View view) {
//        停止服务（参数指定 服务组件）
        stopService(new Intent(this, MyService.class));
    }
}