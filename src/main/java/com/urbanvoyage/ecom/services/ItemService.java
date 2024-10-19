package com.urbanvoyage.ecom.services;

import com.urbanvoyage.ecom.models.Item;
import com.urbanvoyage.ecom.repositories.ItemRepository;
import com.urbanvoyage.ecom.services.interfaces.ItemServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService implements ItemServiceInterface {

    @Autowired
    private ItemRepository itemRepository;

    // Seller
    @Override
    public void createItem(Item newItem) {
        itemRepository.save(newItem);
    }

    @Override
    public void updateItemDetails(String itemId, Item updatedItem) {
        Item foundItem = itemRepository.findById(itemId).orElse(null);
        if (foundItem != null) {
            if (updatedItem.getName() != null) {
                foundItem.setName(updatedItem.getName());
            }
            if (updatedItem.getPrice() != null) {
                foundItem.setPrice(updatedItem.getPrice());
            }
            if (updatedItem.getDescription() != null) {
                foundItem.setDescription(updatedItem.getDescription());
            }
            if (updatedItem.getQuantity() >= 0) {
                foundItem.setQuantity(updatedItem.getQuantity());
            }
            if (updatedItem.getImages() != null) {
                foundItem.setImages(updatedItem.getImages());
            }
            if (updatedItem.getTags() != null) {
                foundItem.setTags(updatedItem.getTags());
            }
            itemRepository.save(foundItem);
        }
    }


    @Override
    public void deleteItem(String userId, String itemId) {
        Item foundItem = itemRepository.findById(itemId).orElse(null);
        if (foundItem != null && foundItem.getSellerId() != null && foundItem.getSellerId().equals(userId)) {
            itemRepository.deleteById(itemId);
        }
    }

    // User
    @Override
    public Optional<Item> getASingleItem(String itemId) {
        return itemRepository.findById(itemId);
    }

    @Override
    public ArrayList<Item> getAllItems() {
        List<Item> allItems = itemRepository.findAll();
        return new ArrayList<>(allItems);
    }
}
