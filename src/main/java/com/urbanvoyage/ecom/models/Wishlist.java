package com.urbanvoyage.ecom.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "wishlists")
public class Wishlist {
    @Id
    private String id;

    private String userId;
    private ArrayList<String> itemIds = new ArrayList<>();

    public Wishlist() {}

    public Wishlist(String id, String userId, ArrayList<String> itemsId) {
        this.id = id;
        this.userId = userId;
        this.itemIds = itemsId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getItemIds() {
        return itemIds;
    }

    public void setItemIds(ArrayList<String> itemIds) {
        this.itemIds = itemIds;
    }
}
