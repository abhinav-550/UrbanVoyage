package com.urbanvoyage.ecom.models;

import com.urbanvoyage.ecom.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "orders")
public class Order {
        public enum Status {
            Pending, Shipping, Completed, Cancelled
        }

    @Id
    private String id;
    private ArrayList<String> items;
    private Status status;
    private String orderedAt;
    private String userId;
    private String shippingAddress;

    // Constructors
    public Order() {}

    public Order(String id, ArrayList<String> items, Status status, String orderedAt, String userId, String shippingAddress) {
        this.id = id;
        this.items = items;
        this.status = status;
        this.orderedAt =   Utility.getCurrentDate();
        this.userId = userId;
        this.shippingAddress = shippingAddress;
    }

    // Getters and Setters
    public String getOrderId() {
        return id;
    }

    public void setOrderId(String id) {
        this.id = id;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(String orderedAt) {
        this.orderedAt = orderedAt;
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
