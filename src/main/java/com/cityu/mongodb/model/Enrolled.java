package com.cityu.mongodb.model;

import com.cityu.mongodb.utils.DateUtils;

import java.util.Date;

public class Enrolled {

    private Student student;
    private Integer year;
    private Course course;
    private Date enrolDate;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getEnrolDate() {
        return enrolDate;
    }

    public void setEnrolDate(Date enrolDate) {
        this.enrolDate = DateUtils.getCurrentZoneDate(enrolDate);
    }
}
