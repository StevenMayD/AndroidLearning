package com.example.androidlearning.room.manager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.androidlearning.room.Student;
import com.example.androidlearning.room.StudentDao;
import com.example.androidlearning.room.StudentDatabase;

import java.util.List;

/**
 * Database引擎：即数据库操作管理类
 * */
public class DBEngine {
    private StudentDao studentDao;

    // 由用户传递外界环境context
    public DBEngine(Context context) {
        StudentDatabase studentDatabase = StudentDatabase.getInstance(context);
        studentDao = studentDatabase.getStudentDao(); // 拿到数据库的dao 接下来的增删改查 都是对dao进行
    }

    /** 对dao进行增删改查 **/

    /**
     * 增：...表示 可变参数：调用时 可以传入很多参数 也可以传入一个参数）
     * */
    public void insertStudents(Student ... students) {
        // 给Task传studentDao进行增删改查操作 并传入操作数据students
        new InsertAsyncTask(studentDao).execute(students);
    }

    // 自定义一个异步操作类（插入数据的异步操作类）：如果我们想玩数据库 默认是在异步线程进行
    static class InsertAsyncTask extends AsyncTask<Student, Void, Void> {
        private StudentDao dao; // 类中含有一个dao对象 操作数据库

        // 构造函数 需要接收dao来操作数据库
        public InsertAsyncTask(StudentDao studentDao) {
            dao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            dao.insertStudents(students); // 通过dao插入表数据
            return null;
        }
    }

    /**
     * 删：
     * */
    // 条件删除
    public void deleteStudents(Student ... students) {
        new DeleteAsyncTask(studentDao).execute(students);
    }
    // 删除所有
    public void deleteAllStudents() {
        new DeleteAllAsyncTask(studentDao).execute();
    }

    // 条件删除
    static class DeleteAsyncTask extends AsyncTask<Student, Void, Void> {
        private StudentDao dao;

        // 构造函数 需要接收dao来操作数据库
        public DeleteAsyncTask(StudentDao studentDao) {
            dao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) { // 既然传递了student进来 就是依据条件删除
            dao.deleteStudent(students); // 通过dao删除表数据
            return null;
        }
    }
    // 删除所有数据 不需要students参数 参数为Void
    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private StudentDao dao;

        // 构造函数 需要接收dao来操作数据库
        public DeleteAllAsyncTask(StudentDao studentDao) {
            dao = studentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllStudents(); // 通过dao删除全部表数据
            return null;
        }
    }

    /**
     * 改：
     * */
    public void updateStudents(Student ... students) {
        new UpdateAsyncTask(studentDao).execute(students);
    }

    static class UpdateAsyncTask extends AsyncTask<Student, Void, Void> {
        private StudentDao dao;

        // 构造函数 需要接收dao来操作数据库
        public UpdateAsyncTask(StudentDao studentDao) {
            dao = studentDao;
        }

        @Override
        protected Void doInBackground(Student... students) {
            dao.updateStudents(students); // 通过更新表数据
            return null;
        }
    }

    /**
     * 查：查询全部数据 不需要条件
     * */
    public void queryAllStudents() {
        // 给Task传studentDao进行增删改查操作 并传入操作数据students
        new QueryAllAsyncTask(studentDao).execute();
    }

    // 自定义一个异步操作类（插入数据的异步操作类）：如果我们想玩数据库 默认是在异步线程进行
    static class QueryAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private StudentDao dao; // 类中含有一个dao对象 操作数据库

        // 构造函数 需要接收dao来操作数据库
        public QueryAllAsyncTask(StudentDao studentDao) {
            dao = studentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<Student> allStudent = dao.getAllStudent(); // 通过dao查询表数据
            // 遍历全部查询结果
            for (Student student : allStudent) {
                Log.d("DBEngine-QueryAllAsyncTask", "doInBackground: 全部 查询 每一项：" + student.toString());
            }
            return null;
        }
    }
}
