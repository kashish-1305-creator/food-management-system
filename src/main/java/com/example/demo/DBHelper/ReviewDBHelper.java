
package com.example.demo.DBHelper;

import com.example.demo.model.Review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDBHelper {

    @Autowired
    private MongoTemplate mongoTemplate;


    // =====================================================
    //                    BASIC CRUD
    // =====================================================


    // Get All Reviews
    // GET /reviews
    public List<Review> getAll() {

        return mongoTemplate.findAll(Review.class);
    }


    // Get Review By ID
    // GET /reviews/{id}
    public Review getById(String id) {

        return mongoTemplate.findById(id, Review.class);
    }


    // Add Review
    // POST /reviews
    public Review add(Review review) {

        return mongoTemplate.save(review);
    }


    // Update Review
    // PUT /reviews/{id}
    public Review update(String id, Review review) {

        review.setReviewId(id);

        return mongoTemplate.save(review);
    }


    // Delete Review
    // DELETE /reviews/{id}
    public boolean delete(String id) {

        Review review =
                mongoTemplate.findById(id, Review.class);

        if (review != null) {

            mongoTemplate.remove(review);

            return true;
        }

        return false;
    }


    // =====================================================
    //                    SEARCH APIs
    // =====================================================


    // 1. Get Reviews By NGO
    //
    // GET /reviews/ngo/N101
    //
    public List<Review> getReviewsByNGO(String ngoId) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("ngoId")
                        .is(ngoId)
        );

        return mongoTemplate.find(query, Review.class);
    }


    // 2. Get Reviews By User
    //
    // GET /reviews/user/U101
    //
    public List<Review> getReviewsByUser(String userId) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("userId")
                        .is(userId)
        );

        return mongoTemplate.find(query, Review.class);
    }


    // 3. Get Reviews By Rating
    //
    // GET /reviews/rating/5
    //
    public List<Review> getReviewsByRating(int rating) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("rating")
                        .is(rating)
        );

        return mongoTemplate.find(query, Review.class);
    }


    // =====================================================
    //                    RATING APIs
    // =====================================================


    // 4. Get Average Rating Of NGO
    //
    // GET /reviews/average/N101
    //
    public double getAverageRating(String ngoId) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("ngoId")
                        .is(ngoId)
        );

        List<Review> reviews =
                mongoTemplate.find(query, Review.class);


        // No reviews
        if (reviews.isEmpty()) {
            return 0.0;
        }


        double totalRating = 0;


        for (Review review : reviews) {

            totalRating =
                    totalRating + review.getRating();
        }


        return totalRating / reviews.size();
    }


    // 5. Get Top Rated Reviews
    //
    // GET /reviews/top-rated
    //
    public List<Review> getTopRated() {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("rating")
                        .gte(4)
        );

        return mongoTemplate.find(query, Review.class);
    }
}

