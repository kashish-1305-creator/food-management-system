package com.example.demo.DBHelper;

import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewDBHelper {

    @Autowired
    private ReviewRepository reviewRepository;

    // =====================================================
    //                    BASIC CRUD
    // =====================================================

    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    public Review getById(String id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public Review add(Review review) {
        return reviewRepository.save(review);
    }

    public Review update(String id, Review review) {
        review.setReviewId(id);
        return reviewRepository.save(review);
    }

    public boolean delete(String id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // =====================================================
    //                    SEARCH APIs
    // =====================================================

    public List<Review> getReviewsByNGO(String ngoId) {
        return reviewRepository.findByNgoId(ngoId);
    }

    public List<Review> getReviewsByUser(String userId) {
        return reviewRepository.findByUserId(userId);
    }

    public List<Review> getReviewsByRating(int rating) {
        return reviewRepository.findAll().stream().filter(r -> r.getRating() == rating).toList();
    }

    // =====================================================
    //                    RATING APIs
    // =====================================================

    public double getAverageRating(String ngoId) {
        List<Review> reviews = reviewRepository.findByNgoId(ngoId);

        if (reviews.isEmpty()) {
            return 0.0;
        }

        double totalRating = 0;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }

        return totalRating / reviews.size();
    }

    public List<Review> getTopRated() {
        return reviewRepository.findAll().stream().filter(r -> r.getRating() >= 4).toList();
    }
}
