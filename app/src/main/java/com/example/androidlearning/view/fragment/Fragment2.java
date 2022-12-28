package com.example.androidlearning.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.androidlearning.R;
import com.example.androidlearning.view.viewCallback.IFragmentCallback;

/**
 * FragmentTransactionActivity的片段
 * */
public class Fragment2 extends Fragment {
    private View root;
    private TextView textView;
    private Button button;
    private IFragmentCallback fragmentCallback; // 给fragment类 声明一个接口属性

    @Override
    // fragment生命周期和activity类似 都有onCreate；但绑定layout在onCreateView中进行
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // 给fragment类 定义一个公开方法：其接口属性 由调用该方法 传递
    public void setFragmentCallback(IFragmentCallback fragmentCallback) {
        this.fragmentCallback = fragmentCallback;
    }

    @Override
    // 绑定layout界面布局
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 由于onCreateView会被多次调用 root定义成全局属性
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_2, container, false);
        }
        // fragment是小activity，同样可以响应各种点击事件
        textView = root.findViewById(R.id.fagment_textview);
        button = root.findViewById(R.id.fagment_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 更新UI
                textView.setText("是的，我很好，你呢2？");
                // 接口方式，fragment向activity传数据：类似iOS代理传值
                fragmentCallback.sendMsgToActivity("你好，这是来自fragment的信息");
                // 接口方式，fragment向activity主动获取数据：类似iOS代理传值
                String msg = fragmentCallback.getMsgFromActivity(", hello");
                // 由于fragment没有上下文场景，需要.getContext()获取上下文
                Toast.makeText(Fragment2.this.getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}