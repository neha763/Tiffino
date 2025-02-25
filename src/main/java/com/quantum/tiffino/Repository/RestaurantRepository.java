package com.quantum.tiffino.Repository;

import com.quantum.tiffino.Entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {


    Optional<Restaurant> findByName(String restaurantName);

    List<Restaurant> findByMenus_ItemNameContaining(String itemName);
}

