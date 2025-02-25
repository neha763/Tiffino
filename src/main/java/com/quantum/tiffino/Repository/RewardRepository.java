package com.quantum.tiffino.Repository;

import com.quantum.tiffino.Entity.Reward;
import com.quantum.tiffino.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardRepository extends JpaRepository<Reward,Long> {
    List<Reward> findByUserIdAndRedeemedFalse(Long user_id);
    List<Reward> findByUser(User user);



}
