package com.cityu.mongodb.dao;

import com.cityu.mongodb.model.User;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends AbstractDao{

    public User selectByUsernameAndPwd(String username, String password) {
        return dao.findOne(Query.query(Criteria.where("username").is(username).and("password").is(password)), User.class);
    }
}
