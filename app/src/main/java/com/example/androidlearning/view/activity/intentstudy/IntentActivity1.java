package com.example.androidlearning.view.activity.intentstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearning.R;

public class IntentActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent1);
    }

    /**
     * 跳转：intent传递基本类型数据
     * */
    public void startAction(View view) {
        Intent intent = new Intent(this, IntentActivity2.class);
        // 跳转到IntentActivity2时 携带数据（intent可以传递基本数据类型 不能传递对象）
        intent.putExtra("name", "李连杰"); // 传递String字符串
        intent.putExtra("sex", 'M'); // 传递char字符
        startActivity(intent);
    }

    /**
     * 跳转：intent传递bundle包装数据
     * */
    public void startAction2(View view) {
        Intent intent = new Intent(this, IntentActivity2.class);

        // 封装包裹 bundle对象
        Bundle bundle = new Bundle();
        bundle.putString("name", "吕布");
        bundle.putChar("sex", 'M');

        intent.putExtras(bundle);
        startActivity(intent);
    }
}