package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.DeliveryPerson;
import com.quantum.tiffino.Repository.DeliveryPersonRepository;
import com.quantum.tiffino.Service.DeliveryPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryPersonServiceImpl implements DeliveryPersonService {

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    @Override
    public DeliveryPerson createDeliveryPerson(DeliveryPerson deliveryPerson) {
        return deliveryPersonRepository.save(deliveryPerson);
    }

    @Override
    public DeliveryPerson getDeliveryPersonById(Long id) {
        Optional<DeliveryPerson> deliveryPerson = deliveryPersonRepository.findById(id);
        return deliveryPerson.orElseThrow(() -> new RuntimeException("DeliveryPerson not found with ID: " + id));
    }

    @Override
    public List<DeliveryPerson> getAllDeliveryPersons() {
        return deliveryPersonRepository.findAll();
    }

    @Override
    public void updateDeliveryPerson(Long id, DeliveryPerson deliveryPerson) {
        Optional<DeliveryPerson> existingDeliveryPerson = deliveryPersonRepository.findById(id);
        if (existingDeliveryPerson.isPresent()) {
            DeliveryPerson updatedDeliveryPerson = existingDeliveryPerson.get();
            updatedDeliveryPerson.setName(deliveryPerson.getName());
            updatedDeliveryPerson.setPhoneNumber(deliveryPerson.getPhoneNumber());
            updatedDeliveryPerson.setVehicleType(deliveryPerson.getVehicleType());
            deliveryPersonRepository.save(updatedDeliveryPerson);
        } else {
            throw new RuntimeException("DeliveryPerson not found with ID: " + id);
        }
    }

    @Override
    public void deleteDeliveryPerson(Long id) {
        Optional<DeliveryPerson> deliveryPerson = deliveryPersonRepository.findById(id);
        if (deliveryPerson.isPresent()) {
            deliveryPersonRepository.delete(deliveryPerson.get());
        } else {
            throw new RuntimeException("DeliveryPerson not found with ID: " + id);
        }
    }
}
