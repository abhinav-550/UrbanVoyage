package com.urbanvoyage.ecom.services;

import com.urbanvoyage.ecom.models.Wishlist;
import com.urbanvoyage.ecom.repositories.WishlistRepository;
import com.urbanvoyage.ecom.services.interfaces.WishlistServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WishlistService implements WishlistServiceInterface {
    @Autowired
    WishlistRepository wishlistRepository;

    @Override
    public void addWishlistItem(String userId, String itemId) {
        Wishlist foundWishlist = wishlistRepository.findByUserId(userId);
        ArrayList<String> itemList;

        if (foundWishlist == null) {
            foundWishlist = new Wishlist();
            foundWishlist.setUserId(userId);
            itemList = new ArrayList<>();
        } else {
            itemList = foundWishlist.getItemIds();
            if (itemList == null) {
                itemList = new ArrayList<>();
            }
        }

        itemList.add(itemId);
        foundWishlist.setItemIds(itemList);
        wishlistRepository.save(foundWishlist);
    }

    @Override
    public void removeWishlistItem(String userId, String itemId) {
        Wishlist foundWishlist = wishlistRepository.findByUserId(userId);
        if (foundWishlist != null) {
            ArrayList<String> itemList = foundWishlist.getItemIds();
            if (itemList != null) {
                itemList.removeIf(existingItemId -> existingItemId.equals(itemId));
                foundWishlist.setItemIds(itemList);
            }
            wishlistRepository.save(foundWishlist);
        }
    }
}
