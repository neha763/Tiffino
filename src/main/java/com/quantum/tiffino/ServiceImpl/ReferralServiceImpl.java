package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.Referral;
import com.quantum.tiffino.Entity.User;
import com.quantum.tiffino.Repository.ReferralRepository;
import com.quantum.tiffino.Repository.UserRepository;
import com.quantum.tiffino.Service.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReferralServiceImpl implements ReferralService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReferralRepository referralRepository;

    @Override
    public Referral createReferral(Long userId, String referralCode) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Referral referral = new Referral();
        referral.setUser(user);
        referral.setReferralCode(referralCode);
        return referralRepository.save(referral);
    }


    @Override
    public Referral getReferralByUserId(Long userId) {
        return referralRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Referral not found"));
    }
}
