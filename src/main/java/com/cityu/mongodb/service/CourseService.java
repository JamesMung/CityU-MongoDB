package com.cityu.mongodb.service;

import com.cityu.mongodb.dao.CourseDao;
import com.cityu.mongodb.dto.CourseDto;
import com.cityu.mongodb.model.Course;
import com.cityu.mongodb.model.Enrolled;
import com.cityu.mongodb.vo.CourseStatistics;
import com.cityu.mongodb.vo.DeptCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private CourseDao courseDao;

    public CourseDao getCourseDao() {
        return courseDao;
    }

    @Autowired
    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public List<CourseStatistics> getStatisData() {
        List<CourseStatistics> result = new ArrayList<>();

        courseDao.getCourEnrolledList().stream()
                .collect(Collectors.groupingBy(e -> e.getCourse(), Collectors.counting()))
                .forEach((course, num) -> {
                    CourseStatistics c = new CourseStatistics();
                    c.setTitle(course.getTitle());
                    c.setEnrolledNum(num);

                    result.add(c);
                });

        return result;
    }

    public List<DeptCourse> getAllList() {
        return courseDao.findOfferedCourse().stream()
                .map(offer -> {
                    DeptCourse d = new DeptCourse();
                    d.setDeptId(offer.getDept().getDeptId());
                    d.setDeptName(offer.getDept().getDeptName());
                    d.setYear(offer.getYear());
                    d.setTitle(offer.getCourse().getTitle());
                    d.setLevel(offer.getCourse().getLevel());
                    d.setEnrolledNum(offer.getClassSize()-offer.getAvailablePlaces());
                    d.setClassSize(offer.getClassSize());

                    return d;
                }).collect(Collectors.toList());
    }

    public boolean addCourse(CourseDto courDto) {
        return courseDao.addCourse(courDto);
    }

    public List<Enrolled> getEnrolledStudents(String courId, Integer year) {
        return courseDao.getEnrolledList(courId, year);
    }
}
