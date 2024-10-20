package com.urbanvoyage.ecom.services;

import com.urbanvoyage.ecom.models.User;
import com.urbanvoyage.ecom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.urbanvoyage.ecom.services.interfaces.UserServiceInterface;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {

    private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        user.setPassword(bcrypt.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public User findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public void updateUser(String id, User updatedUser) {
        User foundUser = userRepository.findById(id).orElse(null);
        if (foundUser != null) {
            if (updatedUser.getUsername() != null) {
                foundUser.setUsername(updatedUser.getUsername());
            }
            if (updatedUser.getPFP() != null) {
                foundUser.setPFP(updatedUser.getPFP());
            }
            userRepository.save(foundUser);
        }
    }

    @Override
    public void changePassword(String id, String password) {
        User foundUser = userRepository.findById(id).orElseThrow();
        foundUser.setPassword(bcrypt.encode(password));
        userRepository.save(foundUser);
    }

    @Override
    public boolean verifyPassword(String username, String rawPassword) {
        User foundUser = userRepository.findByUsername(username);
        return foundUser != null && bcrypt.matches(rawPassword, foundUser.getPassword());
    }

    @Override
    public void updatedShopDetails(String id, User updatedShopDetails) {
        User foundUser = userRepository.findById(id).orElse(null);
        if (foundUser == null) {
            return;
        }
        foundUser.setShopName(updatedShopDetails.getShopName());
        foundUser.setShopAddress(updatedShopDetails.getShopAddress());
        foundUser.setShopWebsite(updatedShopDetails.getShopWebsite());
        foundUser.setShopDescription(updatedShopDetails.getShopDescription());
        userRepository.save(foundUser);
    }

    @Override
    public void addShopImages(String id, String imageUrl) {
        User foundUser = userRepository.findById(id).orElse(null);
        if (foundUser == null) {
            return;
        }
        foundUser.addShopImages(imageUrl);
        userRepository.save(foundUser);
    }

    @Override
    public void addAddress(String id, String address) {
        User foundUser = userRepository.findById(id).orElse(null);
        if (foundUser == null) {
            return;
        }
        foundUser.addAddress(address);
        userRepository.save(foundUser);
    }

    @Override
    public void setShippingAddress(String id, String address) {
        User foundUser = userRepository.findById(id).orElse(null);
        if (foundUser != null) {
            foundUser.setShippingAddress(address);
            userRepository.save(foundUser);
        }
    }

    @Override
    public void newCartItem(String id, String itemId) {
        User foundUser = userRepository.findById(id).orElse(null);
        if (foundUser == null) {
            return;
        }
        ArrayList<String> cartItems = foundUser.getCart();
        cartItems.add(itemId);
        foundUser.setCart(cartItems);
        userRepository.save(foundUser);
    }

    @Override
    public void deleteCartItem(String id, String itemId) {
        User foundUser = userRepository.findById(id).orElse(null);
        if (foundUser == null) {
            return;
        }
        ArrayList<String> cartItems = foundUser.getCart();
        cartItems = cartItems.stream()
                .filter(currItemId -> !currItemId.equals(itemId))
                .collect(Collectors.toCollection(ArrayList::new));
        foundUser.setCart(cartItems);
        userRepository.save(foundUser);
    }
}
