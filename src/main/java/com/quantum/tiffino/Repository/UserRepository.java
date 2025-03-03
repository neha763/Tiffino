package com.quantum.tiffino.Repository;

import com.quantum.tiffino.Entity.Restaurant;
import com.quantum.tiffino.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);
    Optional<User>findByEmail(String email);

    // Corrected the query syntax and fixed method signature for findByUsername
    Optional<User> findByUsername(String username);
    Optional<User> findByPhoneNumber(String phoneNumber);
    List<Restaurant> findRestaurantById(Long id);


    boolean existsByEmail(String email);
}

