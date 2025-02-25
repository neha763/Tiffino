package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.Delivery;
import com.quantum.tiffino.Entity.DeliveryPerson;
import com.quantum.tiffino.Entity.DeliveryStatus;
import com.quantum.tiffino.Exception.DeliveryNotFoundException;
import com.quantum.tiffino.Exception.DeliveryPersonNotFoundException;
import com.quantum.tiffino.Repository.DeliveryRepository;
import com.quantum.tiffino.Repository.DeliveryPersonRepository;
import com.quantum.tiffino.Service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    @Override
    public Delivery createDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    @Override
    public Delivery getDeliveryById(Long id) throws DeliveryNotFoundException {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found with ID: " + id));
    }

    @Override
    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    @Override
    public Delivery assignDeliveryToPerson(Long deliveryId, Long deliveryPersonId) throws DeliveryNotFoundException {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found with ID: " + deliveryId));

        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(deliveryPersonId)
                .orElseThrow(() -> new DeliveryPersonNotFoundException("Delivery Person not found with ID: " + deliveryPersonId));

        delivery.setDeliveryPerson(deliveryPerson);
        return deliveryRepository.save(delivery);
    }

    @Override
    public void updateDeliveryStatus(Long deliveryId, String status) throws DeliveryNotFoundException {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found with ID: " + deliveryId));
        delivery.setDeliveryStatus(DeliveryStatus.valueOf(status));
        deliveryRepository.save(delivery);
    }

    @Override
    public void cancelDelivery(Long deliveryId) throws DeliveryNotFoundException {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DeliveryNotFoundException("Delivery not found with ID: " + deliveryId));
        delivery.setDeliveryStatus(DeliveryStatus.valueOf("CANCELLED"));
        deliveryRepository.save(delivery);
    }
}
