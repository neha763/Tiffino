package com.quantum.tiffino.Repository;

import com.quantum.tiffino.Entity.Delivery;
import com.quantum.tiffino.Entity.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {

    List<Delivery> findByDeliveryPersonId(Long deliveryPersonId);

    List<Delivery> findByDeliveryStatus(DeliveryStatus deliveryStatus);
}
