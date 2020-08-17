package com.cityu.mongodb.model;


import org.springframework.data.annotation.Id;

public class Course {
    @Id
    private String courseId;

    private String title;
    private String level;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (!courseId.equals(course.courseId)) return false;
        if (!title.equals(course.title)) return false;
        return level.equals(course.level);
    }

    @Override
    public int hashCode() {
        int result = courseId.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + level.hashCode();
        return result;
    }
}
