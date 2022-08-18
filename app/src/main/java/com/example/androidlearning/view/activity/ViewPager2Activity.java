package com.example.androidlearning.view.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.androidlearning.R;
import com.example.androidlearning.adapter.ViewPager2Adapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPager2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager2);

        List titleList = new ArrayList();
        titleList.add("title1");
        titleList.add("title2");
        titleList.add("title3");
        titleList.add("title4");
        titleList.add("title5");

        List colorList = new ArrayList();
        colorList.add(R.color.white);
        colorList.add(R.color.purple_200);
        colorList.add(R.color.black);
        colorList.add(R.color.teal_200);
        colorList.add(R.color.purple_700);

        ViewPager2 viewPager = findViewById(R.id.viewPager2);
        // viewpager本身是个容器，需要适配数据才能呈现，ViewPager2Adapter适配数据类
        ViewPager2Adapter viewPagerAdapter = new ViewPager2Adapter(titleList, colorList);
        viewPager.setAdapter(viewPagerAdapter);
    }
}