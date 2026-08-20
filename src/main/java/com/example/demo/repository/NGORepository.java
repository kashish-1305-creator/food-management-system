package com.example.demo.repository;

import com.example.demo.model.NGO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NGORepository extends JpaRepository<NGO, String> {

    Optional<NGO> findByEmailAndPassword(String email, String password);

    List<NGO> findByNameContainingIgnoreCase(String name);

    List<NGO> findByEmailContainingIgnoreCase(String email);

    List<NGO> findByAddressContainingIgnoreCase(String address);

    List<NGO> findByMobile(String mobile);

    List<NGO> findByStatusIgnoreCase(String status);

    boolean existsByEmail(String email);
}
