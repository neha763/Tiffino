package com.quantum.tiffino.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private double discountPercentage;
    //private double discountAmount;
    private boolean isActive;
    private LocalDateTime expirationDate;
    private int maxUsageLimit;
    private int usedCount;

    public Coupon(Long id, String code, DiscountType discountType, double discountPercentage, boolean isActive, LocalDateTime expirationDate, int maxUsageLimit, int usedCount) {
        this.id = id;
        this.code = code;
        this.discountType = discountType;
        this.discountPercentage = discountPercentage;
        this.isActive = isActive;
        this.expirationDate = expirationDate;
        this.maxUsageLimit = maxUsageLimit;
        this.usedCount = usedCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getMaxUsageLimit() {
        return maxUsageLimit;
    }

    public void setMaxUsageLimit(int maxUsageLimit) {
        this.maxUsageLimit = maxUsageLimit;
    }

    public int getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", discountType=" + discountType +
                ", discountPercentage=" + discountPercentage +
                ", isActive=" + isActive +
                ", expirationDate=" + expirationDate +
                ", maxUsageLimit=" + maxUsageLimit +
                ", usedCount=" + usedCount +
                '}';
    }
}