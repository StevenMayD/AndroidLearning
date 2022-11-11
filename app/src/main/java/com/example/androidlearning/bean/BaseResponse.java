/**
  * Copyright 2022 bejson.com 
  */
package com.example.androidlearning.bean;

/**
 * Auto-generated: 2022-11-11 17:22:8
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */

// JavaBean类 保存接口请求响应的数据对象
public class BaseResponse {

    private Data data;
    private int errorCode;
    private String errorMsg;
    public void setData(Data data) {
         this.data = data;
     }
     public Data getData() {
         return data;
     }

    public void setErrorCode(int errorCode) {
         this.errorCode = errorCode;
     }
     public int getErrorCode() {
         return errorCode;
     }

    public void setErrorMsg(String errorMsg) {
         this.errorMsg = errorMsg;
     }
     public String getErrorMsg() {
         return errorMsg;
     }

     // 重写一个对象转字符串的方法(用于控制台打印)
    @Override
    public String toString() {
        return "BaseResponse{" +
                "data=" + data+
                ", errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}