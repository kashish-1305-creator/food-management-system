package com.example.demo.DBHelper;

import com.example.demo.model.User;
import com.example.demo.model.NGO;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.NGORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuthDBHelper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NGORepository ngoRepository;

    public User userLogin(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).orElse(null);
    }

    public NGO ngoLogin(String email, String password) {
        return ngoRepository.findByEmailAndPassword(email, password).orElse(null);
    }
}
