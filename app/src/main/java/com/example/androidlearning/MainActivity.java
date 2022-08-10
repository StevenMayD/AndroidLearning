package com.example.androidlearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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
        for (int i = 0; i < 1009; i++) {
            Bean bean = new Bean();
            bean.setName("哈喽呀 " + i);
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
        // 线性布局
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);

        // 网格布局
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3); // spanCount：一行显示格数
//        recyclerView.setLayoutManager(gridLayoutManager);

        // 瀑布流布局
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayout.VERTICAL); // 一行或一列显示3条数据， 垂直方向排布瀑布
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        // adapter辅助适配类
        RecycleViewAdapter recycleAdapter = new RecycleViewAdapter(listData, this);
        recyclerView.setAdapter(recycleAdapter);
        // recycleAdapter对象调用其方法setRecycleItemClickLinstener，此方法的参数是一个接口类型的对象 （而接口对象自然需要实现其内容）
        recycleAdapter.setRecycleItemClickLinstener(new RecycleViewAdapter.RecycleItemClickListener() {
            @Override
            public void itemClick(int position) {
                Log.e("RecycleView点击了", "onRecycleItemClick: 第" + position + "个");
            }
        });
    }
}