package com.example.androidlearning.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * DB：数据库关联，将Entity与Dao关联（定义成继承自RoomDatabase的抽象类abstract）
 *  @Database 关联上数据表Student，指定版本号为1
 * */
@Database(entities = {Student.class}, version = 1, exportSchema = false)
public abstract class StudentDatabase extends RoomDatabase {
    // 用户只需要操作dao 我们必须暴露dao， dao被用户拿到后，就能对数据库 增删改查
    public abstract StudentDao getStudentDao();

    // 单例模式 返回DB
    private static StudentDatabase INSTANCE;
    // 声明为public 供外界调用
    public static synchronized StudentDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            // 定义数据库名字为student_database
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), StudentDatabase.class, "student_database")
                    // 如果我们想玩数据库 默认是异步线程的
                    // 慎用：强制开启 主线程 也可以操作数据库（测试可以用 真实环境下 不要用）
//                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
