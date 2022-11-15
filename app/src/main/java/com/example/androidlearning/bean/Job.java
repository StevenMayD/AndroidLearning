package com.example.androidlearning.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Job {
    // 默认就是加上了@Expose注解，表示 属性参与序列化和反序列化
    @Expose
    int jobNum;
    // 可以配置是否参与 序列化和反序列化: 为了生效外界Gson还需要使用 Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    @Expose(serialize = true, deserialize = true)
    String jobType;

    // 由于class是关键字，不用用于命名，使用@SerializedName("class")注解，可以控制 序列化出来的是class属性名 而不是cls
    @SerializedName("class")
    int cls;

    public int getJobNum() {
        return jobNum;
    }

    public void setJobNum(int jobNum) {
        this.jobNum = jobNum;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
    public int getCls() {
        return cls;
    }

    public void setCls(int cls) {
        this.cls = cls;
    }
    // 添加构造方法（共外界调用 生成Bean对象）
    public Job(int jobNum, String jobType) {
        this.jobNum = jobNum;
        this.jobType = jobType;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobNum=" + jobNum +
                ", jobType='" + jobType + '\'' +
                ", cls=" + cls +
                '}';
    }
}
