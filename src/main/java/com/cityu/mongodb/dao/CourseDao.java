package com.cityu.mongodb.dao;

import com.cityu.mongodb.dto.CourseDto;
import com.cityu.mongodb.model.*;
import com.cityu.mongodb.query.SearchCriteria;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CourseDao extends AbstractDao {

    public List<Enrolled> getCourEnrolledList(Integer year) {
        Criteria c = new Criteria();
        if (year != null) c.and("year").is(year);

        return dao.find(Query.query(c), Enrolled.class);
    }

    public List<Offer> findOfferedCourse(SearchCriteria criteria) {
        if (criteria == null) {
            return dao.findAll(Offer.class);
        } else {
            Criteria c = new Criteria();
            if (!StringUtils.isEmpty(criteria.getCourseId())) c.and("course.courseId").is(criteria.getCourseId());
            if (!StringUtils.isEmpty(criteria.getDeptId())) c.and("dept.deptId").is(criteria.getDeptId());
            if (!StringUtils.isEmpty(criteria.getLevel())) c.and("course.level").is(criteria.getLevel());
            if (criteria.getYear() != null) c.and("year").is(criteria.getYear());

            return dao.find(Query.query(c), Offer.class);
        }
    }

    @Transactional
    public boolean addCourse(CourseDto courDto) {
        Query existQuery = Query.query(Criteria.where("dept.deptId").is(courDto.getDeptId())
                .and("course.courseId").is(courDto.getCourseId())
                .and("year").is(courDto.getYear()));

        if(dao.exists(existQuery, Offer.class))
            return false;

        Course c = courDto.toCourse();
        dao.insert(c);

        Department dept = dao.findById(courDto.getDeptId(), Department.class);
        Offer offer = new Offer();
        offer.setDept(dept);
        offer.setCourse(c);
        offer.setYear(courDto.getYear());
        offer.setClassSize(courDto.getClassSize());
        offer.setAvailablePlaces(courDto.getAvailablePlaces());

        dao.insert(offer);

        return true;
    }

    public List<Enrolled> getEnrolledList(Student student, SearchCriteria criteria) {
        Criteria c = new Criteria();
        if (student != null) c.and("student.stuId").is(student.getStuId());
        if (!StringUtils.isEmpty(criteria.getCourseId())) c.and("course.courseId").is(criteria.getCourseId());
        if (!StringUtils.isEmpty(criteria.getLevel())) c.and("course.level").is(criteria.getLevel());
        if (criteria.getYear() != null) c.and("year").is(criteria.getYear());

        if (StringUtils.isEmpty(criteria.getDeptId())) {
            return dao.find(Query.query(c), Enrolled.class);
        } else {
            List<Course> courses = dao.find(Query.query(Criteria.where("dept.deptId").is(criteria.getDeptId())), Offer.class)
                    .stream().map(offer -> offer.getCourse()).collect(Collectors.toList());

            return dao.find(Query.query(c), Enrolled.class).stream().filter(e -> courses.contains(e.getCourse())).collect(Collectors.toList());
        }
    }

    public Offer getOfferedByEnrolled(Enrolled enrolled) {
        return dao.findOne(Query.query(Criteria.where("course.courseId").is(enrolled.getCourse().getCourseId()).and("year").is(enrolled.getYear())), Offer.class);
    }

    public List<Offer> getUnEnrolledCourses(Student student, SearchCriteria criteria) {
        List<String> enrolledIds = getEnrolledList(student, criteria).stream().map(e -> e.getCourse().getCourseId()).collect(Collectors.toList());

        Criteria c = Criteria.where("course.courseId").nin(enrolledIds);
        if (!StringUtils.isEmpty(criteria.getCourseId())) c.and("course.courseId").is(criteria.getCourseId());
        if (!StringUtils.isEmpty(criteria.getDeptId())) c.and("dept.deptId").is(criteria.getDeptId());
        if (!StringUtils.isEmpty(criteria.getLevel())) c.and("course.level").is(criteria.getLevel());
        if (criteria.getYear() != null) c.and("year").is(criteria.getYear());

        return dao.find(Query.query(c), Offer.class);
    }

    public List<Course> getCourseList() {
        return dao.findAll(Course.class);
    }

    public void delCourse(SearchCriteria criteria) {
        dao.remove(Query.query(Criteria.where("courseId").is(criteria.getCourseId())), Course.class);

        dao.remove(Query.query(Criteria.where("course.courseId").is(criteria.getCourseId()).and("dept.deptId").is(criteria.getDeptId()).and("year").is(criteria.getYear())), Offer.class);
        dao.remove(Query.query(Criteria.where("course.courseId").is(criteria.getCourseId()).and("year").is(criteria.getYear())), Enrolled.class);
    }
}
