package com.example.demo.DBHelper;

import com.example.demo.model.NGO;
import com.example.demo.repository.NGORepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Repository
public class NGODBHelper {

    @Autowired
    private NGORepository ngoRepository;

    // =====================================================
    //                    BASIC CRUD
    // =====================================================

    // Get All NGOs
    public List<NGO> getAll() {
        return ngoRepository.findAll();
    }

    // Get NGO By ID
    public NGO getById(String id) {
        return ngoRepository.findById(id).orElse(null);
    }

    // Add NGO
    public NGO add(NGO ngo) {
        return ngoRepository.save(ngo);
    }

    // Update NGO
    public NGO update(String id, NGO ngo) {
        ngo.setNGOId(id);
        return ngoRepository.save(ngo);
    }

    // Delete NGO
    public boolean delete(String id) {
        if (ngoRepository.existsById(id)) {
            ngoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // =====================================================
    //                       LOGIN
    // =====================================================

    public NGO login(String email, String password) {
        return ngoRepository.findByEmailAndPassword(email, password).orElse(null);
    }

    // =====================================================
    //                    SEARCH APIs
    // =====================================================

    public List<NGO> searchByName(String name) {
        return ngoRepository.findByNameContainingIgnoreCase(name);
    }

    public List<NGO> searchByEmail(String email) {
        return ngoRepository.findByEmailContainingIgnoreCase(email);
    }

    public List<NGO> searchByMobile(String mobile) {
        return ngoRepository.findByMobile(mobile);
    }

    public List<NGO> searchByAddress(String address) {
        return ngoRepository.findByAddressContainingIgnoreCase(address);
    }

    // =====================================================
    //                  NGO STATUS APIs
    // =====================================================

    public List<NGO> getApprovedNGOs() {
        return ngoRepository.findByStatusIgnoreCase("Approved");
    }

    public List<NGO> getPendingNGOs() {
        return ngoRepository.findByStatusIgnoreCase("Pending");
    }

    // =====================================================
    //                    NGO DASHBOARD
    // =====================================================

    public Map<String, Object> getDashboard(String id) {
        Map<String, Object> dashboard = new HashMap<>();

        NGO ngo = ngoRepository.findById(id).orElse(null);

        if (ngo == null) {
            dashboard.put("message", "NGO Not Found");
            return dashboard;
        }

        dashboard.put("ngoId", id);
        dashboard.put("ngoName", ngo.getName());
        dashboard.put("email", ngo.getEmail());
        dashboard.put("status", ngo.getStatus());

        return dashboard;
    }

    // =====================================================
    //                 NGO REGISTRATION
    // =====================================================

    public NGO registerNGO(NGO ngo) {
        ngo.setStatus("Pending");
        return ngoRepository.save(ngo);
    }

    // =====================================================
    //              CHECK NGO EMAIL
    // =====================================================

    public boolean emailExists(String email) {
        return ngoRepository.existsByEmail(email);
    }

    // =====================================================
    //                 APPROVE NGO
    // =====================================================

    public NGO approveNGO(String ngoId) {
        NGO ngo = ngoRepository.findById(ngoId).orElse(null);
        if (ngo != null) {
            ngo.setStatus("Approved");
            return ngoRepository.save(ngo);
        }
        return null;
    }

    // =====================================================
    //                 REJECT NGO
    // =====================================================

    public NGO rejectNGO(String ngoId) {
        NGO ngo = ngoRepository.findById(ngoId).orElse(null);
        if (ngo != null) {
            ngo.setStatus("Rejected");
            return ngoRepository.save(ngo);
        }
        return null;
    }

    // =====================================================
    //                  BLOCK NGO
    // =====================================================

    public NGO blockNGO(String ngoId) {
        NGO ngo = ngoRepository.findById(ngoId).orElse(null);
        if (ngo != null) {
            ngo.setStatus("Blocked");
            return ngoRepository.save(ngo);
        }
        return null;
    }

    // =====================================================
    //                UNBLOCK NGO
    // =====================================================

    public NGO unblockNGO(String ngoId) {
        NGO ngo = ngoRepository.findById(ngoId).orElse(null);
        if (ngo != null) {
            ngo.setStatus("Approved");
            return ngoRepository.save(ngo);
        }
        return null;
    }
}
