package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.MealCustomization;

import java.util.Optional;

public interface MealCustomizationService {
    Optional<MealCustomization> getCustomizationByUser(Long userId);

    MealCustomization saveCustomization(MealCustomization mealCustomization);

    MealCustomization updateCustomization(Long userId, MealCustomization mealCustomization);

    void deleteCustomization(Long userId);

}
