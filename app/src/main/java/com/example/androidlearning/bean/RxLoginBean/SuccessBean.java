package com.example.androidlearning.bean.RxLoginBean;

import androidx.annotation.NonNull;

/* 登录 注册 成功的bean信息 (登录成功的相关数据SuccessBean)
    {
        data:{
            id:””,
            name:”"
        }
    }
* */
public class SuccessBean {
    private int id;
    private String name;

    public SuccessBean() {

    }
    // 构造方法
    public SuccessBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // setter和getter方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 重写方法(用于打印SuccessBean对象)
    @NonNull
    @Override
    public String toString() {
        return "SuccessBean{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
