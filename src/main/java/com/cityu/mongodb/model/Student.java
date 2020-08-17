package com.cityu.mongodb.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Student {
    @Id
    private String stuId;

    private String stuName;
    private Date DOB;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }
}
