package com.example.androidlearning.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearning.R;

/**
 * Room的学习：三角色编写
     Entity：定义表结构字段（数据表，并非数据库）
     Dao：增删改查表数据，实际上就是数据库访问对象database access object
     DB：数据库，将Entity与Dao关联
 * */
public class RoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
    }
}