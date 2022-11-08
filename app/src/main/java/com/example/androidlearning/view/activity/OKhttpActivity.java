package com.example.androidlearning.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearning.R;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
                    Log.d(TAG, "getSync同步:" + response.body().string());
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
                    Log.d(TAG, "getAsync异步:" + response.body().string());
                }
            }
        });
    }
    //  post同步请求
    public void postSync_form_urlencoded(View view) {
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
                    Log.d(TAG, "postSync同步:" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //  post异步请求：常规的键值对参数 请求
    public void postAsync_form_urlencoded(View view) {
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
                    Log.d(TAG, "postAsync异步:" + response.body().string());
                }
            }
        });
    }

    // post异步请求：文件上传 请求
    public void postAsync_form_data(View view) throws IOException {
        // 获取待上传文件
        File file1 = new File("/storage/emulated/0/Pictures/1666838557454-185e93498-afdf-4325-b8fb-3ac8e5002dcf.jpeg");
        File file2 = new File("/storage/emulated/0/Pictures/img_16672116809444dbaf6e2-815f-4e19-8fed-47e5ee78a8bd.jpeg");
        if (!file2.exists() || !file2.exists()) {
            Log.d(TAG, "文件不存在");
            return;
        }
        // jpg图片上传使用image/jpeg，常见的媒体格式类型：https://www.runoob.com/http/http-content-type.html
        // name值 由接口文档而定
        MultipartBody multipartBody = new MultipartBody.Builder()
                .addFormDataPart("file1", file1.getName(), RequestBody.create(file1, MediaType.parse("image/jpeg")))
                .addFormDataPart("file2", file2.getName(), RequestBody.create(file2, MediaType.parse("image/jpeg")))
                .build();
        Request request = new Request.Builder().url("https://www.httpbin.org/post").post(multipartBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "postAsync_form_data文件上传 失败：" + e.toString());
                // postAsync_form_data文件上传 失败：java.io.FileNotFoundException: /storage/emulated/0/Pictures/img_16672116809444dbaf6e2-815f-4e19-8fed-47e5ee78a8bd.jpeg: open failed: EACCES (Permission denied)
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // 响应code in 200..299 表示请求是成功的
                if (response.isSuccessful()) {
                    Log.d(TAG, "postAsync_form_data文件上传 成功：" + response.body().string());
                }
            }
        });
    }

    // post异步请求：json数据提交
    public void postAsync_json(View view) throws IOException {
        // 同步也行
//        new Thread() {
//            public void run() {
//                RequestBody requestBody = RequestBody.create("{\"a\":1, \"b\": 2}", MediaType.parse("application/json"));
//                Request request = new Request.Builder().url("https://www.httpbin.org/post").post(requestBody).build();
//                Call call = okHttpClient.newCall(request);
//                try {
//                    Response response = call.execute();
//                    Log.d(TAG, "postSync_json数据提交：" + response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();

        // 异步
        RequestBody requestBody = RequestBody.create("{\"a\":1, \"b\": 2}", MediaType.parse("application/json"));
        Request request = new Request.Builder().url("https://www.httpbin.org/post").post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                // 响应code in 200..299 表示请求是成功的
                if (response.isSuccessful()) {
                    Log.d(TAG, "postSync_json数据提交:" + response.body().string());
                }
            }
        });
    }

    // 添加拦截器进行请求
    public void request_addInterceptor(View view) {
        new Thread() {
            public void run() {
                // OkHttpClient添加 前置拦截器 addInterceptor
                OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    // addInterceptor 会在请求发给服务器之前 拦截请到求
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        // 获取请求 并添加自定义配置
                        Request request = chain.request().newBuilder()
                                .addHeader("os", "android") // 统一添加header
                                .addHeader("version", "1.0")
                                .build();
                        Log.d(TAG, "1.执行-addInterceptor:" + request.header("os"));
                        Response response = chain.proceed(request);
                        Log.d(TAG, "4.执行-addInterceptor:" + request.header("os"));
                        return response;
                    }
                    // 添加后置拦截器 addNetworkInterceptor
                }).addNetworkInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Log.d(TAG, "2.执行-addNetworkInterceptor:" + chain.request().header("os"));
                        Response response = chain.proceed(chain.request());
                        Log.d(TAG, "3.执行-addNetworkInterceptor:" + chain.request().header("os"));
                        return response;
                    }
                }).build();

                Request request = new Request.Builder().url("https://www.httpbin.org/get?a=1&b=1").build();
                Call call =  httpClient.newCall(request);
                try {
                    Response response = call.execute();
                    Log.d(TAG, "添加拦截器进行请求:" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}