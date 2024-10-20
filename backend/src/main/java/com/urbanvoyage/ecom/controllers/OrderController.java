package com.urbanvoyage.ecom.controllers;

import com.urbanvoyage.ecom.controllers.auth.AuthCookieController;
import com.urbanvoyage.ecom.models.Item;
import com.urbanvoyage.ecom.models.Order;
import com.urbanvoyage.ecom.models.User;
import com.urbanvoyage.ecom.services.ItemService;
import com.urbanvoyage.ecom.services.OrderService;
import com.urbanvoyage.ecom.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    @PostMapping("/cart/checkout")
    public ResponseEntity<String> issueOrder(HttpServletRequest req) {
        String userId = AuthCookieController.getAuthDetail(req, "userId");
        User foundUser = userService.findUserById(userId).orElse(null);

        if (foundUser == null) {
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        }

        ArrayList<Item> allCartItems = new ArrayList<>();

        if (foundUser.getCart() != null && !foundUser.getCart().isEmpty()) {
            ArrayList<String> itemIds = foundUser.getCart();
            long orderCost = 0;

            for (String id : itemIds) {
                Item item = itemService.getASingleItem(id).orElse(null);
                if (item != null) {
                    orderCost = orderCost + item.getPrice();
                    allCartItems.add(item);
                }
            }

            // If no items were found in the cart
            if (allCartItems.isEmpty()) {
                return new ResponseEntity<>("No valid items found in the cart.", HttpStatus.BAD_REQUEST);
            }

            Order newOrder = new Order();
            newOrder.setStatus("Pending");
            newOrder.setTotalCost(orderCost);
            newOrder.setShippingAddress(foundUser.getShippingAddress());
            newOrder.setItems(allCartItems);
            newOrder.setUserId(userId);

            // Clear user's cart after order creation
            foundUser.setCart(new ArrayList<>());
            userService.saveUser(foundUser);

            orderService.createNewOrder(newOrder);
            return new ResponseEntity<>("Order placed successfully.", HttpStatus.OK);
        }

        return new ResponseEntity<>("Cart is empty.", HttpStatus.BAD_REQUEST);
    }
}
