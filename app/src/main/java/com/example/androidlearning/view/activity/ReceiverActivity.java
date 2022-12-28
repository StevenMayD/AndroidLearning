package com.example.androidlearning.view.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.androidlearning.R;
import com.example.androidlearning.receiver.DynamicReceiver;
import com.example.androidlearning.utils.ActionUtils;


/**
 * Receiver的学习：广播
 * */
public class ReceiverActivity extends AppCompatActivity {
    private static final String TAG = ReceiverActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        //        (动态)第二步：在onCreate注册广播（订阅），动态使用Java代码注册一个广播的接收者
        DynamicReceiver updateIpSelectCity = new DynamicReceiver();
        IntentFilter filter = new IntentFilter(); // 过滤器：过滤是哪个action
        filter.addAction(ActionUtils.ACTION_DYNAMIC_FLAG);
        registerReceiver(updateIpSelectCity, filter); // 注册接收者
    }

    //    （动态）第三步：发送广播
    public void sendAction1(View view) {
        Log.d(TAG, "ReceiverActivity sendAction1: 已发送动态注册的广播");

        Intent intent = new Intent();
        intent.setAction(ActionUtils.ACTION_DYNAMIC_FLAG); // ActionUtils.ACTION_FLAG广播标记 与 注册时保持一致
        /* 如果是给自身应用内广播接收者发送广播 需要添加：intent.setClassName(this,"包名.receiver.UpdateIpSelectCity");
        *  如果是给其他应用的广播接收者发送消息 需要添加：intent.setClassName("包名","包名.receiver.UpdateIpSelectCity");
        * */
        intent.setClassName(this, "com.example.androidlearning.receiver.UpdateIpSelectCity");
        sendBroadcast(intent); // 发送广播
    }

//    第二步：发送广播 给接收者
//    静态注册广播 发送广播
    public void sendAction2(View view) {
        Log.d(TAG, "ReceiverActivity sendAction2: 已发送静态广播");

        Intent intent = new Intent();
        intent.setAction(ActionUtils.ACTION_STATIC_FLAG); // ActionUtils.ACTION_FLAG广播标记 与 注册时保持一致
        /* 如果是给自身应用内广播接收者发送广播 需要添加：intent.setClassName(this,"包名.receiver.CustomReceiver");
         *  如果是给其他应用的广播接收者发送消息 需要添加：intent.setClassName("包名","包名.receiver.CustomReceiver");
         * */
        intent.setClassName(this, "com.example.androidlearning.receiver.StaticReceiver");
        sendBroadcast(intent); // 发送广播
    }
}