package com.example.androidlearning.utils;

// 处理广播接收者的工具类（记录广播标记）
public interface ActionUtils {
//    广播注册时 与 发送广播时 的 唯一标识，必须保持一致（给动态注册用）
    String ACTION_EQUES_UPDATE_IP = "com.learning.receiver_study_";

//    广播注册时 与 发送广播时 的 唯一标识，必须保持一致（给静态注册用）
    String ACTION_FLAG = "com.learning.receiver_flag_";
}
