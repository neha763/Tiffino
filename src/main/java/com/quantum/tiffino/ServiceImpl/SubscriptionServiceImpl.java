package com.quantum.tiffino.ServiceImpl;
import com.quantum.tiffino.Entity.Subscription;
import com.quantum.tiffino.Entity.SubscriptionStatus;
import com.quantum.tiffino.Entity.User;
import com.quantum.tiffino.Exception.SubscriptionNotFoundException;
import com.quantum.tiffino.Exception.UserNotFoundException;
import com.quantum.tiffino.Repository.SubscriptionRepository;
import com.quantum.tiffino.Repository.UserRepository;
import com.quantum.tiffino.Service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Subscription createSubscription(Subscription subscription) {
        if(subscription.isZeroCostDelivery()==false){
            subscription.setZeroCostDelivery(true);

        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public List<Subscription> getUserSubscriptions(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        return subscriptionRepository.findByUser(user);
    }

    @Override
    public Optional<Subscription> getSubscriptionById(Long subscriptionId) {
        return subscriptionRepository.findById(subscriptionId);
    }

    @Override
    public void updateSubscriptionStatus(Long subscriptionId, SubscriptionStatus status) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found"));
        subscription.setSubscriptionStatus(status);
        if(status == SubscriptionStatus.ACTIVE){
            subscription.setZeroCostDelivery(true);
        }else {
            subscription.setZeroCostDelivery(false);
        }
        subscriptionRepository.save(subscription);
    }

    @Override
    public List<Subscription> getActiveSubscriptions() {
        return subscriptionRepository.findBySubscriptionStatus(SubscriptionStatus.ACTIVE);
    }

    @Override
    public void pauseSubscription(Long subscriptionId, Date resumeDate) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found"));
        subscription.setSubscriptionStatus(SubscriptionStatus.PAUSED);
        subscription.setPausedUntilDate(resumeDate);
        subscriptionRepository.save(subscription);
    }

    @Override
    public void cancelSubscription(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found"));
        subscription.setSubscriptionStatus(SubscriptionStatus.CANCELED);
        subscriptionRepository.save(subscription);
    }


    @Override
    public void updateZeroCostDelivery(Long subscriptionId, boolean isZeroCostDelivery) {
        Optional<Subscription> subscriptionOpt = subscriptionRepository.findById(subscriptionId);
        if (subscriptionOpt.isPresent()) {
            Subscription subscription = subscriptionOpt.get();
            subscription.setZeroCostDelivery(isZeroCostDelivery);
            subscriptionRepository.save(subscription);
        }
    }


    @Override
    public boolean getZeroCostDeliveryStatus(Long subscriptionId) {
        Optional<Subscription> subscriptionOpt = subscriptionRepository.findById(subscriptionId);
        return subscriptionOpt.map(Subscription::isZeroCostDelivery).orElse(false);
    }
}