package com.quantum.tiffino.Repository;

import com.quantum.tiffino.Entity.MealReplacementPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealReplacementPolicyRepository extends JpaRepository<MealReplacementPolicy,Long> {
}
