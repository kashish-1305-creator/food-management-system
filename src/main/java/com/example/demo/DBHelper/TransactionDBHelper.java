package com.example.demo.DBHelper;

import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionDBHelper {

    @Autowired
    private TransactionRepository transactionRepository;

    // =====================================================
    //                    BASIC CRUD
    // =====================================================

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    public Transaction getById(String id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public Transaction add(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction update(String id, Transaction transaction) {
        transaction.setTransactionId(id);
        return transactionRepository.save(transaction);
    }

    public boolean delete(String id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // =====================================================
    //                    SEARCH APIs
    // =====================================================

    public List<Transaction> getByUser(String userId) {
        return transactionRepository.findByUserId(userId);
    }

    public List<Transaction> getByNGO(String ngoId) {
        return transactionRepository.findByNgoId(ngoId);
    }

    public List<Transaction> getByFood(String foodId) {
        return transactionRepository.findByFoodId(foodId);
    }

    // =====================================================
    //                    FILTER APIs
    // =====================================================

    public List<Transaction> getByStatus(String status) {
        return transactionRepository.findByStatus(status);
    }

    public List<Transaction> getByDate(String date) {
        return transactionRepository.findByDateContainingIgnoreCase(date);
    }

    // =====================================================
    //                 STATUS FILTER APIs
    // =====================================================

    public List<Transaction> getPending() {
        return transactionRepository.findByStatus("Pending");
    }

    public List<Transaction> getCompleted() {
        return transactionRepository.findByStatus("Completed");
    }

    public List<Transaction> getPendingByFood(String foodId) {
        return transactionRepository.findByFoodIdAndStatus(foodId, "Pending");
    }

    public List<Transaction> getAcceptedByFood(String foodId) {
        return transactionRepository.findByFoodIdAndStatus(foodId, "Accepted");
    }

    // =====================================================
    //                 TRANSACTION STATUS
    // =====================================================

    public Transaction acceptTransaction(String id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction != null) {
            transaction.setStatus("Accepted");
            return transactionRepository.save(transaction);
        }
        return null;
    }

    public Transaction rejectTransaction(String id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction != null) {
            transaction.setStatus("Rejected");
            return transactionRepository.save(transaction);
        }
        return null;
    }

    // =====================================================
    //                 CANCEL TRANSACTION
    // =====================================================

    public Transaction cancelTransaction(String id, String reason, String cancelledBy) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction != null) {
            transaction.setStatus("Cancelled");
            transaction.setCancellationReason(reason);
            transaction.setCancelledBy(cancelledBy);
            return transactionRepository.save(transaction);
        }
        return null;
    }

    // =====================================================
    //                 COMPLETE TRANSACTION
    // =====================================================

    public Transaction completeTransaction(String id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction != null) {
            transaction.setStatus("Completed");
            return transactionRepository.save(transaction);
        }
        return null;
    }

    // =====================================================
    //                 CANCELLED TRANSACTIONS
    // =====================================================

    public List<Transaction> getCancelled() {
        return transactionRepository.findByStatus("Cancelled");
    }

    public List<Transaction> getCancelledByFood(String foodId) {
        return transactionRepository.findByFoodIdAndStatus(foodId, "Cancelled");
    }
}
