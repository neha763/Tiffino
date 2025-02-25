package com.quantum.tiffino.Service;
import com.quantum.tiffino.Entity.Subscription;
import com.quantum.tiffino.Entity.SubscriptionStatus;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface SubscriptionService {

    Subscription createSubscription(Subscription subscription);

    List<Subscription> getUserSubscriptions(Long userId);

    Optional<Subscription> getSubscriptionById(Long subscriptionId);

    void updateSubscriptionStatus(Long subscriptionId, SubscriptionStatus status);

    List<Subscription> getActiveSubscriptions();

    void pauseSubscription(Long subscriptionId, Date resumeDate);

    void cancelSubscription(Long subscriptionId);

    void updateZeroCostDelivery(Long subscriptionId, boolean isZeroCostDelivery);
    boolean getZeroCostDeliveryStatus(Long subscriptionId);
}
