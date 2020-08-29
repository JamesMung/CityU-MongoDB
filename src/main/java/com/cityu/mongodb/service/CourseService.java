package com.cityu.mongodb.service;

import com.cityu.mongodb.dao.CourseDao;
import com.cityu.mongodb.dao.StudentDao;
import com.cityu.mongodb.dto.CourseDto;
import com.cityu.mongodb.model.Enrolled;
import com.cityu.mongodb.model.Student;
import com.cityu.mongodb.query.SearchCriteria;
import com.cityu.mongodb.vo.CourseStatistics;
import com.cityu.mongodb.vo.DeptCourse;
import com.cityu.mongodb.vo.EnrolledStatistics;
import com.cityu.mongodb.vo.OfferedStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private CourseDao courseDao;
    private StudentDao studentDao;

    public CourseDao getCourseDao() {
        return courseDao;
    }

    @Autowired
    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

    @Autowired
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public List<CourseStatistics> getCourseEnrollStatistics(Integer year) {
        List<CourseStatistics> result = new ArrayList<>();

        courseDao.getCourEnrolledList(year).stream()
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
        return searchCourse(null);
    }

    public boolean addCourse(CourseDto courDto) {
        return courseDao.addCourse(courDto);
    }

    public List<Enrolled> getEnrolledStudents(String courId, Integer year) {
        return courseDao.getEnrolledList(courId, year);
    }

    public List<DeptCourse> searchCourse(SearchCriteria criteria) {
        return courseDao.findOfferedCourse(criteria).stream()
                .map(offer -> {
                    DeptCourse d = new DeptCourse();
                    d.setDeptId(offer.getDept().getDeptId());
                    d.setDeptName(offer.getDept().getDeptName());
                    d.setYear(offer.getYear());
                    d.setAvailablePlaces(offer.getAvailablePlaces());
                    d.setCourseId(offer.getCourse().getCourseId());
                    d.setTitle(offer.getCourse().getTitle());
                    d.setLevel(offer.getCourse().getLevel());
                    d.setEnrolledNum(offer.getClassSize()-offer.getAvailablePlaces());
                    d.setClassSize(offer.getClassSize());

                    return d;
                }).collect(Collectors.toList());
    }

    public List<Enrolled> getEnrolledCourses(Student student) {
        return courseDao.getEnrolledList(student);
    }

    public List<DeptCourse> getUnEnrolledCourses(Student student, SearchCriteria criteria) {
        return courseDao.getUnEnrolledCourses(student, criteria).stream()
                .map(offer -> {
                    DeptCourse d = new DeptCourse();
                    d.setDeptId(offer.getDept().getDeptId());
                    d.setDeptName(offer.getDept().getDeptName());
                    d.setYear(offer.getYear());
                    d.setAvailablePlaces(offer.getAvailablePlaces());
                    d.setCourseId(offer.getCourse().getCourseId());
                    d.setTitle(offer.getCourse().getTitle());
                    d.setLevel(offer.getCourse().getLevel());
                    d.setEnrolledNum(offer.getClassSize()-offer.getAvailablePlaces());
                    d.setClassSize(offer.getClassSize());

                    return d;
                }).collect(Collectors.toList());
    }

    public List<OfferedStatistics> getOfferedStatistic() {
        List<OfferedStatistics> result = new ArrayList<>();

        courseDao.findOfferedCourse(null).stream()
                .collect(Collectors.groupingBy(o -> o.getYear()))
                .forEach((year, list) -> {
                    OfferedStatistics o = new OfferedStatistics();
                    o.setYear(year);
                    o.setOfferedNum(Integer.valueOf(list.size()).longValue());
                    result.add(o);
                });

        return result;
    }

    public List<EnrolledStatistics> getEnrolledStatistic() {
        List<EnrolledStatistics> result = new ArrayList<>();

        courseDao.getEnrolledList(null, null)
                .stream().collect(Collectors.groupingBy(e -> e.getYear()))
                .forEach((year, list) -> {
                    EnrolledStatistics es = new EnrolledStatistics();
                    es.setYear(year);
                    es.setEnrolledNum(Integer.valueOf(list.size()).longValue());

                    List<Student> enrolledStudents = list.stream().map(e -> e.getStudent()).collect(Collectors.toList());
                    es.setUnEnrolledNum(Integer.valueOf(studentDao.findNotEnrolledStudent(enrolledStudents).size()).longValue());

                    result.add(es);
                });

        return result;
    }
}
