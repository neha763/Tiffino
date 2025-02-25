package com.quantum.tiffino.Repository;

import com.quantum.tiffino.Entity.MealAvailability;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MealAvailabilityRepository extends JpaRepository<MealAvailability,Long> {
    List<MealAvailability> findByMealPlan_Id(Long mealId);

    List<MealAvailability> findByRegion_Id(Long regionId);


}
