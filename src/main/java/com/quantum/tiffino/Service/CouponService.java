package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.Coupon;

import java.util.List;
import java.util.Optional;

public interface CouponService {


        boolean applyCoupon(String code);

        void deleteCoupon(Long id);

        Coupon updateCoupon(Long id, Coupon coupon);

        List<Coupon> getAllCoupons();

        Coupon saveCoupon(Coupon coupon);

        Optional<Coupon> getCouponByID(Long id);

    }



