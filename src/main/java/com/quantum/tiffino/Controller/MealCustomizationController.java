package com.quantum.tiffino.Controller;

import com.quantum.tiffino.Entity.MealCustomization;
import com.quantum.tiffino.Service.MealCustomizationService;
import com.quantum.tiffino.Exception.MealCustomizationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/meal-customizations")
@CrossOrigin("*")
public class MealCustomizationController {

    private final MealCustomizationService mealCustomizationService;

    @Autowired
    public MealCustomizationController(MealCustomizationService mealCustomizationService) {
        this.mealCustomizationService = mealCustomizationService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<MealCustomization> getCustomizationByUser(@PathVariable Long userId) {
        Optional<MealCustomization> customization = mealCustomizationService.getCustomizationByUser(userId);

        return customization.map(ResponseEntity::ok)
                .orElseThrow(() -> new MealCustomizationNotFoundException("Meal customization for user with ID " + userId + " not found."));
    }

    @PostMapping("/")
    public ResponseEntity<MealCustomization> createCustomization(@RequestBody MealCustomization mealCustomization) {
        MealCustomization savedCustomization = mealCustomizationService.saveCustomization(mealCustomization);
        return new ResponseEntity<>(savedCustomization, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<MealCustomization> updateCustomization(@PathVariable Long userId, @RequestBody MealCustomization mealCustomization) {
        MealCustomization updatedCustomization = mealCustomizationService.updateCustomization(userId, mealCustomization);
        return ResponseEntity.ok(updatedCustomization);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteCustomization(@PathVariable Long userId) {
        mealCustomizationService.deleteCustomization(userId);
        return ResponseEntity.ok("Meal customization deleted successfully for user with ID " + userId);
    }
}
