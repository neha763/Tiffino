package com.quantum.tiffino.Controller;

import com.quantum.tiffino.Entity.Reward;
import com.quantum.tiffino.Entity.RewardType;
import com.quantum.tiffino.Service.RewardService;
import com.quantum.tiffino.Exception.InsufficientPointsException;
import com.quantum.tiffino.Exception.RewardNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rewards")
@CrossOrigin("*")
public class RewardController {

    private final RewardService rewardService;

    @Autowired
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reward>> getUserRewards(@PathVariable Long userId) {
        List<Reward> rewards = rewardService.getUserRewards(userId);
        if (rewards.isEmpty()) {
            throw new RewardNotFoundException("No rewards found for user with ID: " + userId);
        }
        return ResponseEntity.ok(rewards);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addReward(@RequestParam Long userId,
                                            @RequestParam String rewardType,
                                            @RequestParam String description) {
        try {
            rewardService.addReward(userId, RewardType.valueOf(rewardType), description);
            return ResponseEntity.ok("Reward added successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid Reward Type provided.");
        }
    }

    @PostMapping("/redeem")
    public ResponseEntity<String> redeemReward(@RequestParam Long userId,
                                               @RequestParam int points) {
        try {
            if (rewardService.redeemReward(userId, points)) {
                return ResponseEntity.ok("Reward redeemed successfully!");
            } else {
                throw new InsufficientPointsException("Insufficient points to redeem the reward.");
            }
        } catch (InsufficientPointsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
