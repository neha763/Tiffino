package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.DeliveryPerson;

import java.util.List;

public interface DeliveryPersonService {
        DeliveryPerson createDeliveryPerson(DeliveryPerson deliveryPerson);
        DeliveryPerson getDeliveryPersonById(Long id);
        List<DeliveryPerson> getAllDeliveryPersons();
        void updateDeliveryPerson(Long id, DeliveryPerson deliveryPerson);
        void deleteDeliveryPerson(Long id);
}
