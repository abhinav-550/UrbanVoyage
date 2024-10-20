package com.urbanvoyage.ecom.services.interfaces;

import com.urbanvoyage.ecom.models.Item;

import java.util.ArrayList;
import java.util.Optional;

public interface ItemServiceInterface {
    //User
    Optional<Item> getASingleItem(String itemId);
    ArrayList<Item> getAllItems();
    //Seller
    public void createItem(Item newItem);
    void updateItemDetails(String itemId, Item updatedItem);
    void deleteItem(String userId, String itemId);
}
