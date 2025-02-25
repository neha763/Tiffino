package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.Reward;
import com.quantum.tiffino.Entity.RewardType;

import java.util.List;

public interface RewardService {
    List<Reward> getUserRewards(Long user_id);

    void addReward(Long userId, RewardType rewardType, String description);

    boolean redeemReward(Long userId, int points);


}
