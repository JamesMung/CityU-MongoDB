package com.cityu.mongodb.vo;

public class DeptCourse {
    private String deptId;
    private String deptName;
    private String title;
    private String level;
    private Integer year;
    private Integer classSize;
    private Integer enrolledNum;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getClassSize() {
        return classSize;
    }

    public void setClassSize(Integer classSize) {
        this.classSize = classSize;
    }

    public Integer getEnrolledNum() {
        return enrolledNum;
    }

    public void setEnrolledNum(Integer enrolledNum) {
        this.enrolledNum = enrolledNum;
    }
}
