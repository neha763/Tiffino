package com.quantum.tiffino.Controller;
import com.quantum.tiffino.Entity.Subscription;
import com.quantum.tiffino.Entity.SubscriptionStatus;
import com.quantum.tiffino.Service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subscriptions")
@CrossOrigin("*")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;


    @PostMapping("/create")
    public Subscription createSubscription(@RequestBody Subscription subscription) {
        subscription.setZeroCostDelivery(true);
        return subscriptionService.createSubscription(subscription);
    }


    @GetMapping("/user/{userId}")
    public List<Subscription> getUserSubscriptions(@PathVariable Long userId) {
        return subscriptionService.getUserSubscriptions(userId);
    }


    @GetMapping("/{subscriptionId}")
    public Optional<Subscription> getSubscriptionById(@PathVariable Long subscriptionId) {
        return subscriptionService.getSubscriptionById(subscriptionId);
    }

    // Update subscription status (Active, Paused, etc.)
    @PutMapping("/{subscriptionId}/status")
    public void updateSubscriptionStatus(@PathVariable Long subscriptionId, @RequestParam SubscriptionStatus status) {
        subscriptionService.updateSubscriptionStatus(subscriptionId, status);
    }


    @PutMapping("/{subscriptionId}/pause")
    public void pauseSubscription(
            @PathVariable Long subscriptionId,
            @RequestParam("resumeDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date resumeDate) {
        subscriptionService.pauseSubscription(subscriptionId, resumeDate);
    }



    @PutMapping("/{subscriptionId}/cancel")
    public void cancelSubscription(@PathVariable Long subscriptionId) {
        subscriptionService.cancelSubscription(subscriptionId);
    }


    @GetMapping("/active")
    public List<Subscription> getActiveSubscriptions() {
        return subscriptionService.getActiveSubscriptions();
    }

    @PutMapping("/{subscriptionId}/zeroCostDelivery")
    public void updateZeroCostDelivery(
            @PathVariable Long subscriptionId,
            @RequestParam boolean isZeroCostDelivery) {
        subscriptionService.updateZeroCostDelivery(subscriptionId, isZeroCostDelivery);
    }


    @GetMapping("/{subscriptionId}/zeroCostDelivery")
    public boolean getZeroCostDeliveryStatus(@PathVariable Long subscriptionId) {
        return subscriptionService.getZeroCostDeliveryStatus(subscriptionId);
    }
}
