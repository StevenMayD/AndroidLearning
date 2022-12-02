package com.example.androidlearning.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearning.R;
import com.example.androidlearning.bean.RxLoginBean.SuccessBean;
import com.example.androidlearning.core.CustomObserver;
import com.example.androidlearning.core.LoginEngine;

public class RxLoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_login);

    }

    // 登录
    public void loginClick(View view) {
        /* RX思维解决
            我的需求：
                如果登录成功，只想获取data里的数据，作为SuccessBean
                如果登录成功，只想获取message里的数据，String类型
        * */
        LoginEngine.login("dongshuaiwen", "123456")
                .subscribe(new CustomObserver() {
                    @Override
                    public void success(SuccessBean successBean) {
                        Log.d("RxLoginActivity", "成功Bean详情SuccessBean：" + successBean.toString());
                    }

                    @Override
                    public void error(String message) {
                        Log.d("RxLoginActivity", "String类型 error：" + message);
                    }
                });
    }
}