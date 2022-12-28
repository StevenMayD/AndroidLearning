package com.example.androidlearning.view.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearning.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * RX思维的学习：把握住任务的起点和终点，中间过程环节可以随意增减，每一个过程，根据上一层的响应，来影响下一层的变化
 * */
public class RxActivity extends AppCompatActivity {
    private final String TAG = RxActivity.class.getSimpleName();

    // 网络图片的链接地址
    private final static String PATH = "https://mbdp01.bdstatic.com/static/landing-pc/img/logo_top.79fdb8c2.png";

    // 弹出加载框
    private ProgressDialog progressDialog;

    // 图片显示区
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);

        image = findViewById(R.id.image);
    }

//    图片显示加载功能
    public void showImageAction(View view) {
        /* 如果使用传统方式 实现此功能，每位开发者思想不一样
        * 各有各的方法，afn，asi，线程池
        * */

        /* TODO：RX思维
        *   起点 和 终点
        *   RX思维 把所有函数都称为操作符，操作使得从起点流向终点 执行步骤顺序 一，二，三，四，五
        * */
        // TODO: 第二步 将String传递下去
        // 起点("起点"将string类型的PATH流向"过程")
        Observable.just(PATH)
                /* 过程(需求001)：图片下载需求 (获取到起点的PATH,得到Bitmap，并将Bitmap流向"终点")
                * 异步请求服务器操作：需要在子线程中进行
                * */
                // TODO: 第三步 将String变成Bitmap（这里Function的参数，第一个为上一层String类型，第二个参数为Bitmap下一层使用的类型）
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String path) throws Exception {
                        // TODO: 请求服务器
                        try {
                            Thread.sleep(2000); // 睡眠2秒

                            URL url = new URL(path);
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.setConnectTimeout(5000); // 设置请求连接时长 5秒
                            int responseCode = httpURLConnection.getResponseCode(); // 开始请求
                            if (responseCode == HttpURLConnection.HTTP_OK) {
                                // 请求成功 则解析数据流
                                InputStream inputStream = httpURLConnection.getInputStream();
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                return bitmap; // 将Bitmap流向"终点"
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                // 添加过程(需求002)：图片加水印
                .map(new Function<Bitmap, Bitmap>() {
                    @Override
                    public Bitmap apply(Bitmap bitmap) throws Exception {
                        Paint paint = new Paint();
                        paint.setColor(Color.RED); // 红色画笔
                        paint.setTextSize(17);
                        Bitmap shuiyinBitmap = drawTextToBitmap(bitmap, "哈哈,大家好", paint, 15,15);
                        return shuiyinBitmap;
                    }
                })
                // 增加过程（需求003）：日志记录
                .map(new Function<Bitmap, Bitmap>() {
                    @Override
                    public Bitmap apply(Bitmap bitmap) throws Exception {
                        Log.d(TAG, "什么时候下载了图片 apply: " + System.currentTimeMillis());
                        return bitmap;
                    }
                })
                // 给上面过程的操作 分配异步子线程进行
                .subscribeOn(Schedulers.io())
                // 给下面终点的操作 分配安卓主线程进行
                .observeOn(AndroidSchedulers.mainThread())
                // TODO：导火索点燃 开始执行
                // 关联：观察者设计模式（关联 起点和终点 == 订阅）
                .subscribe(
                        // 终点（数据类型与过程中保持一致为Bitmap）
                        new Observer<Bitmap>() {
                            // TODO: 第一步 订阅关联
                            // 订阅成功（关联起点和终点成功）
                            @Override
                            public void onSubscribe(Disposable d) {
                                // 加载框
                                progressDialog = new ProgressDialog(RxActivity.this);
                                progressDialog.setTitle("RXJava Derry run 正在加载中");
                                progressDialog.show();
                            }
                            // TODO: 第四步 处理结果(显示图片)
                            // 接收上一层给我的响应
                            @Override
                            public void onNext(Bitmap bitmap) {
                                // 获取到上一层的Bitmap，进行操作 （切换主线程 更新UI）
                                image.setImageBitmap(bitmap);
                            }
                            // 链条思维发生了异常
                            @Override
                            public void onError(Throwable e) {

                            }
                            // TODO: 第五步 整个链条思维全部结束
                            // 整个链条全部结束
                            @Override
                            public void onComplete() {
                                if (progressDialog != null) {
                                    progressDialog.hide();
                                }
                            }
                        }
                );
    }
    // 图片加水印 (原始图片bitmap，添加文字text，画笔paint，位置padding )
    private final Bitmap drawTextToBitmap(Bitmap bitmap, String text, Paint paint, int paddingLeft, int paddingTop) {
        Bitmap.Config bitmapConfig = bitmap.getConfig();

        paint.setDither(true); // 获取更清晰的图像采样
        paint.setFilterBitmap(true); // 过滤
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, paddingLeft, paddingTop, paint);
        return bitmap;
    }

    // 常用操作符
    public void action(View view) {
        String[] strings = {"aa", "bb", "cc"};
        
        // 常规循环遍历 打印数组
        for (String string : strings) {
            Log.d(TAG, "常规循环遍历 打印数组: " + string);
        }
        
        // Rx思维 打印数组: 围绕起、终点
        Observable.fromArray(strings)
                // 订阅：起点和终点
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "Rx思维 打印数组: " + s);
                    }
                });
    }
}