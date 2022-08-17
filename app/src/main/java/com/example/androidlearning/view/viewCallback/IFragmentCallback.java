package com.example.androidlearning.view.viewCallback;

// 接口用于fragment传递数据给activity：接口类，通过在activity类中创建接口对象，与fragment类形成一种弱关联
public interface IFragmentCallback {
    // 传递数据给activity
    void sendMsgToActivity(String string);
    // 获取数据从activity（这种获取方式，只能获取activity准备好的数据；观察者模式，可以主动观察activity获取其数据）
    String getMsgFromActivity(String msg);
}
