package com.quantum.tiffino.Repository;

import com.quantum.tiffino.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsername(String username);


    boolean existsByUsername(String username);


    boolean existsByEmail(String email);


    boolean existsByMobileNo(String mobileNo);
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByMobileNo(String mobileNo);
}

