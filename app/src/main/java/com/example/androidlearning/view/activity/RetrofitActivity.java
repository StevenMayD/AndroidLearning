package com.example.androidlearning.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearning.R;
import com.example.androidlearning.bean.BaseResponse;
import com.example.androidlearning.service.RetrofitHttpBinService;
import com.example.androidlearning.service.WanAndroidService;
import com.google.gson.Gson;

import org.reactivestreams.Publisher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {
    private static final String TAG = "RetrofitActivity";
    private Retrofit retrofit; // Retrofit请求对象
    private RetrofitHttpBinService httpBinService;  // Retrofit请求服务

    private Retrofit wanAndroidRetrofit;
    private WanAndroidService wanAndroidService;

    private Retrofit wanAndroidRetrofitConverter; // Retrofit请求对象（配置转换器）
    private WanAndroidService wanAndroidServiceConverter; // Retrofit请求服务（直接返回转换对象BaseResponse）

    private Retrofit retrofitAdapter; // retrofit适配器的使用
    private WanAndroidService retrofitServiceAdapter;

    private Map<String, List<Cookie>> cookies = new HashMap<>(); // 声明Map类型的属性并初始化 用于内存中存储Cookie

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        // Retrofit注意指定baseUrl
        retrofit = new Retrofit.Builder().baseUrl("https://www.httpbin.org/").build();
        httpBinService = retrofit.create(RetrofitHttpBinService.class);
        // 手动转换 响应数据为对象
        wanAndroidRetrofit = new Retrofit.Builder().baseUrl("https://www.wanandroid.com/").build();
        wanAndroidService = wanAndroidRetrofit.create(WanAndroidService.class);
        // 自动转换 响应数据为对象
        wanAndroidRetrofitConverter = new Retrofit.Builder().baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create()) // 添加转换器：因为依赖是 retrofit2:converter-gson，所以使用GsonConverterFactory转换器
                .build(); // Retrofit请求对象 带转换器
        wanAndroidServiceConverter = wanAndroidRetrofitConverter.create(WanAndroidService.class);

        // retrofit适配器的使用
        retrofitAdapter = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .callFactory((okhttp3.Call.Factory) new OkHttpClient.Builder().cookieJar(new CookieJar() { // 添加自动保存cookie的OkHttpClient (由于 玩安卓示例中的嵌套请求 的关联是 通过cookie进行的)
                    @Override
                    public void saveFromResponse(@NonNull HttpUrl httpUrl, @NonNull List<Cookie> list) {
                        // 以host为key，将Cookie保存在内存里，别的请求需要用时，取出来用即可 (比如 收藏、评论的功能，要求是登录的状态，可以将登录接口返回的Cookie，使用在收藏接口的请求时)
                        cookies.put(httpUrl.host(), list);
                    }

                    @NonNull
                    @Override
                    public List<Cookie> loadForRequest(@NonNull HttpUrl httpUrl) {
                        // 如果是请求 "玩安卓"的接口，就使用保存好的cookie 否则返回空数组
                        List<Cookie> cookieList = cookies.get(httpUrl.host());
                        return cookieList==null ? new ArrayList<>() : cookieList;
                    }
                }).build())
                .addConverterFactory(GsonConverterFactory.create()) // 添加转换器
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // 添加适配器
                .build();
        retrofitServiceAdapter = retrofitAdapter.create(WanAndroidService.class);
    }

    // @Post注解请求
    public void postAsync_form_urlencoded(View view) {
        // Retrofit不用再拼接form参数了，利用注解可以简洁地组织好 参数格式
        Call<ResponseBody> call = httpBinService.postRequest("StevenMayD", "DongBao-3037");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d(TAG, "retrofit postAsync_form_urlencoded @Post注解请求:" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // @Body注解请求
    public void postAsync_form_body(View view) {
        new Thread() {
            public void run() {
                // 注意同步请求，要创建子线程 里执行（否则运行崩溃）
                FormBody fromBody = new FormBody.Builder().add("a", "3").add("b", "5").build();
                try {
                    Response<ResponseBody> response = httpBinService.postBodyRequest(fromBody).execute();
                    Log.d(TAG, "retrofit postAsync_form_data @Body注解请求:" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // @Path注解请求
    public void postAsync_Path(View view) {
        new Thread() {
            public void run() {
                try {
                    // 相当于请求地址为：https://www.httpbin.org/post
                    Response<ResponseBody> response = httpBinService.postInPathRequest("post", "android","StevenMayD", "DongBao-3037").execute();
                    Log.d(TAG, "retrofit postAsync_Path注解请求:" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // @Headers注解请求
    public void postAsync_Headers(View view) {
        new Thread() {
            public void run() {
                try {
                    // 相当于请求地址为：https://www.httpbin.org/post
                    Response<ResponseBody> response = httpBinService.postHeadersRequest().execute();
                    Log.d(TAG, "retrofit postAsync_Headers注解请求:" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // @Url注解请求
    public void postAsync_Url(View view) {
        new Thread() {
            public void run() {
                try {
                    // 相当于请求地址为：https://www.httpbin.org/post
                    Response<ResponseBody> response = httpBinService.postUrlRequest("https://www.httpbin.org/post").execute();
                    Log.d(TAG, "retrofit postAsync_Url注解请求:" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //  retrofit 手动转换器
    public void retrofit_converter(View view) {
        new Thread() {
            public void run() {
                try {
                    Call<ResponseBody> call = wanAndroidService.login("StevenMayD", "DongBao-3037");
                    Response<ResponseBody> response = call.execute();
                    String result = response.body().string();
                    // 手动进行数据转换：使用Gson进行反序列化（将json字符串转换为对象，并指定 BaseResponse类 接管）
                    BaseResponse baseResponse = new Gson().fromJson(result, BaseResponse.class);
                    Log.d(TAG, "retrofit_手动转换-Gson:" + baseResponse); // 要想打印对象 需要BaseResponse类添加toString()方法\
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //  retrofit 自动转换器
    public void retrofit_converter_gson(View view) {
        new Thread() {
            public void run() {
                try {
                    // 可以直接使用BaseResponse来接收 而不是ResponseBody
                    Call<BaseResponse> call = wanAndroidServiceConverter.loginWithAutoConverter("StevenMayD", "DongBao-3037");
                    // 自动进行数据转换：使用retrofit2:converter-gson
                    Response<BaseResponse> response = call.execute();
                    BaseResponse baseResponse = response.body();
                    Log.d(TAG, "retrofit_自动转换-retrofit2:converter-gson:" + baseResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // retrofit 适配器：完成优雅的嵌套请求
    public void retrofit_adapter(View view) {
        // 先登陆请求
        retrofitServiceAdapter.login2("StevenMayD", "DongBao-3037")
                .flatMap(new Function<BaseResponse, Publisher<ResponseBody>>() { // flatMap：用于根据login2请求的结果，生成一个新的Publisher（继续进行请求 获取收藏列表接口getArticle）
                    @Override
                    public Publisher<ResponseBody> apply(BaseResponse baseResponse) throws Throwable {
                        // 再请求
                        return retrofitServiceAdapter.getArticle(0);
                    }
                })
                .observeOn(Schedulers.io()) // observeOn：切换到子线程 进行请求
                .subscribeOn(AndroidSchedulers.mainThread()) // subscribeOn：观察回调 则切换到 安卓主线程中
                .subscribe(new Consumer<ResponseBody>() { // subscribe：观察回调 在主线程中 执行 UI等操作
                    @Override
                    // 嵌套请求 最终拿到的是getArticle接口的返回类型ResponseBody
                    public void accept(ResponseBody responseBody) throws Throwable {
                        Log.d(TAG, "retrofit_adapter_适配器-retrofit2:adapter-rxjava3:" + responseBody.string());
                    }
                });
    }
}