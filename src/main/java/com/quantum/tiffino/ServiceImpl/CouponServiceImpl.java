package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.Coupon;
import com.quantum.tiffino.Repository.CouponRepository;
import com.quantum.tiffino.Service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;

import java.util.Optional;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired

    private CouponRepository couponRepository;

    @Override

    public Coupon saveCoupon(Coupon coupon) {

        return couponRepository.save(coupon);

    }

    @Override

    public Optional<Coupon> getCouponByID(Long id) {

        return couponRepository.findById(id);

    }

    @Override

    public List<Coupon> getAllCoupons() {

        return couponRepository.findAll();

    }

    @Override

    public Coupon updateCoupon(Long id, Coupon updatedCoupon) {

        return couponRepository.findById(id).map(coupon -> {

            coupon.setCode(updatedCoupon.getCode());

            coupon.setDiscountType(updatedCoupon.getDiscountType());

            coupon.setDiscountPercentage(updatedCoupon.getDiscountPercentage());

           // coupon.setDiscountAmount(updatedCoupon.getDiscountAmount());

            coupon.setActive(updatedCoupon.isActive());

            coupon.setExpirationDate(updatedCoupon.getExpirationDate());

            coupon.setMaxUsageLimit(updatedCoupon.getMaxUsageLimit());

            return couponRepository.save(coupon);

        }).orElseThrow(() -> new RuntimeException("Coupon not found"));

    }

    @Override

    public void deleteCoupon(Long id) {

        couponRepository.deleteById(id);

    }

    @Override

    public boolean applyCoupon(String code) {

        Optional<Coupon> optionalCoupon = couponRepository.findByCode(code);

        if (optionalCoupon.isPresent()) {

            Coupon coupon = optionalCoupon.get();

            if (coupon.isActive() && coupon.getExpirationDate().isAfter(LocalDateTime.now())
                    && coupon.getUsedCount() < coupon.getMaxUsageLimit()) {

                coupon.setUsedCount(coupon.getUsedCount() + 1);

                couponRepository.save(coupon);

                return true;

            }

        }

        return false;

    }

}
