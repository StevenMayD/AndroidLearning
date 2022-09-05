package com.example.androidlearning.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearning.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKhttpActivity extends AppCompatActivity {
    private static final String TAG = "OKhttpActivity";
    private OkHttpClient okHttpClient; // 请求器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        okHttpClient = new OkHttpClient();
    }
    //   get同步请求
    public void getSync(View view) {
        // 安卓中 网络请求必须启动子线程去处理：.execute()同步请求在主线程中会阻塞线程 崩溃
        new Thread() {
            public void run() {
                // get请求参数以 ?拼接在URL上，参数间&分隔
                Request request = new Request.Builder().url("https://www.httpbin.org/get?a=1&b=1").build();
                // 准备好请求的call对象
                Call call =  okHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    /* 如果服务器响应的是字符串格式，response.body().string() 响应体转换字符串 并打印
                     *  如果服务器响应的是二进制数据，则以二进制的方式读取服务器的响应 .body().byteStream()
                     * */
                    Log.i(TAG, "getSync同步:" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //  get异步请求
    public void getAsync(View view) {
        Request request = new Request.Builder().url("https://www.httpbin.org/get?a=2&b=2").build();
        Call call =  okHttpClient.newCall(request);
        // 异步请求不用自己创建子线程，内部会准备好子线程执行
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // 响应code in 200..299 表示请求是成功的
                if (response.isSuccessful()) {
                    Log.i(TAG, "getAsync异步:" + response.body().string());
                }
            }
        });
    }
    //  post同步请求
    public void postSync(View view) {
        /*  Request默认是.get()可以不写，post请求需要写上.post()
            post请求参数 放在请求体 用form表单的形式提交请求体
            同步请求 需要子线程
        */
        new Thread(){
            public void run() {
                FormBody formBody = new FormBody.Builder().add("a", "3").add("b", "3").build();
                Request request = new Request.Builder().url("https://www.httpbin.org/post").post(formBody).build();
                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    Log.i(TAG, "postSync同步:" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //  post异步请求
    public void postAsync(View view) {
        FormBody formBody = new FormBody.Builder().add("a", "4").add("b", "4").build();
        Request request = new Request.Builder().url("https://www.httpbin.org/post").post(formBody).build();
        Call call = okHttpClient.newCall(request);
        // 异步请求不用自己创建子线程，内部会准备好子线程执行
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // 响应code in 200..299 表示请求是成功的
                if (response.isSuccessful()) {
                    Log.i(TAG, "postAsync异步:" + response.body().string());
                }
            }
        });
    }
}