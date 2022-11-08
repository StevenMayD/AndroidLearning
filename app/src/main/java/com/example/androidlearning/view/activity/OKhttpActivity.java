package com.example.androidlearning.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearning.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
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
    private OkHttpClient okHttpClientWithCookie; // 请求器(携带Cookie)
    private Map<String, List<Cookie>> cookies = new HashMap<>(); // 声明Map类型的属性并初始化 用于内存中存储Cookie

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        okHttpClient = new OkHttpClient(); //初始化属性
        okHttpClientWithCookie = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    // list就是服务返回的Cookie集合
                    public void saveFromResponse(@NonNull HttpUrl httpUrl, @NonNull List<Cookie> list) {
                        // 以host为key，将Cookie保存在内存里，别的请求需要用时，取出来用即可 (比如 收藏、评论的功能，要求是登录的状态，可以将登录接口返回的Cookie，使用在收藏接口的请求时)
                        cookies.put(httpUrl.host(), list);
                    }

                    @NonNull
                    @Override
                    public List<Cookie> loadForRequest(@NonNull HttpUrl httpUrl) {
                        // 如果是请求 "玩万卓"的接口，就使用保存好的cookie 否则返回空数组
                        List<Cookie> cookieList = cookies.get(httpUrl.host());
                        return cookieList==null ? new ArrayList<>() : cookieList;
                    }
                }).build();
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

    // 添加构建者配置：拦截器和缓存
    public void request_addInterceptorAndCache(View view) {
        new Thread() {
            public void run() {
                // OkHttpClient添加 构建者配置
                OkHttpClient httpClient = new OkHttpClient.Builder()
                        // 配置缓存（缓存文件路径，大小为1M, 满了后，会覆盖旧的数据）
                        .cache(new Cache(new File("/path/cache"), 1024*1024))
                        // 前置拦截器 addInterceptor
                        .addInterceptor(new Interceptor() {
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

    // 添加Cookie的请求
    public void request_addCookie(View view) {
        new Thread() {
            public void run() {
                /* 登录API
                * https://www.wanandroid.com/user/login 方法：POST 参数： username，password
                  登录后会在cookie中返回账号密码，只要在客户端做cookie持久化存储即可自动登录验证
                * */
                FormBody formBody = new FormBody.Builder()
                        .add("username", "StevenMayD")
                        .add("password", "DongBao-3037")
                        .build();
                Request request = new Request.Builder().url("https://www.wanandroid.com/user/login").post(formBody).build();

                Call call =  okHttpClientWithCookie.newCall(request);
                try {
                    Response response = call.execute();
                    Log.d(TAG, "添加Cookie的请求:" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // 使用Cookie的请求
    public void request_useCookie(View view) {
        new Thread() {
            public void run() {
                /* 获取收藏列表API
                https://www.wanandroid.com/lg/collect/list/0/json
                方法：GET、参数： 页码：拼接在链接中，从0开始。
                注：该接口支持传入 page_size 控制分页数量，取值为[1-40]，不传则使用默认值，一旦传入了 page_size，后续该接口分页都需要带上，否则会造成分页读取错误
                */
                Request request = new Request.Builder().url("https://www.wanandroid.com/lg/collect/list/0/json").build();
                Call call =  okHttpClientWithCookie.newCall(request);
                try {
                    Response response = call.execute();
                    Log.d(TAG, "使用Cookie的请求:" + response.body().string());
                    // 没有使用Cookie的话， 响应报错:{"errorCode":-1001,"errorMsg":"请先登录！"}
                    // 使用了带Cookie的okHttpClientWithCookie，则获取到了收藏列表
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}