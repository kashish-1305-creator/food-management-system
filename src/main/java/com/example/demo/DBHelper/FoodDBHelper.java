package com.example.demo.DBHelper;

import com.example.demo.model.Food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FoodDBHelper {

    @Autowired
    private MongoTemplate mongoTemplate;


    // =====================================================
    //                    BASIC CRUD
    // =====================================================


    // Get All Food
    public List<Food> getAll() {

        return mongoTemplate.findAll(Food.class);
    }


    // Get Food By ID
    public Food getById(String id) {

        return mongoTemplate.findById(id, Food.class);
    }


    // Add Food
    public Food add(Food food) {

        return mongoTemplate.save(food);
    }


    // Update Food
    public Food update(String id, Food food) {

        food.setFoodId(id);

        return mongoTemplate.save(food);
    }


    // Delete Food
    public boolean delete(String id) {

        Food food = mongoTemplate.findById(id, Food.class);

        if (food != null) {

            mongoTemplate.remove(food);

            return true;
        }

        return false;
    }


    // =====================================================
    //                    SEARCH APIs
    // =====================================================


    // 1. Search Food By Food Name
    //
    // Example:
    // GET /food/search/name/Rice
    //
    // "Rice", "rice", "RICE" will all work.
    //
    public List<Food> searchByFoodName(String foodName) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("foodName")
                        .regex(foodName, "i")
        );

        return mongoTemplate.find(query, Food.class);
    }


    // 2. Search Food By Status
    //
    // Example:
    // GET /food/search/status/Available
    //
    public List<Food> searchByStatus(String status) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("status")
                        .is(status)
        );

        return mongoTemplate.find(query, Food.class);
    }


    // 3. Search Food By User ID
    //
    // Example:
    // GET /food/search/user/U101
    //
    public List<Food> searchByUser(String userId) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("userId")
                        .is(userId)
        );

        return mongoTemplate.find(query, Food.class);
    }


    // =====================================================
    //                    FILTER APIs
    // =====================================================


    // 4. Filter Food By Quantity
    //
    // Examples:
    //
    // /food/filter/quantity?min=10&max=50
    //
    // /food/filter/quantity?min=10
    //
    // /food/filter/quantity?max=50
    //
    public List<Food> filterByQuantity(String min, String max) {

        Query query = new Query();

        // Minimum and Maximum both provided
        if (min != null && max != null) {

            query.addCriteria(
                    Criteria.where("quantity")
                            .gte(min)
                            .lte(max)
            );
        }

        // Only Minimum provided
        else if (min != null) {

            query.addCriteria(
                    Criteria.where("quantity")
                            .gte(min)
            );
        }

        // Only Maximum provided
        else if (max != null) {

            query.addCriteria(
                    Criteria.where("quantity")
                            .lte(max)
            );
        }

        return mongoTemplate.find(query, Food.class);
    }


    // 5. Filter Food By Expiry Date
    //
    // Example:
    // GET /food/filter/expiry/2026-08-15
    //
    public List<Food> filterByExpiry(String date) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("expiry")
                        .is(date)
        );

        return mongoTemplate.find(query, Food.class);
    }
    public Food updateStatus(String foodId, String status) {

    Food food = mongoTemplate.findById(
            foodId,
            Food.class
    );

    if (food != null) {

        food.setStatus(status);

        return mongoTemplate.save(food);
    }

    return null;
}
}
