package com.cityu.mongodb;

import com.cityu.mongodb.constants.Constants;
import com.cityu.mongodb.dao.DeptDao;
import com.cityu.mongodb.dao.StudentDao;
import com.cityu.mongodb.model.Course;
import com.cityu.mongodb.model.Student;
import com.cityu.mongodb.model.User;
import me.xdrop.jrand.JRand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

@SpringBootTest
class MongodbApplicationTests {

    @Autowired
    private StudentDao studentDao;

    private static final int STUDENT_COUNT = 1000;
    private static final String[] DEPT_LIST = {"BE", "CS", "EE", "MSE", "ME", "SEEM", "BST"};
    private static final String[] DEPT_NAME_LIST = {"Biomedical Engineering", "Computer Science", "Electrical Engineering", "Materials Science and Engineering", "Mechanical Engineering", "Systems Engineering and Engineering Management", "Building Science and Technology"};
    private static final String[] LOCATION_LIST = {"Yeung Kin Man Academic Building", "Yeung Kin Man Academic Building", ""};

    @Test
    void insert() {
        generateStudent();
    }

    private void generateStudent() {
        int stuId = 15100001;

        for (int i = 0; i < STUDENT_COUNT; i++) {
            Student student = new Student();
            student.setStuName(JRand.name().gen());
            student.setDOB(JRand.birthday().gen());
            student.setStuId(String.valueOf(stuId));
            studentDao.addStudent(student);
        }
    }

    private void generateDept() {

    }
}
