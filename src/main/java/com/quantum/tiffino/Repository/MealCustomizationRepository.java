package com.quantum.tiffino.Repository;

import com.quantum.tiffino.Entity.MealCustomization;
import com.quantum.tiffino.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MealCustomizationRepository extends JpaRepository<MealCustomization,Long> {
    Optional<MealCustomization> findByUserId(Long userId);
   // Optional<MealCustomization> findByUser(User user);
    void deleteByUserId(Long userId);
}
