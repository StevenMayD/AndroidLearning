package com.example.androidlearning.bean;

public class Job {
    int jobNum;
    String jobType;

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
                '}';
    }
}
