package com.example.androidlearning.view.activity.intentstudy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearning.R;

/**
 * Intent数据传递的学习：解析数据
 * */
public class IntentActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent2);

        /**
         * 接收携带回来的数据
         *  不管数据是零散传递 还是通过包裹bundle传递，接收方一样的处理（如果是bundle包括，会自动拆解bundle）
         * */
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        char sex = intent.getCharExtra("sex", 'A');
        if ((name != null)) {
            Toast.makeText(this, "name：" + name + " sex：" + sex, Toast.LENGTH_SHORT).show();
        }

        /**
         * 接收对象数据：强转接收
         * */
        Teacher teacher = (Teacher) intent.getSerializableExtra("teacher");
        if (teacher != null) {
            Toast.makeText(this, "id：" + teacher.id
                    + " 姓名：" + teacher.name
                    + " 年龄：" + teacher.age,
                    Toast.LENGTH_SHORT).show();
        }

    }
}