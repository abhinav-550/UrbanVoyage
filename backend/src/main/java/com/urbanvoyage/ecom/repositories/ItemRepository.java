package com.urbanvoyage.ecom.repositories;

import com.urbanvoyage.ecom.models.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {

}
