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

    public boolean enrollCourse(Student student, String courseId, Integer year) {
        return studentDao.enrollCourse(student, courseId, year);
    }

    public boolean unEnrollCourse(Student student, String courseId, Integer year) {
        return studentDao.unEnrollCourse(student, courseId, year);
    }

    public void update(Student student) {
        studentDao.update(student);
    }

    public void delete(String stuId) {
        studentDao.delete(stuId);
    }
}
