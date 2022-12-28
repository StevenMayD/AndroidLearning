package com.example.androidlearning.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.androidlearning.R;
import com.example.androidlearning.adapter.MyFragmentStateAdapter;
import com.example.androidlearning.view.fragment.FragmentWeChat;

import java.util.ArrayList;

/**
 * 模拟微信主页面：ViewPager2的运用
 * */

// tab需要监听点击事件
public class MockWechatActivity extends AppCompatActivity implements View.OnClickListener {
    ViewPager2 viewPager;
    private LinearLayout llWeChat, llAddressBook, llFind, llMine;
    private ImageView ivWeChat, ivAddressBook, ivFind, ivMine;
    private ImageView ivCurrent; // 记录当前imageview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_wechat);

        initPager();
        initTabView();
    }

    private void initTabView() {
        llWeChat = findViewById(R.id.id_tab_wechat);
        llWeChat.setOnClickListener(this); // 监听页面点击事件
        llAddressBook = findViewById(R.id.id_tab_addressBook);
        llAddressBook.setOnClickListener(this);
        llFind = findViewById(R.id.id_tab_find);
        llFind.setOnClickListener(this);
        llMine = findViewById(R.id.id_tab_mine);
        llMine.setOnClickListener(this);

        ivWeChat = findViewById(R.id.tab_imageView_wechat);
        ivAddressBook = findViewById(R.id.tab_imageView_addressBook);
        ivFind = findViewById(R.id.tab_imageView_find);
        ivMine = findViewById(R.id.tab_imageView_mine);

        ivWeChat.setSelected(true); // 默认初始选中微信
        ivCurrent = ivWeChat;
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

        // viewPager自带滚动的监听方法（通过监听方法实现 viewpager页面滑动 联动控制 tab切换）
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
//            屏幕滚动过程中不断被调用
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
//            点选第position页面 并且滑动成功停止后会调用
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTab(position);
            }

            @Override
            // 监听点按的操作过程：手指操作屏幕的时候发生变化。有三个值：0（END）,1(PRESS) , 2(UP) 。
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    // 切换底部tab按钮：联动其实就是 改变tab按钮的选中状态
    private void changeTab(int position) {
        ivCurrent.setSelected(false);
        switch (position) {
            case R.id.id_tab_wechat:
                // tab点选 切换viewpage页面
                viewPager.setCurrentItem(0);
            case 0:
                // viewpager页面滑动 切换tab选中状态
                ivWeChat.setSelected(true);
                ivCurrent = ivWeChat;
                break;
            case R.id.id_tab_addressBook:
                viewPager.setCurrentItem(1);
            case 1:
                ivAddressBook.setSelected(true);
                ivCurrent = ivAddressBook;
                break;
            case R.id.id_tab_find:
                viewPager.setCurrentItem(2);
            case 2:
                ivFind.setSelected(true);
                ivCurrent = ivFind;
                break;
            case R.id.id_tab_mine:
                viewPager.setCurrentItem(3);
            case 3:
                ivMine.setSelected(true);
                ivCurrent = ivMine;
                break;
        }
    }

    @Override
    public void onClick(View view) {
        changeTab(view.getId());
    }
}