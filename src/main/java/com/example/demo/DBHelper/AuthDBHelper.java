package com.example.demo.DBHelper;

import com.example.demo.model.User;
import com.example.demo.model.NGO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class AuthDBHelper {

    @Autowired
    private MongoTemplate mongoTemplate;

    public User userLogin(String email,String password){

        Query query = new Query();

        query.addCriteria(
                Criteria.where("email").is(email)
                        .and("password").is(password));

        return mongoTemplate.findOne(query,User.class);
    }

    public NGO ngoLogin(String email,String password){

        Query query = new Query();

        query.addCriteria(
                Criteria.where("email").is(email)
                        .and("password").is(password));

        return mongoTemplate.findOne(query,NGO.class);
    }
}
