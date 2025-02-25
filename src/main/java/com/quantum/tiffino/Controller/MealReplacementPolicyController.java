package com.quantum.tiffino.Controller;
import com.quantum.tiffino.Entity.MealReplacementPolicy;
import com.quantum.tiffino.Exception.MealReplacementPolicyNotFoundException;
import com.quantum.tiffino.Service.MealReplacementPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meal-replacement-policies")
@CrossOrigin("*")
public class MealReplacementPolicyController {

    private final MealReplacementPolicyService mealReplacementPolicyService;

    @Autowired
    public MealReplacementPolicyController(MealReplacementPolicyService mealReplacementPolicyService) {
        this.mealReplacementPolicyService = mealReplacementPolicyService;
    }

    // Create a new meal replacement policy
    @PostMapping
    public ResponseEntity<MealReplacementPolicy> createMealReplacementPolicy(@RequestBody MealReplacementPolicy mealReplacementPolicy) {
        MealReplacementPolicy createdPolicy = mealReplacementPolicyService.createMealReplacementPolicy(mealReplacementPolicy);
        return new ResponseEntity<>(createdPolicy, HttpStatus.CREATED);
    }

    // Get a meal replacement policy by ID
    @GetMapping("/{policyId}")
    public ResponseEntity<MealReplacementPolicy> getMealReplacementPolicyById(@PathVariable Long policyId) {
        try {
            MealReplacementPolicy policy = mealReplacementPolicyService.getMealReplacementPolicyById(policyId)
                    .orElseThrow(() -> new MealReplacementPolicyNotFoundException("Policy with ID " + policyId + " not found"));
            return new ResponseEntity<>(policy, HttpStatus.OK);
        } catch (MealReplacementPolicyNotFoundException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Get all meal replacement policies
    @GetMapping
    public ResponseEntity<List<MealReplacementPolicy>> getAllMealReplacementPolicies() {
        List<MealReplacementPolicy> policies = mealReplacementPolicyService.getAllMealReplacementPolicies();
        return new ResponseEntity<>(policies, HttpStatus.OK);
    }

    // Update an existing meal replacement policy
    @PutMapping("/{policyId}")
    public ResponseEntity<MealReplacementPolicy> updateMealReplacementPolicy(@PathVariable Long policyId,
                                                                             @RequestBody MealReplacementPolicy mealReplacementPolicy) {
        try {
            MealReplacementPolicy updatedPolicy = mealReplacementPolicyService.updateMealReplacementPolicy(policyId, mealReplacementPolicy);
            return new ResponseEntity<>(updatedPolicy, HttpStatus.OK);
        } catch (MealReplacementPolicyNotFoundException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete a meal replacement policy by ID
    @DeleteMapping("/{policyId}")
    public ResponseEntity<Void> deleteMealReplacementPolicy(@PathVariable Long policyId) {
        try {
            mealReplacementPolicyService.deleteMealReplacementPolicy(policyId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (MealReplacementPolicyNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
