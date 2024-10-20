package com.urbanvoyage.ecom.services.interfaces;

import com.urbanvoyage.ecom.models.Item;
import com.urbanvoyage.ecom.models.User;
import java.util.Optional;

public interface UserServiceInterface{
    //Common
    User createUser(User user);
    Optional<User> findUserById(String id);
    public void saveUser(User user);
    // Normal User
    void updateUser(String id , User updatedUser);
    void changePassword(String id , String password);
    boolean verifyPassword(String id , String rawPassword);
    void addAddress(String id, String address);
    void newCartItem(String id, String itemId);
    void deleteCartItem(String id, String itemId);
    void setShippingAddress(String id,String address);
    public User findUserByUsername(String username);

    //Seller Details
    void updatedShopDetails(String id , User updatedShopDetails);
    void addShopImages(String id, String imageUrl);
}