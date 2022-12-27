package com.example.androidlearning.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Dao：增删改查表数据，实际上就是数据库访问对象database access object（Dao为Interface接口类型文件）
 * 使用 @Dao注解（属于room注解处理器androidx.room:room-compiler） 轻松操作数据表 增删改查
 * */
@Dao
public interface StudentDao {
    // 增
    @Insert
    void insertStudents(Student ... students); // 插入数据为Student类型的变量students（...表示 可变参数）

    // 删：删除Student表的所有记录，删除所有数据 需要写语句（@Delete注解用于删除单条数据）
    @Query("DELETE FROM Student")
    void deleteAllStudents();

    // 改
    @Update
    void updateStudents(Student ... students);

    // 查：查询Student表所有数据 并依据ID倒序查询
    @Query("SELECT * FROM Student ORDER BY ID DESC")
    List<Student> getAllStudent();
}
