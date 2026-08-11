package com.example.demo.DBHelper;

import com.example.demo.model.User;
import com.example.demo.model.Food;
import com.example.demo.model.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Repository
public class UserDBHelper {

    @Autowired
    private MongoTemplate mongoTemplate;


    // =====================================================
    //                    BASIC CRUD
    // =====================================================

    // Get All Users
    public List<User> getAll() {

        return mongoTemplate.findAll(User.class);
    }


    // Get User By ID
    public User getById(String id) {

        return mongoTemplate.findById(id, User.class);
    }


    // Add User
    public User add(User user) {

        return mongoTemplate.save(user);
    }


    // Update User
    public User update(String id, User user) {

        user.setUserId(id);

        return mongoTemplate.save(user);
    }


    // Delete User
    public boolean delete(String id) {

        User user = mongoTemplate.findById(id, User.class);

        if (user != null) {

            mongoTemplate.remove(user);

            return true;
        }

        return false;
    }


    // =====================================================
    //                       LOGIN
    // =====================================================

    // Login using Email and Password
    public User login(String email, String password) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("email").is(email)
                        .and("password").is(password)
        );

        return mongoTemplate.findOne(query, User.class);
    }


    // =====================================================
    //                    SEARCH APIs
    // =====================================================


    // 1. Search User By Name
    //
    // Example:
    // GET /users/search/name/Isha
    //
    // "Isha", "isha", "ISHA" will work.
    //
    public List<User> searchByName(String name) {

    Query query = new Query();

    query.addCriteria(
            Criteria.where("userName")
                    .regex(name, "i")
    );

    return mongoTemplate.find(query, User.class);
}


    // 2. Search User By Email
    //
    // Example:
    // GET /users/search/email/isha@gmail.com
    //
    public List<User> searchByEmail(String email) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("email")
                        .regex(email, "i")
        );

        return mongoTemplate.find(query, User.class);
    }


    // 3. Search User By Mobile
    //
    // Example:
    // GET /users/search/mobile/9876543210
    //
    public List<User> searchByMobile(String mobile) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("mobile")
                        .is(mobile)
        );

        return mongoTemplate.find(query, User.class);
    }


    // 4. Search User By Address
    //
    // Example:
    // GET /users/search/address/Pune
    //
    public List<User> searchByAddress(String address) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("address")
                        .regex(address, "i")
        );

        return mongoTemplate.find(query, User.class);
    }


    // =====================================================
    //                    USER FOOD API
    // =====================================================


    // 5. Get Food Added By User
    //
    // Example:
    // GET /users/U101/food
    //
    public List<Food> getUserFood(String userId) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("userId")
                        .is(userId)
        );

        return mongoTemplate.find(query, Food.class);
    }


    // =====================================================
    //                USER TRANSACTION API
    // =====================================================


    // 6. Get Transactions Of User
    //
    // Example:
    // GET /users/U101/transactions
    //
    public List<Transaction> getUserTransactions(String userId) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("userId")
                        .is(userId)
        );

        return mongoTemplate.find(query, Transaction.class);
    }


    // =====================================================
    //                    USER DASHBOARD
    // =====================================================


    // 7. User Dashboard
    //
    // Example:
    // GET /users/U101/dashboard
    //
    public Map<String, Object> getDashboard(String userId) {

        Map<String, Object> dashboard = new HashMap<>();


        // Find all food added by user
        Query foodQuery = new Query();

        foodQuery.addCriteria(
                Criteria.where("userId")
                        .is(userId)
        );

        List<Food> foods =
                mongoTemplate.find(foodQuery, Food.class);


        // Find all transactions of user
        Query transactionQuery = new Query();

        transactionQuery.addCriteria(
                Criteria.where("userId")
                        .is(userId)
        );

        List<Transaction> transactions =
                mongoTemplate.find(
                        transactionQuery,
                        Transaction.class
                );


        // Total Food Added
        long totalFood = foods.size();


        // Available Food
        long availableFood = foods.stream()
                .filter(food ->
                        "Available".equalsIgnoreCase(
                                food.getStatus()
                        )
                )
                .count();


        // Donated Food
        long donatedFood = foods.stream()
                .filter(food ->
                        "Donated".equalsIgnoreCase(
                                food.getStatus()
                        )
                )
                .count();


        // Expired Food
        long expiredFood = foods.stream()
                .filter(food ->
                        "Expired".equalsIgnoreCase(
                                food.getStatus()
                        )
                )
                .count();


        // Total Transactions
        long totalTransactions =
                transactions.size();


        // Add data to dashboard
        dashboard.put(
                "userId",
                userId
        );

        dashboard.put(
                "totalFoodAdded",
                totalFood
        );

        dashboard.put(
                "availableFood",
                availableFood
        );

        dashboard.put(
                "donatedFood",
                donatedFood
        );

        dashboard.put(
                "expiredFood",
                expiredFood
        );

        dashboard.put(
                "totalTransactions",
                totalTransactions
        );


        return dashboard;
    }
// =====================================================
//                 USER REGISTRATION
// =====================================================

// Register User
//
// New user will automatically get:
// status = Active
//
public User registerUser(User user) {

    user.setStatus("Active");

    return mongoTemplate.save(user);
}


// =====================================================
//              CHECK USER EMAIL
// =====================================================

// Check whether email already exists
//
public boolean emailExists(String email) {

    Query query = new Query();

    query.addCriteria(
            Criteria.where("email")
                    .is(email)
    );

    return mongoTemplate.exists(
            query,
            User.class
    );
}
}
