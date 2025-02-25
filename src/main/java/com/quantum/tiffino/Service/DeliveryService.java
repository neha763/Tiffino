package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.Delivery;
import com.quantum.tiffino.Entity.DeliveryPerson;
import com.quantum.tiffino.Exception.DeliveryNotFoundException;

import java.util.List;

public interface DeliveryService {
    Delivery createDelivery(Delivery delivery);
    Delivery getDeliveryById(Long id) throws DeliveryNotFoundException;
    List<Delivery> getAllDeliveries();
    Delivery assignDeliveryToPerson(Long deliveryId, Long deliveryPersonId) throws DeliveryNotFoundException;
    void updateDeliveryStatus(Long deliveryId, String status) throws DeliveryNotFoundException;
    void cancelDelivery(Long deliveryId) throws DeliveryNotFoundException;
}
