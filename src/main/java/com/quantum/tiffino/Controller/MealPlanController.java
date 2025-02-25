package com.quantum.tiffino.Controller;

import com.quantum.tiffino.Entity.MealPlan;
import com.quantum.tiffino.Service.MealPlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mealplans")
public class MealPlanController {

    @Autowired
    private MealPlanService mealPlanService;

    // Endpoint to create a new Meal Plan
    @PostMapping
    public ResponseEntity<MealPlan> createMealPlan(@RequestBody MealPlan mealPlan) {
        // Call the service to create the MealPlan and link the correct Menu automatically
        MealPlan createdMealPlan = mealPlanService.createMealPlan(mealPlan);

        // Return the response with status 201 (Created) along with the created MealPlan object
        return new ResponseEntity<>(createdMealPlan, HttpStatus.CREATED);
    }
    // Endpoint to get a MealPlan by its ID
    @GetMapping("/{id}")
    public ResponseEntity<MealPlan> getMealPlanById(@PathVariable Long id) {
        MealPlan mealPlan = mealPlanService.getMealPlanById(id);
        return new ResponseEntity<>(mealPlan, HttpStatus.OK);
    }

    // Endpoint to get all MealPlans
    @GetMapping
    public ResponseEntity<List<MealPlan>> getAllMeals() {
        List<MealPlan> mealPlans = mealPlanService.getAllMeals();
        return new ResponseEntity<>(mealPlans, HttpStatus.OK);
    }

    // Endpoint to update an existing MealPlan
    @PutMapping("/{id}")
    public ResponseEntity<MealPlan> updateMeal(@PathVariable Long id, @Valid @RequestBody MealPlan mealPlan) {
        MealPlan updatedMealPlan = mealPlanService.updateMeal(id, mealPlan);
        return new ResponseEntity<>(updatedMealPlan, HttpStatus.OK);
    }

    // Endpoint to delete a MealPlan by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMeal(@PathVariable Long id) {
        mealPlanService.deleteMeal(id);
        return new ResponseEntity<>("MealPlan deleted successfully", HttpStatus.NO_CONTENT);
    }
}
