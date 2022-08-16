package com.example.androidlearning.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidlearning.R;

public class Fragment1 extends Fragment {
    private View root;
    private TextView textView;
    private Button button;

    @Override
    // fragment生命周期和activity类似 都有onCreate；但绑定layout在onCreateView中进行
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments(); // 这里getArguments：获取的就是activity传入的argument
        String string = bundle.getString("message");
        Log.d("activity给fragment传参", "onCreate: " + string); // Log.d打印debug日志
    }

    @Override
    // 绑定layout界面布局
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_1, container, false);
        }
        // fragment是小activity，同样可以响应各种点击事件
        textView = root.findViewById(R.id.fagment_textview);
        button = root.findViewById(R.id.fagment_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("是的，我很好，你呢？");
            }
        });
        return root;
    }
}