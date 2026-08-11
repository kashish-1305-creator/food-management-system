package com.example.demo.DBHelper;

import com.example.demo.model.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionDBHelper {

    @Autowired
    private MongoTemplate mongoTemplate;


    // =====================================================
    //                    BASIC CRUD
    // =====================================================


    // Get All Transactions
    // GET /transactions
    public List<Transaction> getAll() {

        return mongoTemplate.findAll(Transaction.class);
    }


    // Get Transaction By ID
    // GET /transactions/{id}
    public Transaction getById(String id) {

        return mongoTemplate.findById(id, Transaction.class);
    }


    // Add Transaction
    // POST /transactions
    public Transaction add(Transaction transaction) {

        return mongoTemplate.save(transaction);
    }


    // Update Transaction
    // PUT /transactions/{id}
    public Transaction update(
            String id,
            Transaction transaction) {

        transaction.setTransactionId(id);

        return mongoTemplate.save(transaction);
    }


    // Delete Transaction
    // DELETE /transactions/{id}
    public boolean delete(String id) {

        Transaction transaction =
                mongoTemplate.findById(
                        id,
                        Transaction.class
                );

        if (transaction != null) {

            mongoTemplate.remove(transaction);

            return true;
        }

        return false;
    }


    // =====================================================
    //                    SEARCH APIs
    // =====================================================


    // 1. Get Transactions By User
    //
    // GET /transactions/user/U101
    //
    public List<Transaction> getByUser(String userId) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("userId")
                        .is(userId)
        );

        return mongoTemplate.find(
                query,
                Transaction.class
        );
    }


    // 2. Get Transactions By NGO
    //
    // GET /transactions/ngo/N101
    //
    public List<Transaction> getByNGO(String ngoId) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("ngoId")
                        .is(ngoId)
        );

        return mongoTemplate.find(
                query,
                Transaction.class
        );
    }


    // 3. Get Transactions By Food
    //
    // GET /transactions/food/F101
    //
    // IMPORTANT:
    // This is used when multiple NGOs request
    // the same food.
    //
    public List<Transaction> getByFood(String foodId) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("foodId")
                        .is(foodId)
        );

        return mongoTemplate.find(
                query,
                Transaction.class
        );
    }


    // =====================================================
    //                    FILTER APIs
    // =====================================================


    // 4. Get Transactions By Status
    //
    // GET /transactions/status/Pending
    //
    public List<Transaction> getByStatus(String status) {

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


    // 5. Get Transactions By Date
    //
    // GET /transactions/date/2026-08-09
    //
    public List<Transaction> getByDate(String date) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("date")
                        .is(date)
        );

        return mongoTemplate.find(
                query,
                Transaction.class
        );
    }


    // =====================================================
    //                 STATUS FILTER APIs
    // =====================================================


    // 6. Get Pending Transactions
    //
    // GET /transactions/pending
    //
    public List<Transaction> getPending() {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("status")
                        .is("Pending")
        );

        return mongoTemplate.find(
                query,
                Transaction.class
        );
    }


    // 7. Get Completed Transactions
    //
    // GET /transactions/completed
    //
    public List<Transaction> getCompleted() {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("status")
                        .is("Completed")
        );

        return mongoTemplate.find(
                query,
                Transaction.class
        );
    }


    // 8. Get Pending Requests For A Particular Food
    //
    // GET /transactions/food/F101/pending
    //
    // Example:
    //
    // Food F101
    // NGO A -> Pending
    // NGO B -> Pending
    // NGO C -> Pending
    //
    public List<Transaction> getPendingByFood(String foodId) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("foodId")
                        .is(foodId)
                        .and("status")
                        .is("Pending")
        );

        return mongoTemplate.find(
                query,
                Transaction.class
        );
    }


    // 9. Get Accepted Transaction For A Food
    //
    // GET /transactions/food/F101/accepted
    //
    // There should normally be only ONE accepted
    // transaction for one food.
    //
    public List<Transaction> getAcceptedByFood(String foodId) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("foodId")
                        .is(foodId)
                        .and("status")
                        .is("Accepted")
        );

        return mongoTemplate.find(
                query,
                Transaction.class
        );
    }


    // =====================================================
    //                 TRANSACTION STATUS
    // =====================================================


    // 10. Accept Transaction
    //
    // PUT /transactions/{id}/accept
    //
    // NOTE:
    // The complete business logic for accepting one
    // request and rejecting the other requests should
    // eventually be handled in TransactionService.
    //
    public Transaction acceptTransaction(String id) {

        Transaction transaction =
                mongoTemplate.findById(
                        id,
                        Transaction.class
                );

        if (transaction != null) {

            transaction.setStatus("Accepted");

            return mongoTemplate.save(transaction);
        }

        return null;
    }


    // 11. Reject Transaction
    //
    // PUT /transactions/{id}/reject
    //
    public Transaction rejectTransaction(String id) {

        Transaction transaction =
                mongoTemplate.findById(
                        id,
                        Transaction.class
                );

        if (transaction != null) {

            transaction.setStatus("Rejected");

            return mongoTemplate.save(transaction);
        }

        return null;
    }


    // =====================================================
    //                 CANCEL TRANSACTION
    // =====================================================


    // 12. Cancel Transaction
    //
    // PUT /transactions/{id}/cancel
    //
    // Used when NGO cancels an accepted request.
    //
    public Transaction cancelTransaction(
            String id,
            String reason,
            String cancelledBy) {

        Transaction transaction =
                mongoTemplate.findById(
                        id,
                        Transaction.class
                );

        if (transaction != null) {

            transaction.setStatus("Cancelled");

            transaction.setCancellationReason(reason);

            transaction.setCancelledBy(cancelledBy);

            return mongoTemplate.save(transaction);
        }

        return null;
    }


    // =====================================================
    //                 COMPLETE TRANSACTION
    // =====================================================


    // 13. Complete Transaction
    //
    // PUT /transactions/{id}/complete
    //
    // Used after NGO actually receives the food.
    //
    public Transaction completeTransaction(String id) {

        Transaction transaction =
                mongoTemplate.findById(
                        id,
                        Transaction.class
                );

        if (transaction != null) {

            transaction.setStatus("Completed");

            return mongoTemplate.save(transaction);
        }

        return null;
    }


    // =====================================================
    //                 CANCELLED TRANSACTIONS
    // =====================================================


    // 14. Get Cancelled Transactions
    //
    // GET /transactions/cancelled
    //
    public List<Transaction> getCancelled() {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("status")
                        .is("Cancelled")
        );

        return mongoTemplate.find(
                query,
                Transaction.class
        );
    }


    // 15. Get Cancelled Requests For A Food
    //
    // GET /transactions/food/F101/cancelled
    //
    public List<Transaction> getCancelledByFood(String foodId) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("foodId")
                        .is(foodId)
                        .and("status")
                        .is("Cancelled")
        );

        return mongoTemplate.find(
                query,
                Transaction.class
        );
    }
}

