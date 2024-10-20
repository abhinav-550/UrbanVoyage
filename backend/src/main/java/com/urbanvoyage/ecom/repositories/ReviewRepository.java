package com.urbanvoyage.ecom.repositories;

import com.urbanvoyage.ecom.models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
    public List<Review> findByUserId(String userId);
}
