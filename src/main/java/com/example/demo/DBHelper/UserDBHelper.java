package com.example.demo.DBHelper;

import com.example.demo.model.User;
import com.example.demo.model.Food;
import com.example.demo.model.Transaction;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.FoodRepository;
import com.example.demo.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Repository
public class UserDBHelper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // =====================================================
    //                    BASIC CRUD
    // =====================================================

    // Get All Users
    public List<User> getAll() {
        return userRepository.findAll();
    }

    // Get User By ID
    public User getById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    // Add User
    public User add(User user) {
        return userRepository.save(user);
    }

    // Update User
    public User update(String id, User user) {
        user.setUserId(id);
        return userRepository.save(user);
    }

    // Delete User
    public boolean delete(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // =====================================================
    //                       LOGIN
    // =====================================================

    // Login using Email and Password
    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).orElse(null);
    }

    // =====================================================
    //                    SEARCH APIs
    // =====================================================

    // 1. Search User By Name
    public List<User> searchByName(String name) {
        return userRepository.findByUserNameContainingIgnoreCase(name);
    }

    // 2. Search User By Email
    public List<User> searchByEmail(String email) {
        return userRepository.findByEmailContainingIgnoreCase(email);
    }

    // 3. Search User By Mobile
    public List<User> searchByMobile(String mobile) {
        return userRepository.findByMobile(mobile);
    }

    // 4. Search User By Address
    public List<User> searchByAddress(String address) {
        return userRepository.findByAddressContainingIgnoreCase(address);
    }

    // =====================================================
    //                    USER FOOD API
    // =====================================================

    // 5. Get Food Added By User
    public List<Food> getUserFood(String userId) {
        return foodRepository.findByUserId(userId);
    }

    // =====================================================
    //                USER TRANSACTION API
    // =====================================================

    // 6. Get Transactions Of User
    public List<Transaction> getUserTransactions(String userId) {
        return transactionRepository.findByUserId(userId);
    }

    // =====================================================
    //                    USER DASHBOARD
    // =====================================================

    // 7. User Dashboard
    public Map<String, Object> getDashboard(String userId) {
        Map<String, Object> dashboard = new HashMap<>();

        List<Food> foods = foodRepository.findByUserId(userId);
        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        long totalFood = foods.size();
        long availableFood = foods.stream().filter(food -> "Available".equalsIgnoreCase(food.getStatus())).count();
        long donatedFood = foods.stream().filter(food -> "Donated".equalsIgnoreCase(food.getStatus())).count();
        long expiredFood = foods.stream().filter(food -> "Expired".equalsIgnoreCase(food.getStatus())).count();
        long totalTransactions = transactions.size();

        dashboard.put("userId", userId);
        dashboard.put("totalFoodAdded", totalFood);
        dashboard.put("availableFood", availableFood);
        dashboard.put("donatedFood", donatedFood);
        dashboard.put("expiredFood", expiredFood);
        dashboard.put("totalTransactions", totalTransactions);

        return dashboard;
    }

    // =====================================================
    //                 USER REGISTRATION
    // =====================================================

    public User registerUser(User user) {
        user.setStatus("Active");
        return userRepository.save(user);
    }

    // =====================================================
    //              CHECK USER EMAIL
    // =====================================================

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
