package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmailAndPassword(String email, String password);

    List<User> findByUserNameContainingIgnoreCase(String userName);

    List<User> findByEmailContainingIgnoreCase(String email);

    List<User> findByMobile(String mobile);

    List<User> findByAddressContainingIgnoreCase(String address);

    boolean existsByEmail(String email);
}
