package com.example.androidlearning.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearning.R;
import com.example.androidlearning.service.RetrofitHttpBinService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitActivity extends AppCompatActivity {
    private static final String TAG = "RetrofitActivity";
    private Retrofit retrofit; // Retrofit请求对象
    private RetrofitHttpBinService httpBinService;  // Retrofit请求服务

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        // Retrofit注意指定baseUrl
        retrofit = new Retrofit.Builder().baseUrl("https://www.httpbin.org/").build();
        httpBinService = retrofit.create(RetrofitHttpBinService.class);
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

}