package com.example.androidlearning.view.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androidlearning.R;

/**
 * Fragment的学习：片段
 * */
public class FragmentActivity extends AppCompatActivity {
    @Override
    // 基本生命周期方法
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

    }
}