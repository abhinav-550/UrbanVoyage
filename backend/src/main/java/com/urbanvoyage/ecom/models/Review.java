package com.urbanvoyage.ecom.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.urbanvoyage.ecom.utils.Utility;
import java.util.ArrayList;

@Document(collection = "reviews")
public class Review {
    @Id
    private String id;

    private int rating;
    private String reviewDescription;
    private ArrayList<String> ratingImages;
    private String createdAt;
    private String userId;
    private String itemId;


    public Review() {}

    public Review(String id, String reviewDescription, int rating , ArrayList<String> ratingImages, String userId, String itemId) {
        this.id = id;
        this.reviewDescription = reviewDescription;
        this.ratingImages = ratingImages;
        this.userId = userId;
        this.createdAt = Utility.getCurrentDate();
        this.itemId = itemId;
        this.rating = rating;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating= rating;
    }


    public String getReviewDescription() {
        return reviewDescription;
    }

    public void setReviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
    }

    public ArrayList<String> getRatingImages() {
        return ratingImages;
    }

    public void setRatingImages(ArrayList<String> ratingImages) {
        this.ratingImages = ratingImages;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }


}
