package com.example.androidlearning.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.androidlearning.R;

/**
 * FragmentTransactionActivity的片段
 * */
public class Fragment1 extends Fragment {
    private View root;
    private TextView textView;
    private Button button;

    // 体会fragment生命周期
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("fragment生命周期", "onAttach: ");
    }

    @Override
    // fragment生命周期和activity类似 都有onCreate；但绑定layout在onCreateView中进行
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("fragment生命周期", "onCreate: ");

        Bundle bundle = this.getArguments(); // 这里getArguments：获取的就是activity传入的argument
        String string = bundle.getString("message");
        Log.d("activity给fragment传参", "onCreate: " + string); // Log.d打印debug日志
    }

    @Override
    // 绑定layout界面布局
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("fragment生命周期", "onCreateView: ");

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("fragment生命周期", "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("fragment生命周期", "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("fragment生命周期", "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("fragment生命周期", "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("fragment生命周期", "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("fragment生命周期", "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("fragment生命周期", "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("fragment生命周期", "onDetach: ");
    }
}