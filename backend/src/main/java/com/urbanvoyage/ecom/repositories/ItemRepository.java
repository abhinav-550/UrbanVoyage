package com.urbanvoyage.ecom.repositories;

import com.urbanvoyage.ecom.models.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.ArrayList;

public interface ItemRepository extends MongoRepository<Item, String> {

    @Query("{ $or: [ { 'name': { $regex: ?0, $options: 'i' } }, { 'description': { $regex: ?0, $options: 'i' } } ] }")
    ArrayList<Item> findByNameOrDescriptionRegex(String regex);
}
