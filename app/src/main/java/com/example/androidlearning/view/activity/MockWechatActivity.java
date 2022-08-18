package com.example.androidlearning.view.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.androidlearning.R;
import com.example.androidlearning.adapter.MyFragmentStateAdapter;
import com.example.androidlearning.view.fragment.FragmentWeChat;

import java.util.ArrayList;

public class MockWechatActivity extends AppCompatActivity {
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_wechat);

        initPager();
    }

    private void initPager() {
        viewPager = findViewById(R.id.id_viewpager);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(FragmentWeChat.newInstance("微信"));
        fragments.add(FragmentWeChat.newInstance("通信录"));
        fragments.add(FragmentWeChat.newInstance("发现"));
        fragments.add(FragmentWeChat.newInstance("我"));

        MyFragmentStateAdapter fragmentStateAdapter = new MyFragmentStateAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        // 这里由于ViewPager页上是Fragment，所以这里适配器使用一个自定义的继承自系统FragmentStateAdapter的 MyFragmentStateAdapter
        viewPager.setAdapter(fragmentStateAdapter);
    }
}