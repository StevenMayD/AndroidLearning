package com.example.androidlearning.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.androidlearning.R;
import com.example.androidlearning.view.fragment.Fragment1;
import com.example.androidlearning.view.fragment.Fragment2;
import com.example.androidlearning.view.viewCallback.IFragmentCallback;

/**
 * FragmentTransaction的学习：Fragment的动态替换与管理
 * */
// 这里添加 遵循协议implements View.OnClickListener，button的点击事件则不需要再写回调的形式setOnClickListener(new OnClickListener)
public class FragmentTransactionActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_transaction);

        Button changeBtn = findViewById(R.id.btn_change);
        changeBtn.setOnClickListener(this);

        Button replace = findViewById(R.id.btn_replace);
        replace.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change:
//                replaceFragment(new Fragment1());

                // 1、activity给fragment传参：
                // Bundle保存参数来自activity的数据
                Bundle bundle = new Bundle();
                bundle.putString("message", "来自activity的传参");
                Fragment1 fragment1 = new Fragment1();
                // Fragment设置参数
                fragment1.setArguments(bundle);
                replaceFragment(fragment1);
                break;
            case R.id.btn_replace:
//                replaceFragment(new Fragment2());

                // 2、fragment给activity传参：
                Fragment2 fragment2 = new Fragment2();
                fragment2.setFragmentCallback(new IFragmentCallback(){
                    @Override
                    public void sendMsgToActivity(String string) {
                        // activity的匿名内部类中 使用"上下文"，不能直接传this，而是activity.this
                        Toast.makeText(FragmentTransactionActivity.this, string, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public String getMsgFromActivity(String msg) {
                        return "来自activity的传参" + msg;
                    }
                });
                replaceFragment(fragment2);
                break;
        }
    }

    // 动态切换fragment: 五步骤：1、创建待处理的fragment
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager(); // 2、获取Fragment管理器
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); // 3、开启Fragment事务类transaction
        fragmentTransaction.replace(R.id.framelayout, fragment); // 4、完成一次事务：使用transaction，将fragment替换到 framelayout上
        fragmentTransaction.addToBackStack(null); // fragment添加进栈（这样当页面点击或滑动返回时，会挨个出栈，最后一个返回时，才会退出应用到桌面）
        fragmentTransaction.commit(); // 5、提交事务
    }
}