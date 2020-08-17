package com.cityu.mongodb.dao;

import com.cityu.mongodb.dto.CourseDto;
import com.cityu.mongodb.model.Course;
import com.cityu.mongodb.model.Department;
import com.cityu.mongodb.model.Enrolled;
import com.cityu.mongodb.model.Offer;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CourseDao extends AbstractDao {

    public List<Enrolled> getCourEnrolledList() {
        return dao.findAll(Enrolled.class);
    }

    public List<Enrolled> getEnrolledList(String courId, Integer year) {
        return dao.find(Query.query(Criteria.where("course.courId").is(courId).and("year").is(year)), Enrolled.class);
    }

    public List<Offer> findOfferedCourse() {
        return dao.findAll(Offer.class);
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
}
