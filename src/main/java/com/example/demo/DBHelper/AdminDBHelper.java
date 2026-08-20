package com.example.demo.DBHelper;

import com.example.demo.model.Admin;
import com.example.demo.model.NGO;
import com.example.demo.model.User;
import com.example.demo.model.Transaction;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.NGORepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDBHelper {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private NGORepository ngoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // =====================================================
    //                    ADMIN LOGIN
    // =====================================================

    public Admin login(String email, String password) {
        return adminRepository.findByEmailAndPassword(email, password).orElse(null);
    }

    // =====================================================
    //                    ADMIN BY ID
    // =====================================================

    public Admin getById(String id) {
        return adminRepository.findById(id).orElse(null);
    }

    // =====================================================
    //                    NGO MANAGEMENT
    // =====================================================

    public List<NGO> getAllNGOs() {
        return ngoRepository.findAll();
    }

    public List<NGO> getPendingNGOs() {
        return ngoRepository.findByStatusIgnoreCase("Pending");
    }

    public NGO approveNGO(String ngoId) {
        NGO ngo = ngoRepository.findById(ngoId).orElse(null);
        if (ngo != null) {
            ngo.setStatus("Approved");
            return ngoRepository.save(ngo);
        }
        return null;
    }

    public NGO rejectNGO(String ngoId) {
        NGO ngo = ngoRepository.findById(ngoId).orElse(null);
        if (ngo != null) {
            ngo.setStatus("Rejected");
            return ngoRepository.save(ngo);
        }
        return null;
    }

    public NGO blockNGO(String ngoId) {
        NGO ngo = ngoRepository.findById(ngoId).orElse(null);
        if (ngo != null) {
            ngo.setStatus("Blocked");
            return ngoRepository.save(ngo);
        }
        return null;
    }

    public NGO unblockNGO(String ngoId) {
        NGO ngo = ngoRepository.findById(ngoId).orElse(null);
        if (ngo != null) {
            ngo.setStatus("Approved");
            return ngoRepository.save(ngo);
        }
        return null;
    }

    // =====================================================
    //                    USER MANAGEMENT
    // =====================================================

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    // =====================================================
    //                 TRANSACTION MANAGEMENT
    // =====================================================

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsByStatus(String status) {
        return transactionRepository.findByStatus(status);
    }

    // =====================================================
    //                    ADMIN DASHBOARD
    // =====================================================

    public long getTotalUsers() {
        return userRepository.count();
    }

    public long getTotalNGOs() {
        return ngoRepository.count();
    }

    public long getPendingNGOsCount() {
        return ngoRepository.findByStatusIgnoreCase("Pending").size();
    }

    public long getApprovedNGOsCount() {
        return ngoRepository.findByStatusIgnoreCase("Approved").size();
    }

    public long getTotalTransactions() {
        return transactionRepository.count();
    }
}
