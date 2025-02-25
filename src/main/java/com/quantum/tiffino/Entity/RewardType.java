package com.quantum.tiffino.Entity;

public enum RewardType {

    LOYALTY(100, 3),
    REFERRAL(200, 6),
    MILESTONE(500, 12);

    private final int defaultPoints;
    private final int expiryMonths;

    RewardType(int defaultPoints, int expiryMonths) {
        this.defaultPoints = defaultPoints;
        this.expiryMonths = expiryMonths;
    }

    public int getDefaultPoints() {
        return defaultPoints;
    }

    public int getExpiryMonths() {
        return expiryMonths;
    }
}


