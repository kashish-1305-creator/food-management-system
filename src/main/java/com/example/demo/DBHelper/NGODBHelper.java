package com.example.demo.DBHelper;

import com.example.demo.model.NGO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Repository
public class NGODBHelper {

    @Autowired
    private MongoTemplate mongoTemplate;


    // =====================================================
    //                    BASIC CRUD
    // =====================================================


    // Get All NGOs
    public List<NGO> getAll() {

        return mongoTemplate.findAll(NGO.class);
    }


    // Get NGO By ID
    public NGO getById(String id) {

        return mongoTemplate.findById(id, NGO.class);
    }


    // Add NGO
    public NGO add(NGO ngo) {

        return mongoTemplate.save(ngo);
    }


    // Update NGO
    public NGO update(String id, NGO ngo) {

        ngo.setNGOId(id);

        return mongoTemplate.save(ngo);
    }


    // Delete NGO
    public boolean delete(String id) {

        NGO ngo = mongoTemplate.findById(id, NGO.class);

        if (ngo != null) {

            mongoTemplate.remove(ngo);

            return true;
        }

        return false;
    }


    // =====================================================
    //                       LOGIN
    // =====================================================


    // Login using Email and Password
    public NGO login(String email, String password) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("email").is(email)
                        .and("password").is(password)
        );

        return mongoTemplate.findOne(query, NGO.class);
    }


    // =====================================================
    //                    SEARCH APIs
    // =====================================================


    // 1. Search NGO By Name
    //
    // GET /ngo/search/name/HelpingHands
    //
    // Case-insensitive search
    //
    public List<NGO> searchByName(String name) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("name")
                        .regex(name, "i")
        );

        return mongoTemplate.find(query, NGO.class);
    }


    // 2. Search NGO By Email
    //
    // GET /ngo/search/email/ngo@gmail.com
    //
    public List<NGO> searchByEmail(String email) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("email")
                        .regex(email, "i")
        );

        return mongoTemplate.find(query, NGO.class);
    }


    // 3. Search NGO By Mobile
    //
    // GET /ngo/search/mobile/9876543210
    //
    public List<NGO> searchByMobile(String mobile) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("mobile")
                        .is(mobile)
        );

        return mongoTemplate.find(query, NGO.class);
    }


    // 4. Search NGO By Address
    //
    // GET /ngo/search/address/Pune
    //
    public List<NGO> searchByAddress(String address) {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("address")
                        .regex(address, "i")
        );

        return mongoTemplate.find(query, NGO.class);
    }


    // =====================================================
    //                  NGO STATUS APIs
    // =====================================================


    // 5. Get Approved NGOs
    //
    // GET /ngo/approved
    //
    public List<NGO> getApprovedNGOs() {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("status")
                        .is("Approved")
        );

        return mongoTemplate.find(query, NGO.class);
    }


    // 6. Get Pending NGOs
    //
    // GET /ngo/pending
    //
    public List<NGO> getPendingNGOs() {

        Query query = new Query();

        query.addCriteria(
                Criteria.where("status")
                        .is("Pending")
        );

        return mongoTemplate.find(query, NGO.class);
    }


    // =====================================================
    //                    NGO DASHBOARD
    // =====================================================


    // 7. NGO Dashboard
    //
    // GET /ngo/N101/dashboard
    //
    public Map<String, Object> getDashboard(String id) {

        Map<String, Object> dashboard =
                new HashMap<>();


        // Find NGO
        NGO ngo = mongoTemplate.findById(
                id,
                NGO.class
        );


        // NGO not found
        if (ngo == null) {

            dashboard.put(
                    "message",
                    "NGO Not Found"
            );

            return dashboard;
        }


        // NGO ID
        dashboard.put(
                "ngoId",
                id
        );


        // NGO Name
        dashboard.put(
                "ngoName",
                ngo.getName()
        );


        // NGO Email
        dashboard.put(
                "email",
                ngo.getEmail()
        );


        // NGO Status
        dashboard.put(
                "status",
                ngo.getStatus()
        );


        return dashboard;
    }
// =====================================================
//                 NGO REGISTRATION
// =====================================================

// Register NGO
//
// New NGO will automatically get:
// status = Pending
//
// Admin must approve the NGO before
// it can request food.
//
public NGO registerNGO(NGO ngo) {

    ngo.setStatus("Pending");

    return mongoTemplate.save(ngo);
}


// =====================================================
//              CHECK NGO EMAIL
// =====================================================

// Check whether NGO email already exists
//
public boolean emailExists(String email) {

    Query query = new Query();

    query.addCriteria(
            Criteria.where("email")
                    .is(email)
    );

    return mongoTemplate.exists(
            query,
            NGO.class
    );
}


// =====================================================
//                 APPROVE NGO
// =====================================================

// Admin approves NGO
//
// Pending -> Approved
//
public NGO approveNGO(String ngoId) {

    NGO ngo = mongoTemplate.findById(
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
//                 REJECT NGO
// =====================================================

// Admin rejects NGO
//
// Pending -> Rejected
//
public NGO rejectNGO(String ngoId) {

    NGO ngo = mongoTemplate.findById(
            ngoId,
            NGO.class
    );

    if (ngo != null) {

        ngo.setStatus("Rejected");

        return mongoTemplate.save(ngo);
    }

    return null;
}


// =====================================================
//                  BLOCK NGO
// =====================================================

// Admin blocks an NGO
//
// Approved -> Blocked
//
public NGO blockNGO(String ngoId) {

    NGO ngo = mongoTemplate.findById(
            ngoId,
            NGO.class
    );

    if (ngo != null) {

        ngo.setStatus("Blocked");

        return mongoTemplate.save(ngo);
    }

    return null;
}


// =====================================================
//                UNBLOCK NGO
// =====================================================

// Admin unblocks an NGO
//
// Blocked -> Approved
//
public NGO unblockNGO(String ngoId) {

    NGO ngo = mongoTemplate.findById(
            ngoId,
            NGO.class
    );

    if (ngo != null) {

        ngo.setStatus("Approved");

        return mongoTemplate.save(ngo);
    }

    return null;
}
}

