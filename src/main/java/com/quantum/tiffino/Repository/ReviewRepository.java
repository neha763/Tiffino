package com.quantum.tiffino.Repository;

import com.quantum.tiffino.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    //List<Review> findByMealId(Long mealId);

   // List<Review> findByRestaurant_Id(Long restaurantId);

    List<Review> findByUserId(Long userId);


    List<Review> findReviewsByMenuId(Long menuId);

    @Query("SELECT r FROM Review r")
    List<Review> findAllReviews();
    //List<Review> findByMenuId(Long menuId);

    @Query("SELECT r FROM Review r WHERE r.restaurant.id = :restaurantId")
    List<Review> findReviewsByRestaurantId(@Param("restaurantId") Long restaurantId);
}

