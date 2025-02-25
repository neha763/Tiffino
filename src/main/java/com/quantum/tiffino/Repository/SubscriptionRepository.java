package com.quantum.tiffino.Repository;

import com.quantum.tiffino.Entity.Subscription;
import com.quantum.tiffino.Entity.SubscriptionStatus;
import com.quantum.tiffino.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByUser(User user);

    List<Subscription> findBySubscriptionStatus(SubscriptionStatus status);


}
