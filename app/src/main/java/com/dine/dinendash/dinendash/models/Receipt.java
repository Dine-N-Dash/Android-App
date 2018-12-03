package com.dine.dinendash.dinendash.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class Receipt implements Serializable {
    private MutableLiveData<List<ReceiptItem>> items;
    private MutableLiveData<List<Transaction>> transactions;
    private MutableLiveData<String> name;

    public Receipt() {

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
        this.getItems().setValue(items);
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
        getTransactions().setValue(transactions);
    }

    public MutableLiveData<String> getName() {
        if (name == null) {
            name = new MutableLiveData<>();
            name.setValue("");
        }

        return name;
    }

    public void setName(String name) {
        getName().setValue(name);
    }
}
