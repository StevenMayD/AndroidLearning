package com.example.androidlearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

// 主页面逻辑：MainActivity.java
public class MainActivity extends AppCompatActivity {
    // 数组data用于给ListView加载数据（数组中是Bean类型元素）
    private List<Bean> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // MainActivity主页面逻辑 关联 主页面UI
//        setContentView(R.layout.listview); // ListView界面
        setContentView(R.layout.recycleview); // RecycleView界面

        // 添加表格数据
        for (int i = 0; i < 100; i++) {
            Bean bean = new Bean();
            bean.setName("你好" + i);
            listData.add(bean);
        }

        // 通过id拿到UI界面上的ListView对象
//        ListView listView = findViewById(R.id.lv);
//        // adapter辅助适配类：用于控件的数据填充（数据和self自己 放入adapter里作为参数）
//        listView.setAdapter(new ListViewAdapter(listData, this));
//        // listView的item点击事件
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.e("点击了", "onItemClick: 第" + i + "行" );
//            }
//        });


        // 通过id拿到UI界面上的RecycleView对象
        RecyclerView recyclerView = findViewById(R.id.rv);
        // RecyclerView需要自定义布局（ListView默认就是线性布局）
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        // adapter辅助适配类
        RecycleViewAdapter recycleAdapter = new RecycleViewAdapter(listData, this);
        recyclerView.setAdapter(recycleAdapter);
    }
}