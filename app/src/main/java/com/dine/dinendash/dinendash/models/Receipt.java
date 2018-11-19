package com.dine.dinendash.dinendash.models;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class Receipt {
    private MutableLiveData<List<ReceiptItem>> items;
    private MutableLiveData<List<Transaction>> transactions;
    private double subTotal;
    private double totalTax;
    private double total;

    public Receipt() {
        subTotal = 0;
        totalTax = 0;
        total = 0;
    }

    public void addItem(ReceiptItem item) {
        if (getItems().getValue() != null) {
            getItems().getValue().add(item);
        }

        setItems(getItems().getValue());
    }

    public MutableLiveData<List<ReceiptItem>> getItems() {
        if (items == null) {
            items = new MutableLiveData<>();
            items.setValue(new ArrayList<ReceiptItem>());
        }

        return items;
    }

    public void setItems(List<ReceiptItem> items) {
        this.getItems().postValue(items);
    }

    public void addTransaction(Transaction transaction) {
        if (getTransactions().getValue() != null) {
            getTransactions().getValue().add(transaction);
        }

        setTransactions(getTransactions().getValue());
    }

    public MutableLiveData<List<Transaction>> getTransactions() {
        if(transactions == null) {
            transactions = new MutableLiveData<>();
            transactions.setValue(new ArrayList<Transaction>());
        }

        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        getTransactions().postValue(transactions);
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal){ this.subTotal = subTotal; }

    public double getTotal() {
        return total;
    }

    public void setTotal(double Total){ this.total = Total; }

    public double getTotalTax() {
        return totalTax;
    }

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
