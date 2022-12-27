package com.example.androidlearning.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearning.R;
import com.example.androidlearning.bean.RxLoginBean.SuccessBean;
import com.example.androidlearning.core.CustomObserver;
import com.example.androidlearning.core.LoginEngine;
import com.example.androidlearning.tools.MySqliteOpenHelper;

public class RxLoginActivity extends AppCompatActivity {
    SharedPreferences sp;
    private EditText et_name;
    private EditText et_pwd;
    private CheckBox cb_remenberpwd;
    private CheckBox cb_autologin;
    private Button bt_login;
    private Button bt_register;

    // 全局静态变量
    private static String Remenberpwd = "remenberpwd";
    private static String Autologin = "autologin";
    private static String Name = "name";
    private static String Password = "pwd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_login);

        // 获取首选项 SP
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);

        initView();

        // 回显数据：第二次打开的时候，从sp获取数据，进行画面同步
        boolean remenberpwd = sp.getBoolean(Remenberpwd, false); // 如果获取为空 默认值为false
        boolean autologin = sp.getBoolean(Autologin, false);
        if (remenberpwd) {
            // 获取sp保存的name和pwd  并显示到EditText上
            String name = sp.getString(Name, "");
            String pwd = sp.getString(Password, "");
            et_name.setText(name);
            et_pwd.setText(pwd);
            cb_remenberpwd.setChecked(true); // 勾选上
        }
        if (autologin) {
            cb_autologin.setChecked(true);
            // 模拟自动登录
            Toast.makeText(this, "自动登录了", Toast.LENGTH_SHORT).show();
        }
    }

//    初始化
    private void initView() {
        // 找到控件
        et_name = findViewById(R.id.et_name);
        et_pwd = findViewById(R.id.et_pwd);
        cb_remenberpwd = findViewById(R.id.cb_remenberpwd);
        cb_autologin = findViewById(R.id.cb_autologin);
        bt_login = findViewById(R.id.bt_login);
        bt_register = findViewById(R.id.bt_register);

        // 设置监听
        MyOnClickListener listener = new MyOnClickListener();
        bt_login.setOnClickListener(listener);
        bt_register.setOnClickListener(listener);
    }

    // 定义一个内部类（自定义监听类）
    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_register:
                    break;
                // 监听到点击按钮为login
                case R.id.bt_login:
                    String name = et_name.getText().toString().trim(); // 拿到用户名 并去空格
                    String pwd = et_pwd.getText().toString().trim();
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
                        Toast.makeText(RxLoginActivity.this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
                    } else {
                        //  如果记录了密码
                        if (cb_remenberpwd.isChecked()) {
                            // 将用户名和密码保存 同时勾选状态也保存
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString(Name, name); // 保存用户名
                            editor.putString(Password, pwd); // 保存密码
                            editor.putBoolean(Remenberpwd, true); // 保存记住密码的勾选状态
                            editor.apply(); // 确认保存
                        }

                        // 自动登录 勾选
                        if (cb_autologin.isChecked()) {
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putBoolean(Autologin, true); // 保存自动登录的勾选状态
                            editor.apply(); // 确认保存
                        }
                    }
                    break;
            }
        }
    }

    // 登录
    public void loginClick(View view) {
        /* RX思维解决
            我的需求：
                如果登录成功，只想获取data里的数据，作为SuccessBean
                如果登录成功，只想获取message里的数据，String类型
        * */
        LoginEngine.login("dongshuaiwen", "123456")
                .subscribe(new CustomObserver() {
                    @Override
                    public void success(SuccessBean successBean) {
                        Log.d("RxLoginActivity", "成功Bean详情SuccessBean：" + successBean.toString());
                    }

                    @Override
                    public void error(String message) {
                        Log.d("RxLoginActivity", "String类型 error：" + message);
                    }
                });
    }

    /*
        参数1： SP的名字
        参数2： SP保存的时候用的模式（Context.MODE_APPEND-追加：每次保存都会追加；Context.MODE_PRIVATE-常规：每次保存都会更替）
       public SharedPreferences getSharedPreferences(String name, int mode) {
            throw new RuntimeException("Stub!");
        }
     */

    // 通过SP来保存数据
    public void saveDataBySP(View view) {
        SharedPreferences sp_name1 = getSharedPreferences("SP_name1", Context.MODE_PRIVATE);
        sp_name1.edit().putString("key1", "九阳神功").apply(); // apply才会写入到xml配置文件里面去
    }

    // 从SP获取数据
    public void getDataBySP(View view) {
        SharedPreferences sp_name1 = getSharedPreferences("SP_name1", Context.MODE_PRIVATE);
        String value = sp_name1.getString("key1", "key_default"); // 假设key1 获取的值时空的，那么就会使用key_default对应的值
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
    }



    // 创建数据库文件
    public void createDB(View view) {
        // 获取数据管理类的单例
        SQLiteOpenHelper sqLiteOpenHelper = MySqliteOpenHelper.getInstance(this);
        // databases数据库文件夹的创建 靠下面这句话(help.getReadableDatabase或者help.getWritableDatabase)
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
    }

    // 查询数据表
    public void query(View view) {
        SQLiteOpenHelper sqLiteOpenHelper = MySqliteOpenHelper.getInstance(this);
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase(); // 查询为 读的操作
        if (db.isOpen()) { // 数据库打开成功
            // query返回游标
            Cursor cursor = db.rawQuery("select * from persons", null);
            // 迭代游标 来遍历数据
            while (cursor.moveToNext()) { // 获取到一条数据
                // 偷懒的写法 获取数据（前提是知道表结构）
//                int _id = cursor.getInt(0); // 获取主键字段的数据
//                String name = cursor.getString(1); // 获取name字段的数据

                // 规范的写法
                int _id = cursor.getInt(cursor.getColumnIndex("_id")); // 获取主键字段的数据
                String name = cursor.getString(cursor.getColumnIndex("name")); // 获取name字段的数据

                Log.d("RxLoginActivity", "query: _id: " + _id + "  name: " + name);
            }

            // 一定要关闭游标 否则耗费性能 （数据库也要关闭（规范写法））
            cursor.close();
            db.close();
        }
    }

    // 插入表数据
    public void insert(View view) {
        SQLiteOpenHelper sqLiteOpenHelper = MySqliteOpenHelper.getInstance(this);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase(); // 插入 为写操作

        if (db.isOpen()) {
            // 插入语句
            String sql = "insert into persons(name) values('董帅文')";
            db.execSQL(sql);
        }
        db.close();
    }

    // 修改表数据
    public void update(View view) {
        SQLiteOpenHelper sqLiteOpenHelper = MySqliteOpenHelper.getInstance(this);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase(); // 修改 为写操作

        if (db.isOpen()) {
            // 修改语句：将id为3的数据 name修改为李连杰
            String sql = "update persons set name=? where _id=?";
            db.execSQL(sql, new Object[]{"李连杰", 3});
        }
        db.close();
    }

    // 删除表数据
    public void delete(View view) {
        SQLiteOpenHelper sqLiteOpenHelper = MySqliteOpenHelper.getInstance(this);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase(); // 修改 为写操作

        if (db.isOpen()) {
            // 删除id为3的数据
            String sql = "delete from persons where _id=?";
            db.execSQL(sql, new Object[]{3});
        }
        db.close();
    }
}