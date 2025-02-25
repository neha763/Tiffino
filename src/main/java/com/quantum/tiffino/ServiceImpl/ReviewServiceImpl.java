package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.Review;
import com.quantum.tiffino.Exception.ReviewNotFoundException;
import com.quantum.tiffino.Repository.ReviewRepository;
import com.quantum.tiffino.Service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review getReviewById(Long id) throws ReviewNotFoundException {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + id));
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getReviewsByRestaurantId(Long restaurantId) throws ReviewNotFoundException {
        List<Review> reviews = reviewRepository.findReviewsByRestaurantId(restaurantId);
        if (reviews.isEmpty()) {
            throw new ReviewNotFoundException("No reviews found for restaurant with id: " + restaurantId);
        }
        return reviews;
    }

    @Override
    public List<Review> getReviewsByUserId(Long userId) throws ReviewNotFoundException {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        if (reviews.isEmpty()) {
            throw new ReviewNotFoundException("No reviews found for user with id: " + userId);
        }
        return reviews;
    }

    @Override
    public List<Review> getReviewsByMenuId(Long menuId) throws ReviewNotFoundException {
        List<Review> reviews = reviewRepository.findReviewsByMenuId(menuId);
        if (reviews.isEmpty()) {
            throw new ReviewNotFoundException("No reviews found for menu with id: " + menuId);
        }
        return reviews;
    }

    @Override
    public Review updateReview(Long id, Review review) throws ReviewNotFoundException, IllegalArgumentException {
        if (review == null || id == null) {
            throw new IllegalArgumentException("Review or ID cannot be null.");
        }

        // Check if the review exists first
        Optional<Review> existingReviewOpt = reviewRepository.findById(id);
        if (!existingReviewOpt.isPresent()) {
            throw new ReviewNotFoundException("Review not found with id: " + id);
        }

        Review existingReview = existingReviewOpt.get();
        existingReview.setRating(review.getRating());
        existingReview.setComment(review.getComment());
        existingReview.setRestaurant(review.getRestaurant());
        existingReview.setMealPlan(review.getMealPlan());

        return reviewRepository.save(existingReview);
    }

    @Override
    public void deleteReview(Long id) throws ReviewNotFoundException {
        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException("Review not found with id: " + id);
        }
        reviewRepository.deleteById(id);
    }
}
