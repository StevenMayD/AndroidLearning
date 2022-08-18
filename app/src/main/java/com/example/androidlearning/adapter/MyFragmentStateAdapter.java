package com.example.androidlearning.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.List;

// 这里由于ViewPager页上是Fragment，所以这里适配器使用一个自定义的继承自系统FragmentStateAdapter的adapter
public class MyFragmentStateAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {
    // 由外界传递fragment数组，用于adapter布局数据
    List<Fragment> fragmentList = new ArrayList<>();


    // Lifecycle是jetpack组件里的一个控件
    public MyFragmentStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        fragmentList = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
