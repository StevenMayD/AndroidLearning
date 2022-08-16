package com.example.androidlearning.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.androidlearning.R;
import com.example.androidlearning.view.fragment.Fragment1;
import com.example.androidlearning.view.fragment.Fragment2;

// 这里添加 遵循协议implements View.OnClickListener，button的点击事件则不需要再写回调的形式setOnClickListener(new OnClickListener)
public class FragmentTransactionActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_transaction);

        Button changeBtn = findViewById(R.id.btn_change);
        changeBtn.setOnClickListener(this);

        Button replace = findViewById(R.id.btn_replace);
        replace.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change:
                replaceFragment(new Fragment1());
                break;
            case R.id.btn_replace:
                replaceFragment(new Fragment2());
                break;
        }
    }

    // 动态切换fragment
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager(); // 获取Fragment管理器
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); // 获取Fragment事务类
        fragmentTransaction.replace(R.id.framelayout, fragment); // 完成一次事务：将fragment替换到 framelayout上
        fragmentTransaction.addToBackStack(null); // fragment添加进栈（这样当页面点击或滑动返回时，会挨个出栈，最后一个返回时，才会退出应用到桌面）
        fragmentTransaction.commit(); // 提交事务
    }
}