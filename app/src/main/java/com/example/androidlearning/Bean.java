package com.example.androidlearning;

import androidx.annotation.NonNull;

// java bean类用于放置数据
public class Bean {
    // 添加属性
    String name;

    // Generator快捷键（Ctrl+Enter）： 添加getter setter方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
