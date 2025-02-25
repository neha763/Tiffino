package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.MealAvailability;
import com.quantum.tiffino.Repository.MealAvailabilityRepository;
import com.quantum.tiffino.Repository.MealPlanRepository;
import com.quantum.tiffino.Repository.RegionRepository;
import com.quantum.tiffino.Service.MealAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealAvailabilityServiceImpl implements MealAvailabilityService {

    @Autowired
    private MealAvailabilityRepository mealAvailabilityRepository;

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Override
    public MealAvailability createMealAvailability(MealAvailability mealAvailability) {
        // Ensure the associated Meal and Region exist
        if (mealPlanRepository.existsById(mealAvailability.getMealPlan().getId()) &&
                regionRepository.existsById(mealAvailability.getRegion().getId())) {

            return mealAvailabilityRepository.save(mealAvailability);
        } else {
            throw new IllegalArgumentException("Invalid meal or region reference");
        }
    }

    @Override
    public Optional<MealAvailability> getMealAvailabilityById(Long id) {
        return mealAvailabilityRepository.findById(id);
    }

    @Override
    public List<MealAvailability> getAllMealAvailabilities() {
        return mealAvailabilityRepository.findAll();
    }

    @Override
    public List<MealAvailability> getMealAvailabilitiesByMeal(Long mealId) {
        return mealAvailabilityRepository.findByMealPlan_Id(mealId);
    }

    @Override
    public List<MealAvailability> getMealAvailabilitiesByRegion(Long regionId) {
        return mealAvailabilityRepository.findByRegion_Id(regionId);
    }

    @Override
    public MealAvailability updateMealAvailability(Long id, MealAvailability mealAvailability) {
        if (mealAvailabilityRepository.existsById(id)) {
            mealAvailability.setId(id);
            return mealAvailabilityRepository.save(mealAvailability);
        } else {
            throw new IllegalArgumentException("Meal availability not found");
        }
    }

    @Override
    public void deleteMealAvailability(Long id) {
        if (mealAvailabilityRepository.existsById(id)) {
            mealAvailabilityRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Meal availability not found");
        }
    }
}
