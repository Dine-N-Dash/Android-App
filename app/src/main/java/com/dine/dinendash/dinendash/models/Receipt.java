package com.dine.dinendash.dinendash.models;

import java.util.ArrayList;

public class Receipt {
    private ArrayList<ReceiptItem> items;
    private double subTotal;
    private double totalTax;
    private double total;

    public Receipt() {
        items = null;
        subTotal = -1;
        totalTax = 0;
        total = 0;
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

    public void setSubTotal(double subTotal){ this.subTotal = subTotal; }

    public void setTotal(double Total){ this.total = Total; }

    public void setTotalTax(double totalTax){ this.totalTax = totalTax; }

    /* Add later for tax and tip per item instead of per user
    public void calcTax(){
        double runningTotal = 0;
        double itemTax = 0;
        if(items != null && totalTax != 0 && subTotal != 0){
            for(int i = 0; i < items.size(); i++){
                runningTotal += ((itemTax = items.get(i).getPrice() / subTotal)*totalTax);
                items.get(i).setTax(itemTax);
            }
            if(runningTotal != totalTax){
                items.get(items.size() - 1).setTax(items.get(items.size() - 1).getTax() + totalTax - runningTotal);
            }
        }
    }*/
}
