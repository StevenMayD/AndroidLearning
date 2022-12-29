package com.example.androidlearning.view.activity.intentstudy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 普通的对象 必须成为Parcelable的子类，才有作为intent传递对象的资格
 *  推荐的方式：安卓必须使用Parcelable 因为这个是支持 兼容 安卓虚拟机  且 性能更高
 * */
public class Worker implements Parcelable {
    // 定义成员
    public String name;
    public int age;

    // 定义一个传 属性变量的构造函数
    public Worker(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // TODO 注意：读取的顺序(先name后age) 和 写入的顺序 必须一致 否则报错

    // 构造函数：：IntentActivity2后读取
    protected Worker(Parcel in) {
        // 从Parcel对象里面读取成员 赋值给属性name age
        name = in.readString();
        age = in.readInt();
    }

    // 把属性写入到Parcel对象里面：IntentActivity1先写入
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    // 先不管，是系统扩展用的
    @Override
    public int describeContents() {
        return 0;
    }

    // CREATOR一定要有 自动生成/或从安卓官方文档复制，不需要自己写
    public static final Creator<Worker> CREATOR = new Creator<Worker>() {
        // 创建Worker对象 并且Parce构建好，传递给我们的Worker（成员数据就可以从Parce对象获取了）
        @Override
        public Worker createFromParcel(Parcel in) {
            return new Worker(in);
        }

        //
        @Override
        public Worker[] newArray(int size) {
            return new Worker[size];
        }
    };
}
