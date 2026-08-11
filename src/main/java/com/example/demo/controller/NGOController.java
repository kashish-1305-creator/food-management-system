package com.example.demo.controller;

import com.example.demo.DBHelper.NGODBHelper;
import com.example.demo.model.NGO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ngo")
public class NGOController {

    @Autowired
    private NGODBHelper db;


    // =====================================================
    //                    BASIC CRUD APIs
    // =====================================================


    // Get All NGOs
    // GET /ngo
    @GetMapping
    public List<NGO> getAll() {

        return db.getAll();
    }


    // Get NGO By ID
    // GET /ngo/N101
    @GetMapping("/{id}")
    public NGO getById(
            @PathVariable("id") String id) {

        return db.getById(id);
    }


    // =====================================================
    //                  NGO REGISTRATION
    // =====================================================


    // Register NGO
    //
    // POST /ngo/register
    //
    // New NGO automatically gets:
    //
    // status = Pending
    //
    // Admin must approve the NGO.
    //
    @PostMapping("/register")
    public Object register(
            @RequestBody NGO ngo) {


        // Check duplicate email
        if (db.emailExists(ngo.getEmail())) {

            return "Email already registered";
        }


        // Register NGO
        return db.registerNGO(ngo);
    }


    // =====================================================
    //                    ADD NGO
    // =====================================================


    // Add NGO
    // POST /ngo
    //
    // This is basic CRUD.
    // For registration, use /ngo/register.
    //
    @PostMapping
    public NGO add(
            @RequestBody NGO ngo) {

        return db.add(ngo);
    }


    // =====================================================
    //                    UPDATE NGO
    // =====================================================


    // Update NGO
    // PUT /ngo/N101
    @PutMapping("/{id}")
    public NGO update(
            @PathVariable("id") String id,
            @RequestBody NGO ngo) {

        return db.update(id, ngo);
    }


    // =====================================================
    //                    DELETE NGO
    // =====================================================


    // Delete NGO
    // DELETE /ngo/N101
    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable("id") String id) {

        if (db.delete(id)) {

            return "NGO Deleted Successfully";
        }

        return "NGO Not Found";
    }


    // =====================================================
    //                       LOGIN
    // =====================================================


    // NGO Login
    //
    // POST /ngo/login
    //
    @PostMapping("/login")
    public Object login(
            @RequestBody NGO ngo) {


        NGO loginNgo =
                db.login(
                        ngo.getEmail(),
                        ngo.getPassword()
                );


        // Email/password incorrect
        if (loginNgo == null) {

            return "Invalid Email or Password";
        }


        // NGO must be Approved by Admin
        if ("Pending".equalsIgnoreCase(
                loginNgo.getStatus())) {

            return "NGO Registration is Pending Admin Approval";
        }


        // NGO rejected
        if ("Rejected".equalsIgnoreCase(
                loginNgo.getStatus())) {

            return "NGO Registration has been Rejected";
        }


        // NGO blocked
        if ("Blocked".equalsIgnoreCase(
                loginNgo.getStatus())) {

            return "NGO Account is Blocked";
        }


        // Only Approved NGO can login
        if (!"Approved".equalsIgnoreCase(
                loginNgo.getStatus())) {

            return "NGO is not Approved";
        }


        return loginNgo;
    }


    // =====================================================
    //                    SEARCH APIs
    // =====================================================


    // 1. Search NGO By Name
    //
    // GET /ngo/search/name/HelpingHands
    //
    @GetMapping("/search/name/{name}")
    public List<NGO> searchByName(
            @PathVariable("name") String name) {

        return db.searchByName(name);
    }


    // 2. Search NGO By Email
    //
    // GET /ngo/search/email/ngo@gmail.com
    //
    @GetMapping("/search/email/{email}")
    public List<NGO> searchByEmail(
            @PathVariable("email") String email) {

        return db.searchByEmail(email);
    }


    // 3. Search NGO By Mobile
    //
    // GET /ngo/search/mobile/9876543210
    //
    @GetMapping("/search/mobile/{mobile}")
    public List<NGO> searchByMobile(
            @PathVariable("mobile") String mobile) {

        return db.searchByMobile(mobile);
    }


    // 4. Search NGO By Address
    //
    // GET /ngo/search/address/Pune
    //
    @GetMapping("/search/address/{address}")
    public List<NGO> searchByAddress(
            @PathVariable("address") String address) {

        return db.searchByAddress(address);
    }


    // =====================================================
    //                  NGO STATUS APIs
    // =====================================================


    // 5. Get Approved NGOs
    //
    // GET /ngo/approved
    //
    @GetMapping("/approved")
    public List<NGO> getApprovedNGOs() {

        return db.getApprovedNGOs();
    }


    // 6. Get Pending NGOs
    //
    // GET /ngo/pending
    //
    @GetMapping("/pending")
    public List<NGO> getPendingNGOs() {

        return db.getPendingNGOs();
    }


    // =====================================================
    //                    NGO DASHBOARD
    // =====================================================


    // 7. NGO Dashboard
    //
    // GET /ngo/N101/dashboard
    //
    @GetMapping("/{id}/dashboard")
    public Map<String, Object> getDashboard(
            @PathVariable("id") String id) {

        return db.getDashboard(id);
    }
}
