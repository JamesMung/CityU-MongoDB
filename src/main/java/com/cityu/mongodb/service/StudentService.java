package com.cityu.mongodb.service;

import com.cityu.mongodb.dao.StudentDao;
import com.cityu.mongodb.model.Student;
import com.cityu.mongodb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private StudentDao studentDao;

    public List<Student> findStudentList() {
        return studentDao.findAllStudents();
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

    @Autowired
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public void addStudent(Student student) {
        studentDao.addStudent(student);
    }

    public boolean enrollCourse(User u, String courseId, Integer year) {
        if(u.getStudent() == null)
            return false;

        return studentDao.enrollCourse(u, courseId, year);
    }

    public boolean unEnrollCourse(User u, String courseId, Integer year) {
        if(u.getStudent() == null)
            return false;

        return studentDao.unEnrollCourse(u, courseId, year);
    }

    public void update(Student student) {
        studentDao.update(student);
    }

    public void delete(String stuId) {
        studentDao.delete(stuId);
    }
}
