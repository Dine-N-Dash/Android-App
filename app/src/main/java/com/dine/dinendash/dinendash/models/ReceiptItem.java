package com.dine.dinendash.dinendash.models;

public class ReceiptItem {
    private String name;
    private double price;
    private String owner;
    //private double tax;
    //private double tip;

    public  ReceiptItem() {
        this("", 0.0);
    }

    public ReceiptItem(String name, double price) {
        this.name = name;
        this.price = price;
        this.owner = "";
        //this.tax = 0;
        //this.tip = 0;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return  owner;
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

    public void setOwner(String owner) {
        this.owner = owner;
    }

    /* Add these later if we decide to calculate tax and tip per item instead of per user
    public double getTax() {
        return tax;
    }

    public double getTip() {
        return tip;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setTip(double tip) {
        this.tip = tip;
    }*/
}
