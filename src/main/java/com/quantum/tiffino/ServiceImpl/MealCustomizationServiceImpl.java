package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.MealCustomization;
import com.quantum.tiffino.Entity.MealPlan;
import com.quantum.tiffino.Entity.MealPlanType;
import com.quantum.tiffino.Exception.MealCustomizationNotFoundException;
import com.quantum.tiffino.Exception.MealPlanNotFoundException;
import com.quantum.tiffino.Repository.MealCustomizationRepository;
import com.quantum.tiffino.Repository.MealPlanRepository;
import com.quantum.tiffino.Service.MealCustomizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MealCustomizationServiceImpl implements MealCustomizationService {

    private final MealCustomizationRepository mealCustomizationRepository;
    private final MealPlanRepository mealPlanRepository;

    @Autowired
    public MealCustomizationServiceImpl(MealCustomizationRepository mealCustomizationRepository, MealPlanRepository mealPlanRepository) {
        this.mealCustomizationRepository = mealCustomizationRepository;
        this.mealPlanRepository = mealPlanRepository;
    }

    @Override
    public Optional<MealCustomization> getCustomizationByUser(Long userId) {
        return mealCustomizationRepository.findByUserId(userId);
    }


    @Override
    public MealCustomization saveCustomization(MealCustomization mealCustomization) {
        // Automatically set the MealPlan based on MealPlanType
        MealPlan mealPlan = getMealPlanByMealPlanType(mealCustomization.getMealPlanType());
        mealCustomization.setMealPlan(mealPlan);

        // Save MealCustomization with the correct MealPlan
        return mealCustomizationRepository.save(mealCustomization);
    }

    @Override
    public MealCustomization updateCustomization(Long userId, MealCustomization mealCustomization) {
        // Find existing MealCustomization for the user
        MealCustomization existingCustomization = mealCustomizationRepository.findById(userId)
                .orElseThrow(() -> new MealCustomizationNotFoundException("Meal customization for user with ID " + userId + " not found"));

        // Automatically set the MealPlan based on MealPlanType
        MealPlan mealPlan = getMealPlanByMealPlanType(mealCustomization.getMealPlanType());
        mealCustomization.setMealPlan(mealPlan);

        // Update MealCustomization with the correct MealPlan
        return mealCustomizationRepository.save(mealCustomization);
    }

    @Override
    public void deleteCustomization(Long userId) {
        Optional<MealCustomization> existingCustomization = mealCustomizationRepository.findByUserId(userId);

        if (existingCustomization.isPresent()) {
            mealCustomizationRepository.delete(existingCustomization.get());
        } else {
            throw new MealCustomizationNotFoundException("Meal customization for user with ID " + userId + " not found.");
        }
    }
    private MealPlan getMealPlanByMealPlanType(MealPlanType mealPlanType) {
        // Find the MealPlan by MealPlanType (Breakfast, Lunch, Dinner)
        switch (mealPlanType) {
            case BREAKFAST:
                return mealPlanRepository.findById(1L).orElseThrow(() -> new RuntimeException("MealPlan for Breakfast not found"));
            case LUNCH:
                return mealPlanRepository.findById(2L).orElseThrow(() -> new RuntimeException("MealPlan for Lunch not found"));
            case DINNER:
                return mealPlanRepository.findById(3L).orElseThrow(() -> new RuntimeException("MealPlan for Dinner not found"));
            default:
                throw new IllegalArgumentException("Invalid MealPlanType");
        }
    }

    }
