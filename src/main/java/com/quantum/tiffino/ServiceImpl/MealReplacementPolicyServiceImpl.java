package com.quantum.tiffino.ServiceImpl;
import com.quantum.tiffino.Entity.MealReplacementPolicy;
import com.quantum.tiffino.Exception.MealReplacementPolicyNotFoundException;

import com.quantum.tiffino.Repository.MealReplacementPolicyRepository;
import com.quantum.tiffino.Service.MealReplacementPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealReplacementPolicyServiceImpl implements MealReplacementPolicyService {

    private final MealReplacementPolicyRepository mealReplacementPolicyRepository;

    @Autowired
    public MealReplacementPolicyServiceImpl(MealReplacementPolicyRepository mealReplacementPolicyRepository) {
        this.mealReplacementPolicyRepository = mealReplacementPolicyRepository;
    }

    @Override
    public MealReplacementPolicy createMealReplacementPolicy(MealReplacementPolicy mealReplacementPolicy) {
        return mealReplacementPolicyRepository.save(mealReplacementPolicy);
    }

    @Override
    public Optional<MealReplacementPolicy> getMealReplacementPolicyById(Long policyId) {
        return mealReplacementPolicyRepository.findById(policyId);
    }

    @Override
    public List<MealReplacementPolicy> getAllMealReplacementPolicies() {
        return mealReplacementPolicyRepository.findAll();
    }

    @Override
    public MealReplacementPolicy updateMealReplacementPolicy(Long policyId, MealReplacementPolicy mealReplacementPolicy) {
        if (!mealReplacementPolicyRepository.existsById(policyId)) {
            throw new MealReplacementPolicyNotFoundException("Policy with ID " + policyId + " not found");
        }

        mealReplacementPolicy.setPolicyId(policyId);  // Ensure the ID is set correctly
        return mealReplacementPolicyRepository.save(mealReplacementPolicy);
    }

    @Override
    public void deleteMealReplacementPolicy(Long policyId) {
        if (!mealReplacementPolicyRepository.existsById(policyId)) {
            throw new MealReplacementPolicyNotFoundException("Policy with ID " + policyId + " not found");
        }
        mealReplacementPolicyRepository.deleteById(policyId);
    }
}
