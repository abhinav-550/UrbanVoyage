package com.urbanvoyage.ecom.models;

import com.urbanvoyage.ecom.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;

@Document(collection = "orders")
public class Order {
//        public enum Status {
//            Pending, Shipping, Completed, Cancelled
//        }

    @Id
    private String id;
    private ArrayList<Item> items;
    private String status;
    private String orderedAt;
    private String userId;
    private String shippingAddress;
    private long totalCost;

    // Constructors
    public Order() {}

    public Order(String id, ArrayList<Item> items, String status, String orderedAt, String userId, String shippingAddress, long totalCost) {
        this.id = id;
        this.items = items;
        this.status = status;
        this.orderedAt =   Utility.getCurrentDate();
        this.userId = userId;
        this.shippingAddress = shippingAddress;
        this.totalCost = totalCost;
    }

    // Getters and Setters
    public String getOrderId() {
        return id;
    }

    public void setOrderId(String id) {
        this.id = id;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(String orderedAt) {
        this.orderedAt = orderedAt;
    }

    public long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(long cost) {
        this.totalCost = cost;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
