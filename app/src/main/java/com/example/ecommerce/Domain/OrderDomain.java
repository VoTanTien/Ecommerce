package com.example.ecommerce.Domain;

import java.util.ArrayList;
import java.util.List;

public class OrderDomain {
    public String date;

    public String itemsId;

    public int total;

    public String userId;
    private List<ItemsDomain> items;

    public OrderDomain(String date, String itemsId, int total, String userId) {
        this.date = date;
        this.itemsId = itemsId;
        this.total = total;
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItemsId() {
        return itemsId;
    }

    public void setItemsId(String itemsId) {
        this.itemsId = itemsId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public OrderDomain() {

    }

    public List<ItemsDomain> getItems() {
        if (items == null) {
            items = new ArrayList<>(); // Khởi tạo items nếu nó chưa được khởi tạo
        }
        return items;
    }

    public void setItems(List<ItemsDomain> items) {
        this.items = items;
    }
}
