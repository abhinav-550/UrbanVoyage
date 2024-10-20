package com.urbanvoyage.ecom.services;

import com.urbanvoyage.ecom.models.Review;
import com.urbanvoyage.ecom.repositories.ReviewRepository;
import com.urbanvoyage.ecom.services.interfaces.ReviewServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class ReviewService implements ReviewServiceInterface {
    @Autowired
    ReviewRepository reviewRepository;
    @Override
    public void addReview(Review review){
        reviewRepository.save(review);
    }
    @Override
    public void deleteReview(String reviewId){
        reviewRepository.deleteById(reviewId);
    }
    @Override
    public void updatedReview(String reviewId, Review updatedReview){
        Review foundReview = reviewRepository.findById(reviewId).orElse(null);
        if(foundReview != null){
            foundReview.setReviewDescription(updatedReview.getReviewDescription());
            foundReview.setRating(updatedReview.getRating());
            reviewRepository.save(foundReview);
        }
    }
    @Override
    public ArrayList<Review> getAllReviews(String userId){
        return new ArrayList<>(reviewRepository.findByUserId(userId));
    }
}
