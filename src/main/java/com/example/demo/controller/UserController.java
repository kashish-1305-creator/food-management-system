package com.example.demo.controller;

import com.example.demo.DBHelper.UserDBHelper;
import com.example.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDBHelper db;


    // =====================================================
    //                    BASIC CRUD APIs
    // =====================================================


    // Get All Users
    // GET /users
    @GetMapping
    public List<User> getAll() {

        return db.getAll();
    }


    // Get User By ID
    // GET /users/U101
    @GetMapping("/{id}")
    public User getById(
            @PathVariable("id") String id) {

        return db.getById(id);
    }


    // =====================================================
    //                  USER REGISTRATION
    // =====================================================


    // Register User
    //
    // POST /users/register
    //
    // New User automatically gets:
    //
    // status = Active
    //
    @PostMapping("/register")
    public Object register(
            @RequestBody User user) {


        // Check duplicate email
        if (db.emailExists(user.getEmail())) {

            return "Email already registered";
        }


        // Register user
        return db.registerUser(user);
    }


    // =====================================================
    //                    ADD USER
    // =====================================================


    // Add User
    // POST /users
    //
    // This is basic CRUD.
    // For registration, use /users/register.
    //
    @PostMapping
    public User add(
            @RequestBody User user) {

        return db.add(user);
    }


    // =====================================================
    //                    UPDATE USER
    // =====================================================


    // Update User
    // PUT /users/U101
    @PutMapping("/{id}")
    public User update(
            @PathVariable("id") String id,
            @RequestBody User user) {

        return db.update(id, user);
    }


    // =====================================================
    //                    DELETE USER
    // =====================================================


    // Delete User
    // DELETE /users/U101
    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable("id") String id) {

        if (db.delete(id)) {

            return "User Deleted Successfully";
        }

        return "User Not Found";
    }


    // =====================================================
    //                       LOGIN
    // =====================================================


    // Login
    //
    // POST /users/login
    //
    @PostMapping("/login")
    public Object login(
            @RequestBody User user) {


        User loginUser =
                db.login(
                        user.getEmail(),
                        user.getPassword()
                );


        // Email/password incorrect
        if (loginUser == null) {

            return "Invalid Email or Password";
        }


        // User account must be Active
        if (!"Active".equalsIgnoreCase(
                loginUser.getStatus())) {

            return "User Account is not Active";
        }


        return loginUser;
    }


    // =====================================================
    //                    SEARCH APIs
    // =====================================================


    // 1. Search User By Name
    //
    // GET /users/search/name/Isha
    //
    @GetMapping("/search/name/{name}")
    public List<User> searchByName(
            @PathVariable("name") String name) {

        return db.searchByName(name);
    }


    // 2. Search User By Email
    //
    // GET /users/search/email/isha@gmail.com
    //
    @GetMapping("/search/email/{email}")
    public List<User> searchByEmail(
            @PathVariable("email") String email) {

        return db.searchByEmail(email);
    }


    // 3. Search User By Mobile
    //
    // GET /users/search/mobile/9876543210
    //
    @GetMapping("/search/mobile/{mobile}")
    public List<User> searchByMobile(
            @PathVariable("mobile") String mobile) {

        return db.searchByMobile(mobile);
    }


    // 4. Search User By Address
    //
    // GET /users/search/address/Pune
    //
    @GetMapping("/search/address/{address}")
    public List<User> searchByAddress(
            @PathVariable("address") String address) {

        return db.searchByAddress(address);
    }


    // =====================================================
    //                    USER FOOD APIs
    // =====================================================


    // 5. Get Food Added By User
    //
    // GET /users/U101/food
    //
    @GetMapping("/{id}/food")
    public Object getUserFood(
            @PathVariable("id") String id) {

        return db.getUserFood(id);
    }


    // =====================================================
    //                 USER TRANSACTION APIs
    // =====================================================


    // 6. Get User Transactions
    //
    // GET /users/U101/transactions
    //
    @GetMapping("/{id}/transactions")
    public Object getUserTransactions(
            @PathVariable("id") String id) {

        return db.getUserTransactions(id);
    }


    // =====================================================
    //                  USER DASHBOARD
    // =====================================================


    // 7. User Dashboard
    //
    // GET /users/U101/dashboard
    //
    @GetMapping("/{id}/dashboard")
    public Map<String, Object> getDashboard(
            @PathVariable("id") String id) {

        return db.getDashboard(id);
    }
}
