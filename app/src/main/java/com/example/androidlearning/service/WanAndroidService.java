package com.example.androidlearning.service;

import com.example.androidlearning.bean.BaseResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WanAndroidService {
    @POST("user/login")
    @FormUrlEncoded
    Call<ResponseBody> login(@Field("username") String username, @Field("password") String pwd);

    // 带自动转换类型的请求（将响应自动转换为BaseResponse）
    @POST("user/login")
    @FormUrlEncoded
    Call<BaseResponse> loginWithAutoConverter(@Field("username") String username, @Field("password") String pwd);
}
