package com.example.androidlearning.view.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

    /*
        参数1： SP的名字
        参数2： SP保存的时候用的模式（Context.MODE_APPEND-追加：每次保存都会追加；Context.MODE_PRIVATE-常规：每次保存都会更替）
       public SharedPreferences getSharedPreferences(String name, int mode) {
            throw new RuntimeException("Stub!");
        }
     */

    // 通过SP来保存数据
    public void saveDataBySP(View view) {
        SharedPreferences sp_name1 = getSharedPreferences("SP_name1", Context.MODE_PRIVATE);
        sp_name1.edit().putString("key1", "九阳神功").apply(); // apply才会写入到xml配置文件里面去
    }

    // 从SP获取数据
    public void getDataBySP(View view) {
        SharedPreferences sp_name1 = getSharedPreferences("SP_name1", Context.MODE_PRIVATE);
        String value = sp_name1.getString("key1", "key_default"); // 假设key1 获取的值时空的，那么就会使用key_default对应的值
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
    }
}