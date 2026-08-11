package com.example.demo.controller;

import com.example.demo.DBHelper.AuthDBHelper;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.LoginResponse;
import com.example.demo.model.NGO;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthDBHelper db;

    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest request){

        User user = db.userLogin(request.getEmail(),request.getPassword());

        if(user!=null){

            return new LoginResponse(
                    "JWT_TOKEN_HERE",
                    "USER",
                    user
            );
        }

        NGO ngo=db.ngoLogin(request.getEmail(),request.getPassword());

        if(ngo!=null){

            return new LoginResponse(
                    "JWT_TOKEN_HERE",
                    "NGO",
                    ngo
            );
        }

        return "Invalid Credentials";
    }
}
