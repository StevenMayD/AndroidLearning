package com.example.androidlearning.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearning.R;
import com.example.androidlearning.bean.BaseResponse;
import com.example.androidlearning.service.RetrofitHttpBinService;
import com.example.androidlearning.service.WanAndroidService;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {
    private static final String TAG = "RetrofitActivity";
    private Retrofit retrofit; // Retrofit请求对象
    private RetrofitHttpBinService httpBinService;  // Retrofit请求服务

    private Retrofit wanAndroidRetrofit;
    private WanAndroidService wanAndroidService;

    private Retrofit wanAndroidRetrofitConverter; // Retrofit请求对象（配置转换器）
    private WanAndroidService wanAndroidServiceConverter; // Retrofit请求服务（直接返回转换对象BaseResponse）

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

}