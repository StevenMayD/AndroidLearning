package com.example.androidlearning.core;

import com.example.androidlearning.bean.RxLoginBean.ResponseResult;
import com.example.androidlearning.bean.RxLoginBean.SuccessBean;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/* 实现一个自定义的Observer（用来处理个性的需求）
*   并添加abstract 使CustomObserver成为抽象类
* */
public abstract class CustomObserver implements Observer<ResponseResult> {
    // 抽象提取：如果登录成功 则提取SuccessBean
    public abstract void success(SuccessBean successBean);
    // 抽象提取：如果登录失败 则提取message
    public abstract void error(String message);


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    // 接收上一层给我的响应
    public void onNext(ResponseResult responseResult) {
        if (responseResult.getData() == null) {
            error(responseResult.getMessage() + "请求失败，请检查日志");
        } else {
            success(responseResult.getData());
        }
    }

    @Override
    // 链条思维发生了异常
    public void onError(Throwable e) {
        error(e.getMessage() + "出错，请检查错误详情");
    }

    @Override
    public void onComplete() {

    }
}
