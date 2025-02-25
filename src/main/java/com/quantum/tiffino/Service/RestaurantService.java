package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.DeliveryPerson;
import com.quantum.tiffino.Entity.Restaurant;
import com.quantum.tiffino.Exception.RestaurantNotFoundException;

import java.util.List;

public interface RestaurantService {
    Restaurant createRestaurant(Restaurant restaurant);
    Restaurant getRestaurantById(Long id) throws RestaurantNotFoundException;
    List<Restaurant> getAllRestaurants();
    void updateRestaurant(Long id, Restaurant restaurant) throws RestaurantNotFoundException;
    void deleteRestaurant(Long id) throws RestaurantNotFoundException;
     Restaurant addDeliveryPersonToRestaurant(Long restaurantId, Long deliveryPersonId) ;

    List<DeliveryPerson> getDeliveryPersonsForRestaurant(Long restaurantId);
}
