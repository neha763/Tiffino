package com.quantum.tiffino.Entity;



public enum  MembershipType {
    BRONZE(5,12),   // 5% discount
    SILVER(10,12 ),  // 10% discount
    GOLD(15,12),    // 15% discount
    PLATINUM(20,12); // 20% discount

    private final double discountPercentage;
    private final int expiryMonth;

    MembershipType(double discountPercentage,  int expiryMonth) {
        this.discountPercentage = discountPercentage;
        this.expiryMonth = expiryMonth;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }
}
