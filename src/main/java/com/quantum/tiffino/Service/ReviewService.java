package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.Review;
import com.quantum.tiffino.Exception.ReviewNotFoundException;


import java.util.List;


public interface ReviewService {

    Review saveReview(Review review);
    Review getReviewById(Long id) throws ReviewNotFoundException;
    List<Review> getAllReviews();
    List<Review> getReviewsByRestaurantId(Long restaurantId) throws ReviewNotFoundException;
    List<Review> getReviewsByUserId(Long userId) throws ReviewNotFoundException;
    List<Review> getReviewsByMenuId(Long menuId) throws ReviewNotFoundException;
    Review updateReview(Long id, Review review) throws ReviewNotFoundException, IllegalArgumentException;
    void deleteReview(Long id) throws ReviewNotFoundException;
}
