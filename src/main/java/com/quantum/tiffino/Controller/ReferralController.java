package com.quantum.tiffino.Controller;

import com.quantum.tiffino.Entity.Referral;
import com.quantum.tiffino.Service.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/referrals")
@CrossOrigin("*")
public class ReferralController {

    @Autowired
    private ReferralService referralService;


    @PostMapping
    public Referral createReferral(@RequestParam Long userId, @RequestParam String referralCode) {
        return referralService.createReferral(userId, referralCode);
    }


    @GetMapping("/user/{userId}")
    public Referral getReferralByUserId(@PathVariable Long userId) {
        return referralService.getReferralByUserId(userId);
    }
}
