package com.quantum.tiffino.Controller;

import com.quantum.tiffino.Entity.DeliveryPerson;
import com.quantum.tiffino.Service.DeliveryPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-persons")
@CrossOrigin("*")
public class DeliveryPersonController {

    @Autowired
    private DeliveryPersonService deliveryPersonService;

    // Endpoint to create a new delivery person
    @PostMapping("/create")
    public ResponseEntity<DeliveryPerson> createDeliveryPerson(@RequestBody DeliveryPerson deliveryPerson) {
        DeliveryPerson createdDeliveryPerson = deliveryPersonService.createDeliveryPerson(deliveryPerson);
        return ResponseEntity.ok(createdDeliveryPerson);
    }

    // Endpoint to get a delivery person by ID
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryPerson> getDeliveryPersonById(@PathVariable Long id) {
        DeliveryPerson deliveryPerson = deliveryPersonService.getDeliveryPersonById(id);
        return ResponseEntity.ok(deliveryPerson);
    }

    // Endpoint to get all delivery persons
    @GetMapping("/list")
    public ResponseEntity<List<DeliveryPerson>> getAllDeliveryPersons() {
        List<DeliveryPerson> deliveryPersons = deliveryPersonService.getAllDeliveryPersons();
        return ResponseEntity.ok(deliveryPersons);
    }

    // Endpoint to update a delivery person
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateDeliveryPerson(@PathVariable Long id, @RequestBody DeliveryPerson deliveryPerson) {
        deliveryPersonService.updateDeliveryPerson(id, deliveryPerson);
        return ResponseEntity.ok().build();
    }

    // Endpoint to delete a delivery person by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDeliveryPerson(@PathVariable Long id) {
        deliveryPersonService.deleteDeliveryPerson(id);
        return ResponseEntity.ok().build();
    }
}
