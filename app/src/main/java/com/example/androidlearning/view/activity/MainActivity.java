package com.example.androidlearning.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidlearning.R;
import com.example.androidlearning.adapter.ListViewAdapter;
import com.example.androidlearning.adapter.RecycleViewAdapter;
import com.example.androidlearning.adapter.ViewPageAdapter;
import com.example.androidlearning.adapter.ViewPager2Adapter;
import com.example.androidlearning.bean.Bean;

// 主页面逻辑：MainActivity.java
public class MainActivity extends AppCompatActivity {
    // 数组data用于给ListView加载数据（数组中是Bean类型元素）
    private List<Bean> listData = new ArrayList<>();
    private boolean flag = true;
    private String loadSelector = "glide"; // 配置主视图加载的 主界面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Activity生命周期", "onCreate:");

        if (loadSelector == "listview") {
            // MainActivity主页面逻辑Java 关联 主页面UI布局layout
            setContentView(R.layout.activity_listview); // ListView界面
            loadListView();
        } else if (loadSelector == "recycleview") {
            setContentView(R.layout.activity_recycleview); // RecycleView界面
            loadRecyclerView();
        } else if (loadSelector == "framebyframe_animation") {
            setContentView(R.layout.activity_framebyframe_animation); // 逐帧动画界面
            loadFrameByFrameAnimationView();
        } else if (loadSelector == "tweened_animation") {
            setContentView(R.layout.activity_tweened_animation); // 补间动画界面
            loadTweenedAnimationView();
        } else if (loadSelector == "property_animation") {
            setContentView(R.layout.activity_tweened_animation);
            loadPropertyAnimator(); // 属性动画
        } else if (loadSelector == "JavaLayout") {
            loadJavaLayout(); // Java类文件布局界面layout
        } else if (loadSelector == "viewpage") {
            setContentView(R.layout.activity_viewpage);
            loadViewPage(); // 滚动界面viewpage
        } else if (loadSelector == "glide") {
            setContentView(R.layout.activity_glide);
            loadGlide(); // Glide基本使用
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

                // activity组件间跳转：从当前MainActivity 跳转至 MockWechatActivity
                startActivity(new Intent(MainActivity.this, ViewPager2Activity.class));
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

    // 属性动画
    private void loadPropertyAnimator(){
        // ValueAnimator 值的变化
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f,1f); // 让 值0到1的 值属性 进行变化
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // 监听值属性变化
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) { // 监听到值更新了后
                float value = (float) valueAnimator.getAnimatedValue();
                Log.e("你好", "onAnimationUpdate: " + value);
            }
        });
        valueAnimator.start(); // 启动属性动画

        // ObjectAnimator 对象(控件)的指定属性的变化（指定属性为Object对象所拥有的属性，包括透明度，旋转，位移等）
        ImageView imageView = findViewById(R.id.tweened_imageView);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 0f, 1f); // 让 目标对象imageView 的 透明度属性 从透明到不透明 进行变化
        objectAnimator.setDuration(4000);

        // ObjectAnimator监听器 （必须监听4个方法）
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
//            动画开始时候调用
            public void onAnimationStart(Animator animator) {
                Log.e("对象属性动画", "onAnimationStart: 开始");
            }

            @Override
//            动画结束时候调用
            public void onAnimationEnd(Animator animator) {
                Log.e("对象属性动画", "onAnimationEnd: 停止");
            }

            @Override
//            动画取消时候调用
            public void onAnimationCancel(Animator animator) {
                Log.e("对象属性动画", "onAnimationCancel: 取消");
            }

            @Override
//            动画重复执行的时候调用
            public void onAnimationRepeat(Animator animator) {
                Log.e("对象属性动画", "onAnimationRepeat: 重复执行");
            }
        });

//        监听适配器可以选择监听指定的方法
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                Log.e("对象属性动画", "onAnimationStart: 开始2");
            }
        });
        objectAnimator.start(); // 启动动画
    }

    // 使用java编码布局界面
    private void loadJavaLayout() {
        // 主视图布局
        LinearLayout linearLayout = new LinearLayout(this);
        // 设置布局参数LayoutParams: 当使用java布局时，使用LayoutParams来编码布局的尺寸参数集
        LinearLayout.LayoutParams  mainLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT); // 沾满全屏
        linearLayout.setLayoutParams(mainLayoutParams);

        // 子视图控件
        TextView textView = new TextView(this);
        textView.setText("我是文本");
        textView.setBackgroundColor(0xffff0000);
        // 这里的单位默认是像素px
        LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(300, 300);
        textView.setLayoutParams(textLayoutParams);
        // 父视图添加子视图
        linearLayout.addView(textView);

        // 主页面添加 linearLayout布局
        setContentView(linearLayout);
    }

    // 加载viewpage界面
    private void loadViewPage() {
        LayoutInflater layoutInflater = getLayoutInflater().from(this); // 拿到当前布局的渲染器
        View view1 = layoutInflater.inflate(R.layout.layout_viewpage1, null); // 当前布局进行渲染子布局界面，并拿到子布局界面
        View view2 = layoutInflater.inflate(R.layout.layout_viewpage2, null);
        View view3 = layoutInflater.inflate(R.layout.layout_viewpage3, null);

        List<View> viewList = new ArrayList<>();
        viewList.add(view1); // 子界面加入集合
        viewList.add(view2);
        viewList.add(view3);

        ViewPager viewPager = findViewById(R.id.viewpage);
        // viewpage的布局填充需要用到适配器adapter
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(viewList);
        viewPager.setAdapter(viewPageAdapter);
    }

    // Glide基本使用
    private void loadGlide() {
        /* 请求配置：可用于配置占位图
        *  placeholder：正在请求图片的时候展示的图片
        *  error：如果请求失败的时候展示的图片(如果没有设置，还是展示placeholder的占位符)
        *  fallback：如果请求的url/model为null的时候展示的图片(如果没有设置，还是展示placeholder的占位符)
        * */
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.wechat_normal)
                .error(R.drawable.address_book_normal)
                .fallback(R.drawable.find_normal)
                .override(100, 100);

        ImageView imageView = findViewById(R.id.imageView);
        /* with传递一个上下文this、一个view、或者fragment都行，with传递的对象，图片Image的内存生命周期，就会跟这个对象的生命周期绑定在一起
        *  load的内容 可以是工程里的R.资源、或者文件对象、或者是https网络资源(并配置网络权限<uses-permission android:name="android.permission.INTERNET" />)
        *  这里配置了RequestOptions占位图，没看到效果？ 因为glide将加载过的图片，缓存在本地了
        * */
        Glide.with(this)
                .load("https://cdn.wwads.cn/creatives/0eLUA9e7Yr1FpZfqI1h2d3NBbxdbzBizF68j3AYQ_dongshjuaiwen.png")
                .apply(requestOptions)
                .into(imageView);
    }


    // 补全activity所有生命周期方法

    @Override
    // 重新启动
    protected void onRestart() {
        super.onRestart();
        Log.d("Activity生命周期", "onRestart: ");
    }

    @Override
//    画面启动渲染
    protected void onStart() {
        super.onStart();
        Log.d("Activity生命周期", "onStart: ");
    }

    @Override
    // 画面展示渲染完成，画面可见
    protected void onResume() {
        super.onResume();
        Log.d("Activity生命周期", "onResume: ");
    }

    @Override
    // 画面被暂停 画面不可见
    protected void onPause() {
        super.onPause();
        Log.d("Activity生命周期", "onPause: ");
    }

    @Override
    // 画面被停止 画面相关工作全停止
    protected void onStop() {
        super.onStop();
        Log.d("Activity生命周期", "onStop: ");
    }

    @Override
    // 画面被销毁
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Activity生命周期", "onDestroy: ");
    }
}