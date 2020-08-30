package com.cityu.mongodb.dao;

import com.cityu.mongodb.constants.Constants;
import com.cityu.mongodb.model.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StudentDao extends AbstractDao {

    public List<Student> findAllStudents() {
        return dao.findAll(Student.class);
    }

    @Transactional
    public void addStudent(Student student) {
        dao.insert(student);

        // Add user record
        User maxIdUser = dao.findOne(new Query().with(Sort.by(Sort.Direction.DESC, "id")).limit(1), User.class);
        User u = new User();
        u.setId(maxIdUser == null ? 0 : maxIdUser.getId() + 1);
        u.setPassword(Constants.DEFAULT_PWD);
        u.setRole(Constants.UserRole.STUDENT);
        u.setUsername(student.getStuId());
        u.setStudent(student);

        dao.insert(u);
    }

    @Transactional
    public boolean enrollCourse(Student student, String courseId, Integer year) {
        Offer offer = dao.findOne(Query.query(Criteria.where("course.courseId").is(courseId).and("year").is(year)), Offer.class);

        if (offer.getAvailablePlaces() <= 0) return false;

        boolean exists = dao.exists(Query.query(Criteria.where("course.courseId").is(courseId).and("year").is(year).and("student.stuId").is(student.getStuId())), Enrolled.class);

        if (exists) return false;

        Enrolled e = new Enrolled();
        e.setStudent(student);
        e.setCourse(dao.findById(courseId, Course.class));
        e.setEnrolDate(new Date());
        e.setYear(year);
        dao.insert(e);

        dao.updateFirst(Query.query(Criteria.where("course.courseId").is(courseId).and("year").is(year)), Update.update("availablePlaces", offer.getAvailablePlaces() - 1), Offer.class);

        return true;
    }

    public boolean unEnrollCourse(Student student, String courseId, Integer year) {
        Query query = Query.query(Criteria.where("course.courseId").is(courseId).and("year").is(year).and("student.stuId").is(student.getStuId()));
        boolean exists = dao.exists(query, Enrolled.class);

        if (exists) {
            dao.remove(query, Enrolled.class);

            Offer offer = dao.findOne(Query.query(Criteria.where("course.courseId").is(courseId).and("year").is(year)), Offer.class);
            dao.updateFirst(Query.query(Criteria.where("course.courseId").is(courseId).and("year").is(year)), Update.update("availablePlaces", offer.getAvailablePlaces() + 1), Offer.class);
            return true;
        } else {
            return false;
        }
    }

    public void update(Student student) {
        Update updateStu = new Update().set("stuName", student.getStuName()).set("DOB", student.getDOB());
        dao.updateFirst(Query.query(Criteria.where("stuId").is(student.getStuId())), updateStu, Student.class);

        Update updateEmbedStu = new Update().set("student.stuName", student.getStuName()).set("student.DOB", student.getDOB());
        dao.findAndModify(Query.query(Criteria.where("student.stuId").is(student.getStuId())), updateEmbedStu, Enrolled.class);
    }

    public void delete(String stuId) {
        dao.remove(Query.query(Criteria.where("stuId").is(stuId)), Student.class);
        dao.remove(Query.query(Criteria.where("student.stuId").is(stuId)), Enrolled.class);
    }

    public long countAllStudents() {
        return dao.count(new Query(), Student.class);
    }
}
