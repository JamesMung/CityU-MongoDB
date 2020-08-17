package com.cityu.mongodb.service;

import com.cityu.mongodb.dao.UserDao;
import com.cityu.mongodb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserDao userDao;

    public User login(String username, String password) {
       return userDao.selectByUsernameAndPwd(username, password);
    }

    public UserDao getUserDao() {
        return userDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
