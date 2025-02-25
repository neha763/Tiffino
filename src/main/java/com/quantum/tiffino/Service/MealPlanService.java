package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.MealPlan;

import java.util.List;

public interface MealPlanService {
    MealPlan createMealPlan(MealPlan mealPlan);
    MealPlan getMealPlanById(Long mealPlanId);
    List<MealPlan> getAllMeals();
    MealPlan updateMeal(Long id, MealPlan mealPlan);
    void deleteMeal(Long id);


  //  MealPlan createMealPlan(MealPlan mealPlan);
}
