package com.cityu.mongodb.model;

import com.cityu.mongodb.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Enrolled {
    private Student student;
    private Integer year;
    private Course course;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Enrolled enrolled = (Enrolled) o;

        if (student != null ? !student.equals(enrolled.student) : enrolled.student != null) return false;
        if (year != null ? !year.equals(enrolled.year) : enrolled.year != null) return false;
        if (course != null ? !course.equals(enrolled.course) : enrolled.course != null) return false;
        return enrolDate != null ? enrolDate.equals(enrolled.enrolDate) : enrolled.enrolDate == null;
    }

    @Override
    public int hashCode() {
        int result = student != null ? student.hashCode() : 0;
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (course != null ? course.hashCode() : 0);
        result = 31 * result + (enrolDate != null ? enrolDate.hashCode() : 0);
        return result;
    }
}
