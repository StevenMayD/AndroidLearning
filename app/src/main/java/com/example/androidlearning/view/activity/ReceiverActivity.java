package com.example.androidlearning.view.activity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.androidlearning.R;
import com.example.androidlearning.receiver.CustomReceiver;
import com.example.androidlearning.utils.ActionUtils;

public class ReceiverActivity extends AppCompatActivity {
    private static final String TAG = ReceiverActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);
    }

//    动态注册广播 发送广播
    public void sendAction1(View view) {

    }

//    第二步：发送广播 给接收者
//    静态注册广播 发送广播
    public void sendAction2(View view) {
        Log.d(TAG, "ReceiverActivity sendAction2: 已发送广播");

        Intent intent = new Intent();
        intent.setAction(ActionUtils.ACTION_FLAG); // ActionUtils.ACTION_FLAG广播标记 与 注册时保持一致
        intent.setClassName(this, "com.example.androidlearning.receiver.CustomReceiver");
        sendBroadcast(intent); // 发送广播
    }
}