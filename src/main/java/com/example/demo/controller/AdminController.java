package com.example.demo.controller;

import com.example.demo.DBHelper.AdminDBHelper;
import com.example.demo.model.Admin;
import com.example.demo.model.NGO;
import com.example.demo.model.User;
import com.example.demo.model.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminDBHelper db;


    // =====================================================
    //                       LOGIN
    // =====================================================


    // Admin Login
    //
    // POST /admin/login
    //
    @PostMapping("/login")
    public Object login(
            @RequestBody Admin admin) {

        Admin loginAdmin =
                db.login(
                        admin.getEmail(),
                        admin.getPassword()
                );

        if (loginAdmin != null) {

            return loginAdmin;
        }

        return "Invalid Admin Email or Password";
    }


    // =====================================================
    //                    ADMIN BY ID
    // =====================================================


    // GET /admin/A101
    @GetMapping("/{id}")
    public Object getById(
            @PathVariable("id") String id) {

        Admin admin = db.getById(id);

        if (admin != null) {

            return admin;
        }

        return "Admin Not Found";
    }


    // =====================================================
    //                    NGO MANAGEMENT
    // =====================================================


    // Get All NGOs
    //
    // GET /admin/ngos
    //
    @GetMapping("/ngos")
    public List<NGO> getAllNGOs() {

        return db.getAllNGOs();
    }


    // Get Pending NGOs
    //
    // GET /admin/ngos/pending
    //
    @GetMapping("/ngos/pending")
    public List<NGO> getPendingNGOs() {

        return db.getPendingNGOs();
    }


    // Approve NGO
    //
    // PUT /admin/ngos/N101/approve
    //
    @PutMapping("/ngos/{id}/approve")
    public Object approveNGO(
            @PathVariable("id") String id) {

        NGO ngo = db.approveNGO(id);

        if (ngo != null) {

            return ngo;
        }

        return "NGO Not Found";
    }


    // Reject NGO
    //
    // PUT /admin/ngos/N101/reject
    //
    @PutMapping("/ngos/{id}/reject")
    public Object rejectNGO(
            @PathVariable("id") String id) {

        NGO ngo = db.rejectNGO(id);

        if (ngo != null) {

            return ngo;
        }

        return "NGO Not Found";
    }


    // Block NGO
    //
    // PUT /admin/ngos/N101/block
    //
    @PutMapping("/ngos/{id}/block")
    public Object blockNGO(
            @PathVariable("id") String id) {

        NGO ngo = db.blockNGO(id);

        if (ngo != null) {

            return ngo;
        }

        return "NGO Not Found";
    }


    // Unblock NGO
    //
    // PUT /admin/ngos/N101/unblock
    //
    @PutMapping("/ngos/{id}/unblock")
    public Object unblockNGO(
            @PathVariable("id") String id) {

        NGO ngo = db.unblockNGO(id);

        if (ngo != null) {

            return ngo;
        }

        return "NGO Not Found";
    }


    // =====================================================
    //                    USER MANAGEMENT
    // =====================================================


    // Get All Users
    //
    // GET /admin/users
    //
    @GetMapping("/users")
    public List<User> getAllUsers() {

        return db.getAllUsers();
    }


    // Get User By ID
    //
    // GET /admin/users/U101
    //
    @GetMapping("/users/{id}")
    public Object getUserById(
            @PathVariable("id") String id) {

        User user = db.getUserById(id);

        if (user != null) {

            return user;
        }

        return "User Not Found";
    }


    // =====================================================
    //                 TRANSACTION MANAGEMENT
    // =====================================================


    // Get All Transactions
    //
    // GET /admin/transactions
    //
    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {

        return db.getAllTransactions();
    }


    // Get Transactions By Status
    //
    // GET /admin/transactions/status/Pending
    //
    @GetMapping("/transactions/status/{status}")
    public List<Transaction> getTransactionsByStatus(
            @PathVariable("status") String status) {

        return db.getTransactionsByStatus(status);
    }


    // =====================================================
    //                    ADMIN DASHBOARD
    // =====================================================


    // GET /admin/dashboard
    //
    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {

        Map<String, Object> dashboard =
                new HashMap<>();


        dashboard.put(
                "totalUsers",
                db.getTotalUsers()
        );


        dashboard.put(
                "totalNGOs",
                db.getTotalNGOs()
        );


        dashboard.put(
                "pendingNGOs",
                db.getPendingNGOsCount()
        );


        dashboard.put(
                "approvedNGOs",
                db.getApprovedNGOsCount()
        );


        dashboard.put(
                "totalTransactions",
                db.getTotalTransactions()
        );


        return dashboard;
    }
}

