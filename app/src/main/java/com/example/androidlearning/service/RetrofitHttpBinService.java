package com.example.androidlearning.service;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

// 网络请求中间层
public interface RetrofitHttpBinService {
    // 注意 Call是retrofit2里的Call， ResponseBody则是依赖okhttp3的ResponseBody， 互相都有，导包不要搞错了

    /*
     * @Query注解：get请求时，query注解参数名称
     * */
    @GET("get")
    Call<ResponseBody> getRequest(@Query("userName") String userName, @Query("password") String pwd);

    /*
    * @POST注解：请求方式，会进行POST的http请求（注解里要添加 请求的地址("xxx")，用于拼接baseUrl,完整的请求地址为: baseUrl/xxx）
    * @FormUrlEncoded注解：则是会进行 FormUrlEncoded的数据提交方式， form键值形式
    * @Field注解：post请求时，键值形式参数的 键key
    * 另外返回值：Call是retrofit2里的Call，ResponseBody则是okhttp3的ResponseBody
    * */
    @POST("post")
    @FormUrlEncoded
    Call<ResponseBody> postRequest(@Field("userName") String userName, @Field("password") String pwd);

    /*
    * @Body注解：post携带请求体的形式的请求
    * */
    @POST("post")
    Call<ResponseBody> postBodyRequest(@Body RequestBody body);

    /*
    * @Path注解：动态路径替换
    * @Header注解：添加请求头
    * */
    @POST("{id}")
    @FormUrlEncoded
    Call<ResponseBody> postInPathRequest(@Path("id") String path, @Header("os") String os, @Field("userName") String userName, @Field("password") String pwd);

    /*
     * @Headers注解：添加多个请求头
     * */
    @Headers({"os:android", "version:11.0"})
    @POST("post")
    Call<ResponseBody> postHeadersRequest();

    /*
    * @Url注解：直接请求指定的完整url（不依据baseUrl, 也不需要加 请求子路径）
    * */
    @POST
    Call<ResponseBody> postUrlRequest(@Url String url);
}
