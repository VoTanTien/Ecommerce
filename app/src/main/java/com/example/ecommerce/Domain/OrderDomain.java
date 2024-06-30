package com.example.ecommerce.Domain;

import java.util.ArrayList;
import java.util.List;

public class OrderDomain {
    public String date;

    public String itemsId;

    public double total;

    public String userId;

    public OrderDomain() {

    }

    public OrderDomain(String date, String itemsId, double total, String userId) {
        this.date = date;
        this.itemsId = itemsId;
        this.total = total;
        this.userId = userId;
    }
}
