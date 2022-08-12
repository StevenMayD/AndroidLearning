package com.example.androidlearning.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import com.example.androidlearning.R;
import com.example.androidlearning.adapter.ListViewAdapter;
import com.example.androidlearning.adapter.RecycleViewAdapter;
import com.example.androidlearning.bean.Bean;

// 主页面逻辑：MainActivity.java
public class MainActivity extends AppCompatActivity {
    // 数组data用于给ListView加载数据（数组中是Bean类型元素）
    private List<Bean> listData = new ArrayList<>();
    private boolean flag = true;
    private String loadSelector = "tweened_animation"; // 配置主视图加载的 主界面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (loadSelector == "listview") {
            // MainActivity主页面逻辑Java 关联 主页面UI布局layout
            setContentView(R.layout.listview); // ListView界面
            loadListView();
        } else if (loadSelector == "recycleview") {
            setContentView(R.layout.recycleview); // RecycleView界面
            loadRecyclerView();
        } else if (loadSelector == "framebyframe_animation") {
            setContentView(R.layout.framebyframe_animation); // 逐帧动画界面
            loadFrameByFrameAnimationView();
        } else if (loadSelector == "tweened_animation") {
            setContentView(R.layout.tweened_animation); // 补间动画界面
            loadTweenedAnimationView();
        }
    }

//    加载ListView界面
    private void loadListView() {
        // 添加表格数据
        for (int i = 0; i < 1009; i++) {
            Bean bean = new Bean();
            bean.setName("哈喽呀 " + i);
            listData.add(bean);
        }

        // 通过id拿到UI界面上的ListView对象
        ListView listView = findViewById(R.id.lv);
        // adapter辅助适配类：用于控件的数据填充（数据和self自己 放入adapter里作为参数）
        listView.setAdapter(new ListViewAdapter(listData, this));
        // listView的item点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("点击了", "onItemClick: 第" + i + "行" );
            }
        });
    }

    //    加载RecyclerView界面
    private void loadRecyclerView() {
        // 添加表格数据
        for (int i = 0; i < 1009; i++) {
            Bean bean = new Bean();
            bean.setName("你好呀 " + i);
            listData.add(bean);
        }

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

//    加载逐帧动画界面
    private void loadFrameByFrameAnimationView() {
        RelativeLayout relativeLayout = findViewById(R.id.frameByFrameAnimation); // 获取到页面的布局
        AnimationDrawable anim = (AnimationDrawable) relativeLayout.getBackground(); // 获取到布局的背景图 为animation-list 用AnimationDrawable动画类接收
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    anim.start(); // 动画启动
                } else  {
                    anim.stop(); // 动画停止
                }
                flag = !flag;
            }
        });
    }

//    加载补间动画界面
    private void loadTweenedAnimationView() {
        ImageView imageView = findViewById(R.id.tweened_imageView); // 拿到imageview
        imageView.setOnClickListener(new View.OnClickListener() {  // 设置imageview点击事件
            @Override
            public void onClick(View view) {
                /* 通过加载xml动画设置文件来创建一个Animation
                * 注意这里使用MainActivity.this，而不是this。它们本质上是没区别的。但是有时候必须要用MainActivity.this
                * 比如某个控件 setOnClickListener(); 在括号里面new 一个OnClickListener ，然后在onClick方法里面处理的时候必须要用MainActivity.this 而不能用this。
                * */
//                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.tweened_alpha); // 透明度动画
//                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.tweened_rotate); // 旋转动画
//                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.tweened_scale); // 缩放动画
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.tweened_translate); // 平移动画
                imageView.startAnimation(animation); // imageview启动 tweened_alpha设置的动画
            }
        });
    }
}