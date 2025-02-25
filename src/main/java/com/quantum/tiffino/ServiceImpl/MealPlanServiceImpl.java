package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.MealPlan;
import com.quantum.tiffino.Repository.MealPlanRepository;
import com.quantum.tiffino.Service.MealPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealPlanServiceImpl implements MealPlanService {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Override
    public MealPlan createMealPlan(MealPlan mealPlan) {
       return mealPlanRepository.save(mealPlan);
    }

    @Override
    public MealPlan getMealPlanById(Long mealPlanId) {
        Optional<MealPlan> mealPlan = mealPlanRepository.findById(mealPlanId);
        if (mealPlan.isPresent()) {
            return mealPlan.get();
        } else {
            // Handle MealPlan not found
            throw new RuntimeException("Meal Plan not found for ID: " + mealPlanId);
        }
    }

    @Override
    public List<MealPlan> getAllMeals() {
        return mealPlanRepository.findAll();
    }

    @Override
    public MealPlan updateMeal(Long id, MealPlan mealPlan) {
        Optional<MealPlan> existingMealPlan = mealPlanRepository.findById(id);
        if (existingMealPlan.isPresent()) {
            MealPlan updatedMealPlan = existingMealPlan.get();
            updatedMealPlan.setName(mealPlan.getName());
            updatedMealPlan.setDescription(mealPlan.getDescription());
            updatedMealPlan.setPrice(mealPlan.getPrice());
            return mealPlanRepository.save(updatedMealPlan);
        } else {
            // Handle MealPlan not found
            throw new RuntimeException("Meal Plan not found for ID: " + id);
        }
    }

    @Override
    public void deleteMeal(Long id) {
        Optional<MealPlan> mealPlan = mealPlanRepository.findById(id);
        if (mealPlan.isPresent()) {
            mealPlanRepository.delete(mealPlan.get());
        } else {
            // Handle MealPlan not found
            throw new RuntimeException("Meal Plan not found for ID: " + id);
        }
    }
}
