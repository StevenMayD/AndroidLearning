package com.example.androidlearning.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

// ViewPageAdapter需要继承自PagerAdapter 并实现方法：getCount和isViewFromObject
public class ViewPageAdapter extends PagerAdapter {
    private List<View> listView; // 子布局 数组

    // 针对新添加的属性listView 添加新初始化构造方法
    public ViewPageAdapter(List<View> listView) {
        this.listView = listView;
    }

    @Override
    // 获得viewpager中有多少个view
    public int getCount() { // viewpage中有多少个view
        return listView.size();
    }

    @Override
    /* 判断instantiateItem(ViewGroup， int)函数所返回来的Key与一个页面视图是否是代表的同一个视图（即它俩是否是对应你的，对应的表示同一个view）
        通常我们直接写 return view == object
    * */
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    
    // 重写两个 override method复写方法
    @NonNull
    @Override
    /* 将给定位置的view添加到ViewGroup容器中，创建并显示出来
        返回一个代表新增页面的Object(key), 通常都是直接返回view本身就可以了，当然你也可以自定义自己的key，但是key和每个view要一一对应
    * */
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(listView.get(position), 0);
        return listView.get(position);
    }

    @Override
    // 当前不用的view 进行销毁
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(listView.get(position));
    }
}
