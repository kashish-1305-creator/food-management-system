package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Review")
public class Review {

    @Id
    private String reviewId;
    private String userId;
    private String ngoId;
    private String comment;
    private int rating;

    public Review() {}

    public Review(String reviewId, String userId, String ngoId,
                  String comment, int rating) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.ngoId = ngoId;
        this.comment = comment;
        this.rating = rating;
    }

    // Getters & Setters
    public String getReviewId() { return reviewId; }
    public void setReviewId(String reviewId) { this.reviewId = reviewId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getNgoId() { return ngoId; }
    public void setNgoId(String ngoId) { this.ngoId = ngoId; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
}