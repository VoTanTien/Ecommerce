package com.example.ecommerce.Domain;

public class ReviewDomain {
    public String Name;
    public String Description;
    public Integer ItemId;
    public String PicUrl;
    public double Rating;

    public ReviewDomain(String name, String description, Integer itemId, String picUrl, double rating) {
        Name = name;
        Description = description;
        ItemId = itemId;
        PicUrl = picUrl;
        Rating = rating;
    }

    public ReviewDomain() {

    }

}
