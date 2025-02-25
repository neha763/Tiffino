package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.MealReplacementPolicy;

import java.util.List;
import java.util.Optional;

public interface MealReplacementPolicyService {

    MealReplacementPolicy createMealReplacementPolicy(MealReplacementPolicy mealReplacementPolicy);

    Optional<MealReplacementPolicy> getMealReplacementPolicyById(Long policyId);  // Ensure the parameter type is Long

    List<MealReplacementPolicy> getAllMealReplacementPolicies();

    MealReplacementPolicy updateMealReplacementPolicy(Long policyId, MealReplacementPolicy mealReplacementPolicy);  // Ensure the parameter type is Long

    void deleteMealReplacementPolicy(Long policyId);  // Ensure the parameter type is Long

}
