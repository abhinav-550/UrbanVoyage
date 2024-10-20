package com.urbanvoyage.ecom.models;

import com.urbanvoyage.ecom.utils.Utility;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;

@Document(collection = "items")
public class Item {
    @Id
    private String id;

    private String name;
    private BigDecimal price;
    private int quantity;
    private ArrayList<String> tags;
    private ArrayList<String> images;
    private String createdAt;
    private String description;
    private int saleCount;
    private String sellerId;

    // Default constructor
    public Item() {}

    // Parameterized constructor
    public Item(String id, String name, BigDecimal price, int quantity, ArrayList<String> tags, ArrayList<String> images, String description, int saleCount, String sellerId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.tags = tags;
        this.images = images;
        this.createdAt = Utility.getCurrentDate();

        this.description = description;
        this.saleCount = saleCount;
        this.sellerId = sellerId;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
