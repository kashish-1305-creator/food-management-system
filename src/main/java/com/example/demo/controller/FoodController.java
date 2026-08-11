package com.example.demo.controller;

import com.example.demo.DBHelper.FoodDBHelper;
import com.example.demo.model.Food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodDBHelper db;

    // ================================
    // BASIC CRUD APIs
    // ================================

    // Get All Food
    @GetMapping
    public List<Food> getAll() {
        return db.getAll();
    }

    // Get Food By ID
    @GetMapping("/{id}")
    public Food getById(@PathVariable("id") String id) {
        return db.getById(id);
    }

    // Add Food
    @PostMapping
    public Food add(@RequestBody Food food) {

        System.out.println("FoodId   : " + food.getFoodId());
        System.out.println("FoodName : " + food.getFoodName());
        System.out.println("UserId   : " + food.getUserId());
        System.out.println("Status   : " + food.getStatus());

        return db.add(food);
    }

    // Update Food
    @PutMapping("/{id}")
    public Food update(@PathVariable("id") String id,
                       @RequestBody Food food) {

        return db.update(id, food);
    }

    // Delete Food
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {

        if (db.delete(id))
            return "Food Deleted Successfully";

        return "Food Not Found";
    }


    // =================================================
    // SEARCH & FILTER APIs
    // =================================================


    // 1. SEARCH BY FOOD NAME
    //
    // GET /food/search/name/Rice
    //
    @GetMapping("/search/name/{foodName}")
    public List<Food> searchByFoodName(
            @PathVariable("foodName") String foodName) {

        return db.searchByFoodName(foodName);
    }


    // 2. SEARCH BY STATUS
    //
    // GET /food/search/status/Available
    //
    @GetMapping("/search/status/{status}")
    public List<Food> searchByStatus(
            @PathVariable("status") String status) {

        return db.searchByStatus(status);
    }


    // 3. SEARCH BY USER ID
    //
    // GET /food/search/user/U101
    //
    @GetMapping("/search/user/{userId}")
    public List<Food> searchByUser(
            @PathVariable("userId") String userId) {

        return db.searchByUser(userId);
    }


    // 4. FILTER BY QUANTITY
    //
    // GET /food/filter/quantity?min=10&max=50
    //
    // You can also use:
    // /food/filter/quantity?min=10
    // /food/filter/quantity?max=50
    //
    @GetMapping("/filter/quantity")
    public List<Food> filterByQuantity(
            @RequestParam(value = "min", required = false) String min,
            @RequestParam(value = "max", required = false) String max) {

        return db.filterByQuantity(min, max);
    }


    // 5. FILTER BY EXPIRY DATE
    //
    // GET /food/filter/expiry/2026-08-15
    //
    @GetMapping("/filter/expiry/{date}")
    public List<Food> filterByExpiry(
            @PathVariable("date") String date) {

        return db.filterByExpiry(date);
    }
}