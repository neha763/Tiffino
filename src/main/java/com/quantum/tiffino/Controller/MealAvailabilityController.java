package com.quantum.tiffino.Controller;
import com.quantum.tiffino.Entity.MealAvailability;
import com.quantum.tiffino.Service.MealAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/meal-availability")
@CrossOrigin("*")
public class MealAvailabilityController {

    @Autowired
    private MealAvailabilityService mealAvailabilityService;


    @PostMapping
    public ResponseEntity<MealAvailability> createMealAvailability(@RequestBody MealAvailability mealAvailability) {
        try {
            MealAvailability createdMealAvailability = mealAvailabilityService.createMealAvailability(mealAvailability);
            return new ResponseEntity<>(createdMealAvailability, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping
    public ResponseEntity<List<MealAvailability>> getAllMealAvailabilities() {
        List<MealAvailability> mealAvailabilities = mealAvailabilityService.getAllMealAvailabilities();
        return new ResponseEntity<>(mealAvailabilities, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MealAvailability> getMealAvailabilityById(@PathVariable Long id) {
        Optional<MealAvailability> mealAvailability = mealAvailabilityService.getMealAvailabilityById(id);
        return mealAvailability.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/meal/{mealId}")
    public ResponseEntity<List<MealAvailability>> getMealAvailabilitiesByMeal(@PathVariable Long mealId) {
        List<MealAvailability> mealAvailabilities = mealAvailabilityService.getMealAvailabilitiesByMeal(mealId);
        return new ResponseEntity<>(mealAvailabilities, HttpStatus.OK);
    }


    @GetMapping("/region/{regionId}")
    public ResponseEntity<List<MealAvailability>> getMealAvailabilitiesByRegion(@PathVariable Long regionId) {
        List<MealAvailability> mealAvailabilities = mealAvailabilityService.getMealAvailabilitiesByRegion(regionId);
        return new ResponseEntity<>(mealAvailabilities, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MealAvailability> updateMealAvailability(@PathVariable Long id, @RequestBody MealAvailability mealAvailability) {
        try {
            MealAvailability updatedMealAvailability = mealAvailabilityService.updateMealAvailability(id, mealAvailability);
            return new ResponseEntity<>(updatedMealAvailability, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealAvailability(@PathVariable Long id) {
        try {
            mealAvailabilityService.deleteMealAvailability(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
