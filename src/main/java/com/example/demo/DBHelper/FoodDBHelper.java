package com.example.demo.DBHelper;

import com.example.demo.model.Food;
import com.example.demo.repository.FoodRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FoodDBHelper {

    @Autowired
    private FoodRepository foodRepository;

    // =====================================================
    //                    BASIC CRUD
    // =====================================================

    // Get All Food
    public List<Food> getAll() {
        return foodRepository.findAll();
    }

    // Get Food By ID
    public Food getById(String id) {
        return foodRepository.findById(id).orElse(null);
    }

    // Add Food
    public Food add(Food food) {
        return foodRepository.save(food);
    }

    // Update Food
    public Food update(String id, Food food) {
        food.setFoodId(id);
        return foodRepository.save(food);
    }

    // Delete Food
    public boolean delete(String id) {
        if (foodRepository.existsById(id)) {
            foodRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // =====================================================
    //                    SEARCH APIs
    // =====================================================

    // 1. Search Food By Food Name
    public List<Food> searchByFoodName(String foodName) {
        return foodRepository.findByFoodNameContainingIgnoreCase(foodName);
    }

    // 2. Search Food By Status
    public List<Food> searchByStatus(String status) {
        return foodRepository.findByStatusIgnoreCase(status);
    }

    // 3. Search Food By User ID
    public List<Food> searchByUser(String userId) {
        return foodRepository.findByUserId(userId);
    }

    // =====================================================
    //                    FILTER APIs
    // =====================================================

    // 4. Filter Food By Quantity
    public List<Food> filterByQuantity(String min, String max) {
        List<Food> all = foodRepository.findAll();
        return all.stream().filter(f -> {
            if (f.getQuantity() == null) return false;
            try {
                double qty = Double.parseDouble(f.getQuantity());
                if (min != null && qty < Double.parseDouble(min)) return false;
                if (max != null && qty > Double.parseDouble(max)) return false;
                return true;
            } catch (NumberFormatException e) {
                return true;
            }
        }).toList();
    }

    // 5. Filter Food By Expiry Date
    public List<Food> filterByExpiry(String date) {
        return foodRepository.findByExpiryContainingIgnoreCase(date);
    }

    public Food updateStatus(String foodId, String status) {
        Food food = foodRepository.findById(foodId).orElse(null);
        if (food != null) {
            food.setStatus(status);
            return foodRepository.save(food);
        }
        return null;
    }
}
