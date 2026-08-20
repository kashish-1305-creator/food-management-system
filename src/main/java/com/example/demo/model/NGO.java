package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ngos")
public class NGO {

    @Id
    private String ngoId;
    private String name;
    private String email;
    private String address;
    private String mobile;
    private String status;
    private String password;

    public NGO() {
    }

    public NGO(String ngoId, String name, String email, String address, String mobile,String status,String password) {
        this.ngoId = ngoId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.mobile = mobile;
        this.status = status;
        this.password = password;
    }

    // Getters and Setters
    public String getNgoId() {
        return ngoId;
    }

    public void setNgoId(String ngoId) {
        this.ngoId = ngoId;
    }

    public String getNGOId() {
        return ngoId;
    }

    public void setNGOId(String ngoId) {
        this.ngoId = ngoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
