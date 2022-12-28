package com.example.androidlearning.view.activity.intentstudy;

import java.io.Serializable;

/**
 * 普通的对象 必须实现Serializable接口，才有作为intent传递对象的资格
 * */
public class Teacher implements Serializable {
    public int id;
    public String name;
    public int age;
}
