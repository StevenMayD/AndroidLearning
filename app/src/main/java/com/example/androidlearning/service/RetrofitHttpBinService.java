package com.example.androidlearning.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

// 网络请求中间层
public interface RetrofitHttpBinService {
    // 注意 Call是retrofit2里的Call， ResponseBody则是依赖okhttp3的ResponseBody， 互相都有，导包不要搞错了

    /*
    * @POST注解：请求方式，会进行POST的http请求
    * @FormUrlEncoded注解：则是会进行 FormUrlEncoded的数据提交方式， form键值形式
    * @Field注解：post请求时，键值形式参数的 键key
    * */
    @POST("post")
    @FormUrlEncoded
    Call<ResponseBody> postRequest(@Field("userName") String userName, @Field("password") String pwd);

    /*
    * @Query注解：get请求时，query注解参数名称
    * */
    @GET("get")
    Call<ResponseBody> getRequest(@Query("userName") String userName, @Query("password") String pwd);
}
