package com.urbanvoyage.ecom.repositories;

import com.urbanvoyage.ecom.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    public User findByUsername(String username);
}
