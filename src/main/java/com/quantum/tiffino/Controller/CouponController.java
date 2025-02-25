package com.quantum.tiffino.Controller;

import com.quantum.tiffino.Entity.Coupon;
import com.quantum.tiffino.Service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coupons")
@CrossOrigin("*")
public class CouponController {

        @Autowired

        private CouponService couponService;

        @PostMapping

        public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon){

            Coupon savedCoupon = couponService.saveCoupon(coupon);

            return ResponseEntity.ok(savedCoupon);

        }

        @GetMapping("/{id}")

        public ResponseEntity<Coupon> getCouponById(@PathVariable Long id){

            Optional<Coupon> coupon=couponService.getCouponByID(id);

            return coupon.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());

        }

        @GetMapping

        public ResponseEntity<List<Coupon>> getAllCoupons() {

            return ResponseEntity.ok(couponService.getAllCoupons());

        }

        @PutMapping("/{id}")

        public ResponseEntity<Coupon> updateCoupon(@PathVariable Long id, @RequestBody Coupon coupon) {

            Coupon updatedCoupon = couponService.updateCoupon(id, coupon);

            return ResponseEntity.ok(updatedCoupon);

        }

        @DeleteMapping("/{id}")

        public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {

            couponService.deleteCoupon(id);

            return ResponseEntity.noContent().build();

        }

        public ResponseEntity<String> applyCoupon(@PathVariable String code){

            boolean isApplied=couponService.applyCoupon(code);

            return isApplied ? ResponseEntity.ok("Coupon applied Successfully") :

                    ResponseEntity.badRequest().body("Invalid or Expired Coupon");

        }

    }

