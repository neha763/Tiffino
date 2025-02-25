package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.Reward;
import com.quantum.tiffino.Entity.RewardType;
import com.quantum.tiffino.Entity.User;
import com.quantum.tiffino.Repository.RewardRepository;
import com.quantum.tiffino.Repository.UserRepository;
import com.quantum.tiffino.Service.RewardService;
import com.quantum.tiffino.Exception.InsufficientPointsException;
import com.quantum.tiffino.Exception.RewardNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RewardServiceImpl implements RewardService {

    private final RewardRepository rewardRepository;
    private final UserRepository userRepository;

    @Autowired
    public RewardServiceImpl(RewardRepository rewardRepository, UserRepository userRepository) {
        this.rewardRepository = rewardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Reward> getUserRewards(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RewardNotFoundException("User with ID " + userId + " not found."));
        return rewardRepository.findByUser(user);
    }

    @Override
    public void addReward(Long userId, RewardType rewardType, String description) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RewardNotFoundException("User with ID " + userId + " not found."));

        Reward reward = new Reward(user, rewardType, description);
        rewardRepository.save(reward);
    }

    @Override
    public boolean redeemReward(Long userId, int points) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RewardNotFoundException("User with ID " + userId + " not found."));

        List<Reward> userRewards = rewardRepository.findByUser(user);

        // Calculate the total points that can be redeemed.
        int totalPoints = userRewards.stream()
                .filter(reward -> !reward.isRedeemed() && reward.getExpiryDate().isAfter(LocalDateTime.now()))
                .mapToInt(Reward::getPointsEarned)
                .sum();

        // Check if user has enough points to redeem
        if (totalPoints < points) {
            throw new InsufficientPointsException("User does not have enough points to redeem.");
        }

        // Redeem rewards by points
        int remainingPoints = points;
        for (Reward reward : userRewards) {
            if (!reward.isRedeemed() && reward.getExpiryDate().isAfter(LocalDateTime.now())) {
                if (reward.getPointsEarned() <= remainingPoints) {
                    reward.setRedeemed(true);
                    remainingPoints -= reward.getPointsEarned();
                } else {
                    break;
                }
            }
        }

        rewardRepository.saveAll(userRewards);
        return remainingPoints == 0;
    }
}
