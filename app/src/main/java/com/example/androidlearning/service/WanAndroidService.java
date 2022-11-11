package com.example.androidlearning.service;

import com.example.androidlearning.bean.BaseResponse;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WanAndroidService {
    @POST("user/login")
    @FormUrlEncoded
    Call<ResponseBody> login(@Field("username") String username, @Field("password") String pwd);

    // 带自动转换类型的请求（将响应自动转换为BaseResponse）
    @POST("user/login")
    @FormUrlEncoded
    Call<BaseResponse> loginWithAutoConverter(@Field("username") String username, @Field("password") String pwd);


    /* 登录接口 (使用适配器，从而返回Flowable类型 不必返回Call类型)
    * https://www.wanandroid.com/user/login
    方法：POST
    参数：username，password
    * */
    @POST("user/login")
    @FormUrlEncoded
    Flowable<BaseResponse> login2(@Field("username") String username, @Field("password") String pwd);

    /* 获取收藏列表接口
       https://www.wanandroid.com/lg/collect/list/0/json
       方法：GET
       参数： 页码：拼接在链接中，从0开始。
    */
    @GET("lg/collect/list/{pageNum}/json")
    Flowable<ResponseBody> getArticle(@Path("pageNum") int pageNum);
}
