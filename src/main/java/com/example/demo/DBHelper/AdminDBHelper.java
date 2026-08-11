package com.example.demo.DBHelper;

import com.example.demo.model.Admin;
import com.example.demo.model.NGO;
import com.example.demo.model.User;
import com.example.demo.model.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDBHelper {

    @Autowired
    private MongoTemplate mongoTemplate;


    // =====================================================
    //                    ADMIN LOGIN
    // =====================================================

    // Login using Email and Password
    //
    // POST /admin/login
    //
    public Admin login(String email, String password) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("email").is(email)
                        .and("password").is(password)
        );

        return mongoTemplate.findOne(
                query,
                Admin.class
        );
    }


    // =====================================================
    //                    ADMIN BY ID
    // =====================================================

    // Get Admin By ID
    public Admin getById(String id) {

        return mongoTemplate.findById(
                id,
                Admin.class
        );
    }


    // =====================================================
    //                    NGO MANAGEMENT
    // =====================================================

    // Get All NGOs
    //
    // GET /admin/ngos
    //
    public List<NGO> getAllNGOs() {

        return mongoTemplate.findAll(NGO.class);
    }


    // Get Pending NGOs
    //
    // GET /admin/ngos/pending
    //
    public List<NGO> getPendingNGOs() {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("status")
                        .is("Pending")
        );

        return mongoTemplate.find(
                query,
                NGO.class
        );
    }


    // Approve NGO
    //
    // Pending -> Approved
    //
    public NGO approveNGO(String ngoId) {

        NGO ngo =
                mongoTemplate.findById(
                        ngoId,
                        NGO.class
                );

        if (ngo != null) {

            ngo.setStatus("Approved");

            return mongoTemplate.save(ngo);
        }

        return null;
    }


    // Reject NGO
    //
    // Pending -> Rejected
    //
    public NGO rejectNGO(String ngoId) {

        NGO ngo =
                mongoTemplate.findById(
                        ngoId,
                        NGO.class
                );

        if (ngo != null) {

            ngo.setStatus("Rejected");

            return mongoTemplate.save(ngo);
        }

        return null;
    }


    // Block NGO
    //
    // Approved -> Blocked
    //
    public NGO blockNGO(String ngoId) {

        NGO ngo =
                mongoTemplate.findById(
                        ngoId,
                        NGO.class
                );

        if (ngo != null) {

            ngo.setStatus("Blocked");

            return mongoTemplate.save(ngo);
        }

        return null;
    }


    // Unblock NGO
    //
    // Blocked -> Approved
    //
    public NGO unblockNGO(String ngoId) {

        NGO ngo =
                mongoTemplate.findById(
                        ngoId,
                        NGO.class
                );

        if (ngo != null) {

            ngo.setStatus("Approved");

            return mongoTemplate.save(ngo);
        }

        return null;
    }


    // =====================================================
    //                    USER MANAGEMENT
    // =====================================================

    // Get All Users
    //
    // GET /admin/users
    //
    public List<User> getAllUsers() {

        return mongoTemplate.findAll(User.class);
    }


    // Get User By ID
    public User getUserById(String userId) {

        return mongoTemplate.findById(
                userId,
                User.class
        );
    }


    // =====================================================
    //                 TRANSACTION MANAGEMENT
    // =====================================================

    // Get All Transactions
    //
    // GET /admin/transactions
    //
    public List<Transaction> getAllTransactions() {

        return mongoTemplate.findAll(
                Transaction.class
        );
    }


    // Get Transactions By Status
    //
    // GET /admin/transactions/status/Pending
    //
    public List<Transaction> getTransactionsByStatus(
            String status) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("status")
                        .is(status)
        );

        return mongoTemplate.find(
                query,
                Transaction.class
        );
    }


    // =====================================================
    //                    ADMIN DASHBOARD
    // =====================================================

    // Get basic Admin Dashboard data
    //
    // GET /admin/dashboard
    //
    public long getTotalUsers() {

        return mongoTemplate.count(
                new Query(),
                User.class
        );
    }


    public long getTotalNGOs() {

        return mongoTemplate.count(
                new Query(),
                NGO.class
        );
    }


    public long getPendingNGOsCount() {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("status")
                        .is("Pending")
        );

        return mongoTemplate.count(
                query,
                NGO.class
        );
    }


    public long getApprovedNGOsCount() {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("status")
                        .is("Approved")
        );

        return mongoTemplate.count(
                query,
                NGO.class
        );
    }


    public long getTotalTransactions() {

        return mongoTemplate.count(
                new Query(),
                Transaction.class
        );
    }
}
