package com.cityu.mongodb.service;

import com.cityu.mongodb.dao.CourseDao;
import com.cityu.mongodb.dao.StudentDao;
import com.cityu.mongodb.dto.CourseDto;
import com.cityu.mongodb.model.Course;
import com.cityu.mongodb.model.Enrolled;
import com.cityu.mongodb.model.Offer;
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

    public List<Course> getAllList() {
        return courseDao.getCourseList();
    }

    public boolean addCourse(CourseDto courDto) {
        return courseDao.addCourse(courDto);
    }

    public List<Enrolled> getEnrolledStudents(String courId, Integer year) {
        SearchCriteria criteria = new SearchCriteria();
        criteria.setCourseId(courId);
        criteria.setYear(year);
        return courseDao.getEnrolledList(null, criteria);
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

    public List<DeptCourse> getEnrolledCourses(Student student, SearchCriteria criteria) {
        return courseDao.getEnrolledList(student,criteria).stream()
                .map(enrolled -> {
                    DeptCourse d = new DeptCourse();
                    Offer offer = courseDao.getOfferedByEnrolled(enrolled);

                    d.setDeptId(offer.getDept().getDeptId());
                    d.setDeptName(offer.getDept().getDeptName());
                    d.setYear(enrolled.getYear());
                    d.setAvailablePlaces(offer.getAvailablePlaces());
                    d.setCourseId(enrolled.getCourse().getCourseId());
                    d.setTitle(enrolled.getCourse().getTitle());
                    d.setLevel(enrolled.getCourse().getLevel());
                    d.setEnrolledNum(offer.getClassSize()-offer.getAvailablePlaces());
                    d.setClassSize(offer.getClassSize());

                    return d;
                }).collect(Collectors.toList());
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

        final long totalStudentNum = studentDao.countAllStudents();
        courseDao.getEnrolledList(null, new SearchCriteria())
                .stream().collect(Collectors.groupingBy(e -> e.getYear()))
                .forEach((year, list) -> {
                    EnrolledStatistics es = new EnrolledStatistics();
                    es.setYear(year);
                    es.setTotalEnrolled(list.stream().count());
                    es.setEnrolledNum(list.stream().map(e -> e.getStudent()).distinct().count());
                    es.setUnEnrolledNum(totalStudentNum - es.getEnrolledNum());

                    result.add(es);
                });

        return result;
    }
}
