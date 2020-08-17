package com.cityu.mongodb.vo;

public class CourseStatistics {
    private String title;
    private Long enrolledNum;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getEnrolledNum() {
        return enrolledNum;
    }

    public void setEnrolledNum(Long enrolledNum) {
        this.enrolledNum = enrolledNum;
    }
}
