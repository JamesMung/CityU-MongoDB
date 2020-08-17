package com.cityu.mongodb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public abstract class AbstractDao {

    @Autowired
    protected MongoTemplate dao;

    public MongoTemplate getDao() {
        return dao;
    }

    public void setDao(MongoTemplate dao) {
        this.dao = dao;
    }
}
