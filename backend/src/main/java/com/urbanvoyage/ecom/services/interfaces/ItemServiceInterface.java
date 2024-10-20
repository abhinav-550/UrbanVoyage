package com.urbanvoyage.ecom.services.interfaces;

import com.urbanvoyage.ecom.models.Item;

import java.util.ArrayList;
import java.util.Optional;

public interface ItemServiceInterface {
    //User
    Optional<Item> getASingleItem(String itemId);
    ArrayList<Item> getAllItems();
    //Seller
    public void saveItem(Item item);
    public ArrayList<Item> searchItems(String query);
    public Item createItem(Item newItem);
    void updateItemDetails(String itemId, Item updatedItem);
    void deleteItem(String userId, String itemId);
}
