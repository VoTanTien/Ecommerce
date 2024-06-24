package com.example.ecommerce.helper;

import android.content.Context;

import com.example.ecommerce.Domain.ItemsDomain;

import java.util.ArrayList;

public class ManagementCart {

    public static ManagementCart instance = new ManagementCart();

    private ArrayList<ItemsDomain> items;
    private ManagementCart() {
        items = new ArrayList<>();
    }

    public ArrayList<ItemsDomain> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }

    public void insertItems(ItemsDomain item) {
        for(ItemsDomain element : items) {
            if (element.getTitle().equals(item.getTitle())) {
                return;
            }
        }

        item.setNumberInCart(1);
        items.add(item);
    }

    public int count() {
        return items.size();
    }

    public double getTotalFee() {
        double fee = 0;

        for(ItemsDomain item : items) {
            fee += item.getPrice() * item.getNumberInCart();
        }

        return fee;
    }

    public void minusNumberItem(int position, ChangeNumberItemsListener changeNumberListenter) {
        int number = items.get(position).getNumberInCart();
        if (number == 1) {
            items.remove(position);
        }
        else {
            items.get(position).setNumberInCart(number - 1);
        }

        changeNumberListenter.change();
    }

    public void plusNumberItem(int position, ChangeNumberItemsListener changeNumberListenter) {
        items.get(position).setNumberInCart(items.get(position).getNumberInCart() + 1);
        changeNumberListenter.change();
    }

}
