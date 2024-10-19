package com.urbanvoyage.ecom.services.interfaces;

public interface WishlistServiceInterface {
    void addWishlistItem(String userId, String itemId);
    void removeWishlistItem(String userId, String itemId);
}
