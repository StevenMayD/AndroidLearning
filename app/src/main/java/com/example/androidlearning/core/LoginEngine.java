package com.example.androidlearning.core;


import com.example.androidlearning.bean.RxLoginBean.ResponseResult;
import com.example.androidlearning.bean.RxLoginBean.SuccessBean;

import io.reactivex.Observable;


/*  登录引擎（core目录放置引擎）
* 登录引擎：处理登录相关的事务
* */
public class LoginEngine {
    // 起点（返回一个Rx思维中的起点）
    public static Observable<ResponseResult> login(String name, String pwd) {
        // 最终返回 总Bean
        ResponseResult responseResult = new ResponseResult();
        if ("dongshuaiwen".equals(name) && "123456".equals(pwd)) {
            /* 登录成功 则返回
            {
                data:{id:””, name:”"} // 登录成功的相关数据SuccessBean
                code:200
                message:”登录成功"
            }
            * */
            SuccessBean successBean = new SuccessBean();
            successBean.setId(10086);
            successBean.setName("dongshuaiwen 登录成功");

            responseResult.setData(successBean);
            responseResult.setCode(200);
            responseResult.setMessage("登录成功");
        } else {
            /* 登录失败 则返回
            {
                data:null
                code:404
                message:”登录失败"
            }
            * */
            responseResult.setData(null);
            responseResult.setCode(404);
            responseResult.setMessage("登录失败");
        }
        // 起点
        return Observable.just(responseResult);
    }
}
