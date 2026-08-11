package com.example.demo.controller;

import com.example.demo.DBHelper.TransactionDBHelper;
import com.example.demo.model.Transaction;
import com.example.demo.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionDBHelper db;

    @Autowired
    private TransactionService transactionService;


    // =====================================================
    //                    BASIC CRUD APIs
    // =====================================================


    // Get All Transactions
    // GET /transactions
    @GetMapping
    public List<Transaction> getAll() {

        return db.getAll();
    }


    // Get Transaction By ID
    // GET /transactions/T101
    @GetMapping("/{id}")
    public Transaction getById(
            @PathVariable("id") String id) {

        return db.getById(id);
    }


    // =====================================================
    //                 CREATE FOOD REQUEST
    // =====================================================

    // Add Transaction / NGO requests food
    //
    // POST /transactions
    //
    // The service automatically sets:
    //
    // status = Pending
    // requestedAt = current time
    //
    @PostMapping
    public Transaction add(
            @RequestBody Transaction transaction) {

        return transactionService.createRequest(transaction);
    }


    // =====================================================
    //                    UPDATE
    // =====================================================


    // Update Transaction
    // PUT /transactions/T101
    @PutMapping("/{id}")
    public Transaction update(
            @PathVariable("id") String id,
            @RequestBody Transaction transaction) {

        return db.update(id, transaction);
    }


    // =====================================================
    //                    DELETE
    // =====================================================


    // Delete Transaction
    // DELETE /transactions/T101
    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable("id") String id) {

        if (db.delete(id)) {

            return "Transaction Deleted Successfully";
        }

        return "Transaction Not Found";
    }


    // =====================================================
    //                    SEARCH APIs
    // =====================================================


    // 1. Search Transactions By User
    //
    // GET /transactions/user/U101
    //
    @GetMapping("/user/{userId}")
    public List<Transaction> getByUser(
            @PathVariable("userId") String userId) {

        return db.getByUser(userId);
    }


    // 2. Search Transactions By NGO
    //
    // GET /transactions/ngo/N101
    //
    @GetMapping("/ngo/{ngoId}")
    public List<Transaction> getByNGO(
            @PathVariable("ngoId") String ngoId) {

        return db.getByNGO(ngoId);
    }


    // 3. Search Transactions By Food
    //
    // GET /transactions/food/F101
    //
    // Shows all NGOs that requested the same food.
    //
    @GetMapping("/food/{foodId}")
    public List<Transaction> getByFood(
            @PathVariable("foodId") String foodId) {

        return db.getByFood(foodId);
    }


    // =====================================================
    //                    FILTER APIs
    // =====================================================


    // 4. Filter Transactions By Status
    //
    // GET /transactions/status/Pending
    //
    @GetMapping("/status/{status}")
    public List<Transaction> getByStatus(
            @PathVariable("status") String status) {

        return db.getByStatus(status);
    }


    // 5. Filter Transactions By Date
    //
    // GET /transactions/date/2026-08-09
    //
    @GetMapping("/date/{date}")
    public List<Transaction> getByDate(
            @PathVariable("date") String date) {

        return db.getByDate(date);
    }


    // =====================================================
    //                    STATUS FILTERS
    // =====================================================


    // 6. Get Pending Transactions
    //
    // GET /transactions/pending
    //
    @GetMapping("/pending")
    public List<Transaction> getPending() {

        return db.getPending();
    }


    // 7. Get Completed Transactions
    //
    // GET /transactions/completed
    //
    @GetMapping("/completed")
    public List<Transaction> getCompleted() {

        return db.getCompleted();
    }


    // 8. Get Cancelled Transactions
    //
    // GET /transactions/cancelled
    //
    @GetMapping("/cancelled")
    public List<Transaction> getCancelled() {

        return db.getCancelled();
    }


    // =====================================================
    //              FOOD REQUEST STATUS APIs
    // =====================================================


    // 9. Get Pending Requests For A Food
    //
    // GET /transactions/food/F101/pending
    //
    @GetMapping("/food/{foodId}/pending")
    public List<Transaction> getPendingByFood(
            @PathVariable("foodId") String foodId) {

        return transactionService
                .getPendingRequestsForFood(foodId);
    }


    // 10. Get Accepted Request For A Food
    //
    // GET /transactions/food/F101/accepted
    //
    @GetMapping("/food/{foodId}/accepted")
    public List<Transaction> getAcceptedByFood(
            @PathVariable("foodId") String foodId) {

        return transactionService
                .getAcceptedRequestForFood(foodId);
    }


    // 11. Get Cancelled Requests For A Food
    //
    // GET /transactions/food/F101/cancelled
    //
    @GetMapping("/food/{foodId}/cancelled")
    public List<Transaction> getCancelledByFood(
            @PathVariable("foodId") String foodId) {

        return db.getCancelledByFood(foodId);
    }


    // =====================================================
    //                    ACCEPT REQUEST
    // =====================================================


    // 12. Accept Transaction
    //
    // PUT /transactions/T101/accept
    //
    // BUSINESS LOGIC:
    //
    // Selected NGO -> Accepted
    // Other pending NGOs -> Rejected
    // Food -> Reserved
    //
    @PutMapping("/{id}/accept")
    public Object acceptTransaction(
            @PathVariable("id") String id) {

        Transaction transaction =
                transactionService.acceptRequest(id);

        if (transaction != null) {

            return transaction;
        }

        return "Transaction cannot be accepted";
    }


    // =====================================================
    //                    REJECT REQUEST
    // =====================================================


    // 13. Reject Transaction
    //
    // PUT /transactions/T101/reject
    //
    @PutMapping("/{id}/reject")
    public Object rejectTransaction(
            @PathVariable("id") String id) {

        Transaction transaction =
                transactionService.rejectRequest(id);

        if (transaction != null) {

            return transaction;
        }

        return "Transaction cannot be rejected";
    }


    // =====================================================
    //                    CANCEL REQUEST
    // =====================================================


    // 14. Cancel Transaction
    //
    // PUT /transactions/T101/cancel
    //
    // Example:
    //
    // ?reason=NGO%20cancelled
    // &cancelledBy=N101
    //
    // BUSINESS LOGIC:
    //
    // Accepted -> Cancelled
    // Food -> Available
    //
    @PutMapping("/{id}/cancel")
    public Object cancelTransaction(
            @PathVariable("id") String id,
            @RequestParam("reason") String reason,
            @RequestParam("cancelledBy") String cancelledBy) {

        Transaction transaction =
                transactionService.cancelRequest(
                        id,
                        reason,
                        cancelledBy
                );

        if (transaction != null) {

            return transaction;
        }

        return "Transaction cannot be cancelled";
    }


    // =====================================================
    //                  COMPLETE DONATION
    // =====================================================


    // 15. Complete Transaction
    //
    // PUT /transactions/T101/complete
    //
    // BUSINESS LOGIC:
    //
    // Accepted -> Completed
    // Food -> Donated
    //
    @PutMapping("/{id}/complete")
    public Object completeTransaction(
            @PathVariable("id") String id) {

        Transaction transaction =
                transactionService.completeRequest(id);

        if (transaction != null) {

            return transaction;
        }

        return "Transaction cannot be completed";
    }
}

