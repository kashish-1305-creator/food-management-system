package com.example.demo.repository;

import com.example.demo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findByUserId(String userId);

    List<Transaction> findByFoodId(String foodId);

    List<Transaction> findByNgoId(String ngoId);

    List<Transaction> findByStatus(String status);

    List<Transaction> findByDateContainingIgnoreCase(String date);

    List<Transaction> findByUserIdAndStatus(String userId, String status);

    List<Transaction> findByNgoIdAndStatus(String ngoId, String status);

    List<Transaction> findByFoodIdAndStatus(String foodId, String status);

    List<Transaction> findByCancelledBy(String cancelledBy);
}
