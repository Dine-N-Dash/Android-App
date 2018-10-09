package com.dine.dinendash.dinendash.models;

public class ReceiptItem {
    private String name;
    private double price;

    public  ReceiptItem() {
        this("", 0.0);
    }

    public ReceiptItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
