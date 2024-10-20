package com.urbanvoyage.ecom.services.interfaces;

import com.urbanvoyage.ecom.models.Review;

import java.util.ArrayList;

public interface ReviewServiceInterface {
    void addReview(Review review);
    void deleteReview(String reviewId);
    void updatedReview(String reviewId, Review updatedReview);
    ArrayList<Review> getAllReviews(String userId);
}
