package com.quantum.tiffino.Controller;

import com.quantum.tiffino.Entity.Delivery;
import com.quantum.tiffino.Exception.DeliveryNotFoundException;
import com.quantum.tiffino.Service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
        Delivery createdDelivery = deliveryService.createDelivery(delivery);
        return ResponseEntity.status(201).body(createdDelivery);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable Long id) throws DeliveryNotFoundException {
        Delivery delivery = deliveryService.getDeliveryById(id);
        return ResponseEntity.ok(delivery);
    }

    @GetMapping
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        List<Delivery> deliveries = deliveryService.getAllDeliveries();
        return ResponseEntity.ok(deliveries);
    }

    @PostMapping("/{deliveryId}/assign/{deliveryPersonId}")
    public ResponseEntity<Delivery> assignDeliveryToPerson(@PathVariable Long deliveryId, @PathVariable Long deliveryPersonId) throws DeliveryNotFoundException {
        Delivery updatedDelivery = deliveryService.assignDeliveryToPerson(deliveryId, deliveryPersonId);
        return ResponseEntity.ok(updatedDelivery);
    }

    @PutMapping("/{deliveryId}/status/{status}")
    public ResponseEntity<Void> updateDeliveryStatus(@PathVariable Long deliveryId, @PathVariable String status) throws DeliveryNotFoundException {
        deliveryService.updateDeliveryStatus(deliveryId, status);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{deliveryId}/cancel")
    public ResponseEntity<Void> cancelDelivery(@PathVariable Long deliveryId) throws DeliveryNotFoundException {
        deliveryService.cancelDelivery(deliveryId);
        return ResponseEntity.noContent().build();
    }
}
