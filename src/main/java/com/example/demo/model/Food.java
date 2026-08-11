package com.example.demo.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Food")
public class Food {
 @Id
private String foodId;
private String foodName;
private String quantity;
private String expiry;
private String userId;
private String status;
    public Food() {
    }

    public Food(String foodId, String foodName, String quantity, String expiry, String userId,String status) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.expiry = expiry;
        this.userId = userId;
        this.status = status;
    }

    // Getters and Setters
    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry ) {
        this.expiry = expiry;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
}
}

