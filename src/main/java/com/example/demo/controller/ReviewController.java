package com.example.demo.controller;

import com.example.demo.DBHelper.ReviewDBHelper;
import com.example.demo.model.Review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewDBHelper db;


    // =====================================================
    //                    BASIC CRUD APIs
    // =====================================================


    // Get All Reviews
    // GET /reviews
    @GetMapping
    public List<Review> getAll() {
        return db.getAll();
    }


    // Get Review By ID
    // GET /reviews/R101
    @GetMapping("/{id}")
    public Review getById(@PathVariable("id") String id) {
        return db.getById(id);
    }


    // Add Review
    // POST /reviews
    @PostMapping
    public Review add(@RequestBody Review review) {
        return db.add(review);
    }


    // Update Review
    // PUT /reviews/R101
    @PutMapping("/{id}")
    public Review update(@PathVariable("id") String id,
                         @RequestBody Review review) {

        return db.update(id, review);
    }


    // Delete Review
    // DELETE /reviews/R101
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {

        if (db.delete(id))
            return "Review Deleted Successfully";

        return "Review Not Found";
    }


    // =====================================================
    //                    SEARCH APIs
    // =====================================================


    // 1. Get Reviews By NGO
    //
    // GET /reviews/ngo/N101
    //
    @GetMapping("/ngo/{ngoId}")
    public List<Review> getReviewsByNGO(
            @PathVariable("ngoId") String ngoId) {

        return db.getReviewsByNGO(ngoId);
    }


    // 2. Get Reviews By User
    //
    // GET /reviews/user/U101
    //
    @GetMapping("/user/{userId}")
    public List<Review> getReviewsByUser(
            @PathVariable("userId") String userId) {

        return db.getReviewsByUser(userId);
    }


    // 3. Get Reviews By Rating
    //
    // GET /reviews/rating/5
    //
    @GetMapping("/rating/{rating}")
    public List<Review> getReviewsByRating(
            @PathVariable("rating") int rating) {

        return db.getReviewsByRating(rating);
    }


    // =====================================================
    //                    RATING APIs
    // =====================================================


    // 4. Get Average Rating Of NGO
    //
    // GET /reviews/average/N101
    //
    @GetMapping("/average/{ngoId}")
    public double getAverageRating(
            @PathVariable("ngoId") String ngoId) {

        return db.getAverageRating(ngoId);
    }


    // 5. Get Top Rated Reviews
    //
    // GET /reviews/top-rated
    //
    @GetMapping("/top-rated")
    public List<Review> getTopRated() {

        return db.getTopRated();
    }
}

