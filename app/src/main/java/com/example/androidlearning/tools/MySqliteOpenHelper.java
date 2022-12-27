package com.example.androidlearning.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/* MySqliteOpenHelper 工具类 单例模式(1.构造函数需要私有化  2.对外提供函数)
*   自定义类MySqliteOpenHelper 继承自抽象类SQLiteOpenHelper
* */
public class MySqliteOpenHelper extends SQLiteOpenHelper {
    // 1.构造函数需要私有化private内部来调用（数据库必须有名字、版本号，故必须实现构造函数）
    private MySqliteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // 2.对外提供public函数调用
    private static SQLiteOpenHelper mInstance;
    public static synchronized SQLiteOpenHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySqliteOpenHelper(context, "dswDB.db", null, 1); // 工厂factory参数可以为null；   以后想要数据库升级 逐步提升版本号为2，3....
        }
        return mInstance;
    }

    @Override
    /* 数据库初始化时用的
        创建表：表数据初始化 数据库第一次创建的时候调用 第二次发现有表了 就不会重复创建了，也意味着 此函数只会执行一次
    */
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 创建表：persons表  _id, name
        // 主键：primary key 主键必须唯一（要求1：写法， _id标准的写法   要求2：类型，主键只能是integer类型）
        // 主键自动增长：autoincrement
        String sql = "create table persons(_id integer primary key autoincrement, name text)";
        sqLiteDatabase.execSQL(sql); // 执行sql
    }

    @Override
    // 数据库升级用的
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
