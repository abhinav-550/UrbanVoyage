package com.urbanvoyage.ecom.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.File;
import java.util.ArrayList;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    // Enum for roles

    public static enum Role {
        Admin, Seller, User
    }

    // Default user fields
    private String PFP;
    private String username;
    private String email;
    private String password;
    private String shippingAddress;
    private ArrayList<String> address = new ArrayList<>();
    private ArrayList<String> cart = new ArrayList<>();

    // Seller fields
    private String shopName;
    private String shopAddress;
    private ArrayList<String> shopImages = new ArrayList<>();
    private String shopWebsite;
    private String shopDescription;

    private Role role;

    public User() {}

    public User(String id, String PFP, String username, String email, String password, ArrayList<String> address, ArrayList<String> cart, String shopName, String shopAddress, ArrayList<String> shopImages, String shopWebsite, String shopDescription,String shippingAddress, Role role) {
        this.id = id;
        this.PFP = PFP;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.cart = cart;
        this.shippingAddress = shippingAddress;

        //seller
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopImages = shopImages;
        this.shopWebsite = shopWebsite;
        this.shopDescription = shopDescription;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPFP() {
        return PFP;
    }

    public void setPFP(String PFP) {
        this.PFP = PFP;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getAddress() {
        return address;
    }

    public void setAddress(ArrayList<String> address) {
        this.address = address;
    }

    public void addAddress(String address){
        this.address.add(address);
    }

    public ArrayList<String> getCart() {
        return cart;
    }

    public void setCart(ArrayList<String> cart) {
        this.cart = cart;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public ArrayList<String> getShopImages() {
        return shopImages;
    }

    public void addShopImages(String imageUrl){
        this.shopImages.add(imageUrl);
    }

    public void setShopImages(ArrayList<String> shopImages) {
        this.shopImages = shopImages;
    }

    public String getShopWebsite() {
        return shopWebsite;
    }

    public void setShopWebsite(String shopWebsite) {
        this.shopWebsite = shopWebsite;
    }

    public String getShopDescription() {
        return shopDescription;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
