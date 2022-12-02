package com.example.androidlearning.bean.RxLoginBean;

/* 请求服务器的结果bean(总bean)
{
    data:{“token”:””, “id”:””} // 登录成功的相关数据SuccessBean
    code:200
    message:”登录成功"
}
*/
public class ResponseResult {
    private SuccessBean data; // 登录成功
    private int code;
    private String message;

    public ResponseResult() {

    }

    public ResponseResult(SuccessBean data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public SuccessBean getData() {
        return data;
    }

    public void setData(SuccessBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "data=" + data +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
