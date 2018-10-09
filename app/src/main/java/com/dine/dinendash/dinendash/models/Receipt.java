package com.dine.dinendash.dinendash.models;

import java.util.ArrayList;

public class Receipt {
    private ArrayList<ReceiptItem> items;

    public Receipt() {

    }

    public void AddItem(ReceiptItem item) {
        getItems().add(item);
    }

    public ArrayList<ReceiptItem> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }

        return items;
    }

    public void setItems(ArrayList<ReceiptItem> items) {
        this.items = items;
    }
}
