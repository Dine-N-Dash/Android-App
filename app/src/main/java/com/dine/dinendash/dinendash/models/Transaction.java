package com.dine.dinendash.dinendash.models;

import com.google.firebase.database.Exclude;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class Transaction {
    private transient MutableLiveData<String> name;
    private transient MutableLiveData<String> phoneNumber;
    private transient MutableLiveData<Double> total;
    private transient MutableLiveData<Boolean> completed;
    private double tipPercent;

    private String dbName;
    private String dbPhoneNumber;
    private double dbTotal;
    private boolean dbCompleted;

    public Transaction() {
        this("", "");
    }

    public Transaction(String name, String phoneNumber){
        setName(name);
        setPhoneNumber(phoneNumber);
        setTotal(0.0);
        setCompleted(false);
        tipPercent = 0.0;
    }

    public void addItem(ReceiptItem item) {
        // Add item total to transaction total and set this transaction as the item's owner
        if (getTotal().getValue() != null) {
            setTotal(getTotal().getValue() + item.getPrice());
            item.setOwner(this);
        }
    }

    public void removeItem(ReceiptItem item) {
        // Subtract item total from transaction total and remove this transaction as the item's owner
        if (getTotal().getValue() != null) {
            setTotal(getTotal().getValue() - item.getPrice());
            item.setOwner(null);
        }
    }

    public void applyTip(double percent) {
        if (total.getValue() != null) {
            double preTax = total.getValue() / (1 + tipPercent);
            double newTotal = preTax + (preTax * percent);
            total.postValue(newTotal);
            dbTotal = newTotal;
            tipPercent = percent;
        }
    }

    @Exclude
    public MutableLiveData<String> getName() {
        if (name == null) {
            name = new MutableLiveData<>();
        }

        return name;
    }

    @Exclude
    public void setName(String name) {
        getName().postValue(name);
        dbName = name;
    }

    @Exclude
    public MutableLiveData<String> getPhoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = new MutableLiveData<>();
        }

        return phoneNumber;
    }

    @Exclude
    public void setPhoneNumber(String phoneNumber) {
        getPhoneNumber().setValue(phoneNumber);
        dbPhoneNumber = phoneNumber;
    }

    @Exclude
    public MutableLiveData<Double> getTotal() {
        if (total == null) {
            total = new MutableLiveData<>();
        }

        return total;
    }

    @Exclude
    public void setTotal(Double total) {
        getTotal().setValue(total);
        dbTotal = total;
    }

    @Exclude
    public MutableLiveData<Boolean> getCompleted() {
        if (completed == null) {
            completed = new MutableLiveData<>();
        }

        return completed;
    }

    @Exclude
    public void setCompleted(Boolean completed) {
        getCompleted().setValue(completed);
        dbCompleted = completed;
    }

    @NonNull
    @Override
    public String toString() {
        if (getName() != null) {
            if (getName().getValue() != null) {
                return getName().getValue();
            }
        }
        return super.toString();
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
        setName(dbName);
    }

    public String getDbPhoneNumber() {
        return dbPhoneNumber;
    }

    public void setDbPhoneNumber(String dbPhoneNumber) {
        this.dbPhoneNumber = dbPhoneNumber;
        setPhoneNumber(dbPhoneNumber);
    }

    public double getDbTotal() {
        return dbTotal;
    }

    public void setDbTotal(double dbTotal) {
        this.dbTotal = dbTotal;
        setTotal(dbTotal);
    }

    public boolean getDbCompleted() {
        return dbCompleted;
    }

    public void setDbCompleted(boolean dbCompleted) {
        this.dbCompleted = dbCompleted;
        setCompleted(dbCompleted);
    }
}
