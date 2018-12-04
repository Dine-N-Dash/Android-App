// Written by: Ryan Filkins & Shelby Heffron
// Tested by: Ryan Filkins

package com.dine.dinendash.dinendash.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class Receipt implements Serializable {

    private MutableLiveData<List<ReceiptItem>> items;
    private MutableLiveData<List<Transaction>> transactions;
    private MutableLiveData<String> name;

    // NOTE: These fields and their corresponding getters and setters are not included in Report 3 as they were added after it was submitted
    // They are necessary due to the difficulty of storing LiveData objects in a Firebase database
    private List<ReceiptItem> dbItems;
    private List<Transaction> dbTransactions;
    private String dbName;

    public Receipt() {

    }

    @Exclude
    public void addItem(ReceiptItem item) {
        if (getItems().getValue() != null) {
            getItems().getValue().add(item);
        }

        setItems(getItems().getValue());
    }

    @Exclude
    public MutableLiveData<List<ReceiptItem>> getItems() {
        if (items == null) {
            items = new MutableLiveData<>();
            items.setValue(new ArrayList<ReceiptItem>());
        }

        return items;
    }

    @Exclude
    public void setItems(List<ReceiptItem> items) {
        this.getItems().setValue(items);
        dbItems = items;
    }

    @Exclude
    public void addTransaction(Transaction transaction) {
        if (getTransactions().getValue() != null) {
            getTransactions().getValue().add(transaction);
        }

        setTransactions(getTransactions().getValue());
    }

    @Exclude
    public MutableLiveData<List<Transaction>> getTransactions() {
        if(transactions == null) {
            transactions = new MutableLiveData<>();
            transactions.setValue(new ArrayList<Transaction>());
        }

        return transactions;
    }

    @Exclude
    public void setTransactions(List<Transaction> transactions) {
        getTransactions().setValue(transactions);
        dbTransactions = transactions;
    }

    @Exclude
    public MutableLiveData<String> getName() {
        if (name == null) {
            name = new MutableLiveData<>();
            name.setValue("");
        }

        return name;
    }

    @Exclude
    public void setName(String name) {
        getName().setValue(name);
        dbName = name;
    }

    public List<ReceiptItem> getDbItems() {
        return dbItems;
    }

    public void setDbItems(List<ReceiptItem> dbItems) {
        this.dbItems = dbItems;
        setItems(dbItems);
    }

    public List<Transaction> getDbTransactions() {
        return dbTransactions;
    }

    public void setDbTransactions(List<Transaction> dbTransactions) {
        this.dbTransactions = dbTransactions;
        setTransactions(dbTransactions);
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
        setName(dbName);
    }
}
