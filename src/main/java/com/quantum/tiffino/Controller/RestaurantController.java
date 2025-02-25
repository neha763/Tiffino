package com.quantum.tiffino.Controller;

import com.quantum.tiffino.Entity.Restaurant;
import com.quantum.tiffino.Entity.DeliveryPerson;
import com.quantum.tiffino.Service.RestaurantService;
import com.quantum.tiffino.Exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin("*")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // Endpoint to create a new restaurant
    @PostMapping("/create")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant createdRestaurant = restaurantService.createRestaurant(restaurant);
        return ResponseEntity.ok(createdRestaurant);
    }

    // Endpoint to get a restaurant by ID
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(restaurant);
    }

    // Endpoint to get all restaurants
    @GetMapping("/list")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    // Endpoint to update a restaurant
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) throws RestaurantNotFoundException {
        restaurantService.updateRestaurant(id, restaurant);
        return ResponseEntity.ok().build();
    }

    // Endpoint to delete a restaurant
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) throws RestaurantNotFoundException {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok().build();
    }

    // Endpoint to add DeliveryPerson to a restaurant
    @PostMapping("/add-delivery-person/{restaurantId}/{deliveryPersonId}")
    public ResponseEntity<Restaurant> addDeliveryPersonToRestaurant(@PathVariable Long restaurantId, @PathVariable Long deliveryPersonId) {
        Restaurant updatedRestaurant = restaurantService.addDeliveryPersonToRestaurant(restaurantId, deliveryPersonId);
        return ResponseEntity.ok(updatedRestaurant);
    }

    // Endpoint to get the list of DeliveryPersons for a restaurant
    @GetMapping("/delivery-persons/{restaurantId}")
    public ResponseEntity<List<DeliveryPerson>> getDeliveryPersonsForRestaurant(@PathVariable Long restaurantId) {
        List<DeliveryPerson> deliveryPersons = restaurantService.getDeliveryPersonsForRestaurant(restaurantId);
        return ResponseEntity.ok(deliveryPersons);
    }
}
