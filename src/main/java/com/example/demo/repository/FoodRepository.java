package com.example.demo.repository;

import com.example.demo.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, String> {

    List<Food> findByUserId(String userId);

    List<Food> findByFoodNameContainingIgnoreCase(String foodName);

    List<Food> findByQuantity(String quantity);

    List<Food> findByStatusIgnoreCase(String status);

    List<Food> findByExpiryContainingIgnoreCase(String expiry);
}
