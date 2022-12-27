package com.example.androidlearning.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 一张数据表Student (数据表，并非数据库)
 *  使用 @Entity注解（属于room注解处理器androidx.room:room-compiler） 直接使得Student类 同时成为一张数据表Student
 * */
@Entity
public class Student {
    // 主键唯一 主键自动增长
    @PrimaryKey(autoGenerate = true)
    private int id;         // 数据表主键字段
    private String name;    // 数据表字段
    private int age;        // 数据表字段

    // 构造函数
    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // setter getter方法
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // 方便打印显示
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
