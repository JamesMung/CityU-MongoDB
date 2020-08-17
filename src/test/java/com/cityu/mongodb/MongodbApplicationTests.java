package com.cityu.mongodb;

import com.cityu.mongodb.constants.Constants;
import com.cityu.mongodb.dao.DeptDao;
import com.cityu.mongodb.model.Course;
import com.cityu.mongodb.model.User;
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
    private MongoTemplate dao;

    @Test
    void insert() {
        User u = new User();
        u.setId(0);
        u.setPassword(Constants.DEFAULT_PWD);
        u.setUsername("admin");
        u.setRole(Constants.UserRole.ADMIN);
        dao.insert(u);
    }

    @Test
    void find() {
        User u = dao.findOne(Query.query(Criteria.where("id").is(0)), User.class);
        System.out.println(u);
    }

    @Test
    void update() {
        dao.updateFirst(Query.query(Criteria.where("id").is(0)), Update.update("password", "P@ssw0rd"), User.class);
    }
}
