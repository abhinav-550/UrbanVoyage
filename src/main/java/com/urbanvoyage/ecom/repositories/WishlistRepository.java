package com.urbanvoyage.ecom.repositories;

import com.urbanvoyage.ecom.models.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends MongoRepository<Wishlist, String> {
    public Wishlist findByUserId(String userId);
}
