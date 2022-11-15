package com.example.androidlearning.bean;

// java bean类用于放置数据
public class Bean {
    // 添加属性
    String name;
    int age;
    Job job;

    // Generator快捷键（Ctrl+Enter）： 添加getter setter方法
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
    public Job getJob() {
        return job;
    }
    public void setJob(Job job) {
        this.job = job;
    }

    // 添加构造方法（共外界调用 生成Bean对象）
    public Bean() {

    }

    public Bean(String name, int age, Job job) {
        this.name = name;
        this.age = age;
        this.job = job;
    }
}
