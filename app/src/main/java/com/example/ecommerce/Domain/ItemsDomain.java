package com.example.ecommerce.Domain;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemsDomain implements Serializable {
    int itemId;
    String title;
    String description;
    ArrayList<String> picUrl;
    String brand;
    String type;
    int price;
    int oldPrice;
    int review;
    double rating;
    int numberInCart;

    public ItemsDomain() {
    }

    public ItemsDomain(int itemId ,String title, String description, ArrayList<String> picUrl, String brand, String type, int price, int oldPrice, int review, double rating) {
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.brand = brand;
        this.type = type;
        this.price = price;
        this.oldPrice = oldPrice;
        this.review = review;
        this.rating = rating;
        this.itemId = itemId;
    }

    public int getItemId() {return itemId;}
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(ArrayList<String> picUrl) {
        this.picUrl = picUrl;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(int oldPrice) {
        this.oldPrice = oldPrice;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
}
