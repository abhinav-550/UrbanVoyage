package com.urbanvoyage.ecom.services;

import com.urbanvoyage.ecom.models.Order;
import com.urbanvoyage.ecom.repositories.OrderRepository;
import com.urbanvoyage.ecom.services.interfaces.OrderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements OrderServiceInterface {
    @Autowired
    OrderRepository orderRepository;

    @Override
    public void createNewOrder(Order order){
        orderRepository.save(order);
    }
    @Override
    public void cancelOrder(String userId , String orderId){
        Order foundOrder = orderRepository.findById(orderId).orElse(null);
        if(foundOrder != null && userId.equals(foundOrder.getOrderId())){
            foundOrder.setStatus(Order.Status.Cancelled);
            orderRepository.save(foundOrder);
        }
    }

    @Override
    public Optional<Order> getOrderDetails(String userId , String orderId){
        Order foundOrder = orderRepository.findById(orderId).orElse(null);
        if(foundOrder != null && userId.equals(foundOrder.getUserId())){
            return Optional.of(foundOrder);
        }
        return Optional.empty();
    }

    @Override
    public ArrayList<Order> getAllOrders(String userId, String filter) {
        List<Order> allOrders = orderRepository.findByUserId(userId);

        //default case
        if ("All".equalsIgnoreCase(filter)) {
            return new ArrayList<>(allOrders);
        }

        List<Order> filteredOrders = allOrders.stream()
                .filter(order -> order.getStatus().name().equalsIgnoreCase(filter))
                .toList();

        return new ArrayList<>(filteredOrders);
    }
}
