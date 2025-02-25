package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.DeliveryPerson;
import com.quantum.tiffino.Entity.Restaurant;
import com.quantum.tiffino.Repository.DeliveryPersonRepository;
import com.quantum.tiffino.Repository.RestaurantRepository;
import com.quantum.tiffino.Service.RestaurantService;
import com.quantum.tiffino.Exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;

    @Override
    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant getRestaurantById(Long id) throws RestaurantNotFoundException {
        return restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with ID: " + id));
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public void updateRestaurant(Long id, Restaurant restaurant) throws RestaurantNotFoundException {
        Restaurant existingRestaurant = restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with ID: " + id));
        existingRestaurant.setName(restaurant.getName());
        existingRestaurant.setLocation(restaurant.getLocation());
        existingRestaurant.setCuisineType(restaurant.getCuisineType());
        existingRestaurant.setIsActive(restaurant.getIsActive());
        existingRestaurant.setHygieneStatus(restaurant.getHygieneStatus());
        existingRestaurant.setDeliveryPerson(restaurant.getDeliveryPerson());
        restaurantRepository.save(existingRestaurant);
    }

    @Override
    public void deleteRestaurant(Long id) throws RestaurantNotFoundException {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with ID: " + id));
        restaurantRepository.delete(restaurant);
    }

    public Restaurant addDeliveryPersonToRestaurant(Long restaurantId, Long deliveryPersonId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        Optional<DeliveryPerson> deliveryPerson = deliveryPersonRepository.findById(deliveryPersonId);

        if (restaurant.isPresent() && deliveryPerson.isPresent()) {
            Restaurant updatedRestaurant = restaurant.get();
            List<DeliveryPerson> deliveryPersons = updatedRestaurant.getDeliveryPerson();
            deliveryPersons.add(deliveryPerson.get());
            updatedRestaurant.setDeliveryPerson(deliveryPersons);
            return restaurantRepository.save(updatedRestaurant);
        } else {
            throw new RuntimeException("Restaurant or DeliveryPerson not found");
        }
    }

    public List<DeliveryPerson> getDeliveryPersonsForRestaurant(Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            return restaurant.get().getDeliveryPerson();
        } else {
            throw new RuntimeException("Restaurant not found");
        }
    }
}
