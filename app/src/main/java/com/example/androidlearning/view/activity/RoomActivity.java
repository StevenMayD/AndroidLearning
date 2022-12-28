package com.example.androidlearning.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearning.R;
import com.example.androidlearning.room.Student;
import com.example.androidlearning.room.manager.DBEngine;

/**
 * Room的学习：三角色编写
     Entity：定义表结构字段（数据表，并非数据库）
     Dao：增删改查表数据，实际上就是数据库访问对象database access object
     DB：数据库，将Entity与Dao关联
 * */
public class RoomActivity extends AppCompatActivity {
    private DBEngine dbEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        dbEngine = new DBEngine(this);
    }

    // 增
    public void insertAction(View view) {
        Student student1 = new Student("张无忌", 19);
        Student student2 = new Student("乔峰", 29);
        Student student3 = new Student("段誉", 17);
        dbEngine.insertStudents(student1, student2, student3); // 可变参数 可以传入一个或多个参数
    }

    // 删除部分：删除下标为3的记录
    public void deleteAction(View view) {
        Student student = new Student(null, 0); // 删除操作依据id， name和age参数无所谓
        student.setId(3);
        dbEngine.deleteStudents(student);
    }
    // 删除全部
    public void deleteAllAction(View view) {
        dbEngine.deleteAllStudents();
    }

    // 改： 修改下标为3的数据，修改为 "艾弗森" "40"
    public void updateAction(View view) {
        Student student = new Student("艾弗森", 40);
        student.setId(3);
        dbEngine.updateStudents(student);
    }

    // 查
    public void queryAction(View view) {
        dbEngine.queryAllStudents();
    }
}