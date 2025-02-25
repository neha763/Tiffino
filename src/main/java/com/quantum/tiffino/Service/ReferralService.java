package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.Referral;

public interface ReferralService {
    Referral createReferral(Long userId, String referralCode);
    Referral getReferralByUserId(Long userId);
}
