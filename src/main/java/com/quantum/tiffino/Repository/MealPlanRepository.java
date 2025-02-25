package com.quantum.tiffino.Repository;

import com.quantum.tiffino.Entity.MealPlan;
import com.quantum.tiffino.Entity.MealPlanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan,Long> {

    Optional<MealPlan> findById(Long id);

    MealPlan findFirstByOrderByIdAsc();

    MealPlan findByName(String string);

    MealPlan findByMealPlanType(MealPlanType mealPlanType);
}


