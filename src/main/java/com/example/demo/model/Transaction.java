package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    private String transactionId;

    private String userId;
    private String foodId;
    private String ngoId;

    // Pending, Accepted, Rejected, Cancelled, Completed
    private String status;

    private String date;

    // Additional fields for request/donation tracking
    private String requestedAt;
    private String acceptedAt;
    private String cancelledAt;
    private String completedAt;

    private String cancellationReason;
    private String cancelledBy;


    // Default Constructor
    public Transaction() {
    }


    // Parameterized Constructor
    public Transaction(String transactionId,
                       String userId,
                       String foodId,
                       String ngoId,
                       String status,
                       String date,
                       String requestedAt,
                       String acceptedAt,
                       String cancelledAt,
                       String completedAt,
                       String cancellationReason,
                       String cancelledBy) {

        this.transactionId = transactionId;
        this.userId = userId;
        this.foodId = foodId;
        this.ngoId = ngoId;
        this.status = status;
        this.date = date;

        this.requestedAt = requestedAt;
        this.acceptedAt = acceptedAt;
        this.cancelledAt = cancelledAt;
        this.completedAt = completedAt;

        this.cancellationReason = cancellationReason;
        this.cancelledBy = cancelledBy;
    }


    // Getters & Setters

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }


    public String getNgoId() {
        return ngoId;
    }

    public void setNgoId(String ngoId) {
        this.ngoId = ngoId;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(String requestedAt) {
        this.requestedAt = requestedAt;
    }


    public String getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(String acceptedAt) {
        this.acceptedAt = acceptedAt;
    }


    public String getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(String cancelledAt) {
        this.cancelledAt = cancelledAt;
    }


    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }


    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }


    public String getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(String cancelledBy) {
        this.cancelledBy = cancelledBy;
    }
}