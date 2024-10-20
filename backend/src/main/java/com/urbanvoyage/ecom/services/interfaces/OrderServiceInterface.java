package com.urbanvoyage.ecom.services.interfaces;


import com.urbanvoyage.ecom.models.Order;

import java.util.ArrayList;
import java.util.Optional;

public interface OrderServiceInterface {
    void createNewOrder(Order order);
    void cancelOrder(String userId , String orderId);
    Optional<Order> getOrderDetails(String userId , String orderId);
    ArrayList<Order> getAllOrders(String userId, String filter);
}
