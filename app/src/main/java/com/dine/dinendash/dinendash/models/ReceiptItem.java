// Written by: Ryan Filkins
// Tested by: Ryan Filkins

package com.dine.dinendash.dinendash.models;

public class ReceiptItem {
    private String name;
    private double price;
    private Transaction owner;

    public  ReceiptItem() {
        this("", 0.0);
    }

    public ReceiptItem(String name, double price) {
        this.name = name;
        this.price = price;
        this.owner = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Transaction getOwner() {
        return  owner;
    }

    public void setOwner(Transaction owner) {
        this.owner = owner;
    }
}
