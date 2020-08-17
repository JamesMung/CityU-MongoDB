package com.cityu.mongodb.dto;

import com.cityu.mongodb.model.Course;

public class CourseDto {
    private String deptId;
    private String courseId;
    private String title;
    private String level;
    private Integer year;
    private Integer classSize;
    private Integer availablePlaces;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
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

    public Integer getAvailablePlaces() {
        return availablePlaces;
    }

    public void setAvailablePlaces(Integer availablePlaces) {
        this.availablePlaces = availablePlaces;
    }

    public Course toCourse() {
        Course c = new Course();
        c.setCourseId(this.courseId);
        c.setLevel(this.level);
        c.setTitle(this.title);

        return c;
    }
}
