package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.MealAvailability;

import java.util.List;
import java.util.Optional;

public interface MealAvailabilityService {

    MealAvailability createMealAvailability(MealAvailability mealAvailability);

    Optional<MealAvailability> getMealAvailabilityById(Long id);

    List<MealAvailability> getAllMealAvailabilities();

    List<MealAvailability> getMealAvailabilitiesByMeal(Long mealId);

    List<MealAvailability> getMealAvailabilitiesByRegion(Long regionId);

    MealAvailability updateMealAvailability(Long id, MealAvailability mealAvailability);

    void deleteMealAvailability(Long id);
}
