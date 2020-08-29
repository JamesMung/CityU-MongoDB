package com.cityu.mongodb.vo;

public class EnrolledStatistics {
    private Integer year;
    private Long enrolledNum;
    private Long unEnrolledNum;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getEnrolledNum() {
        return enrolledNum;
    }

    public void setEnrolledNum(Long enrolledNum) {
        this.enrolledNum = enrolledNum;
    }

    public Long getUnEnrolledNum() {
        return unEnrolledNum;
    }

    public void setUnEnrolledNum(Long unEnrolledNum) {
        this.unEnrolledNum = unEnrolledNum;
    }
}
