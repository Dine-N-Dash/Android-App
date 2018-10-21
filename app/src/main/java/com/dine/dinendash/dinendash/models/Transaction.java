package com.dine.dinendash.dinendash.models;

import android.arch.lifecycle.MutableLiveData;

public class Transaction {
    private MutableLiveData<String> name;
    private MutableLiveData<String> phoneNumber;
    private MutableLiveData<Double> total;

    public Transaction() {
        this("", "");
    }

    public Transaction(String name, String phoneNumber){
        setName(name);
        setPhoneNumber(phoneNumber);
        setTotal(0.0);
    }

    public void addItem(ReceiptItem item) {
        if (getTotal().getValue() != null) {
            getTotal().postValue(getTotal().getValue() + item.getPrice());
        }
    }

    public MutableLiveData<String> getName() {
        if (name == null) {
            name = new MutableLiveData<>();
        }

        return name;
    }

    public void setName(String name) {
        getName().postValue(name);
    }

    public MutableLiveData<String> getPhoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = new MutableLiveData<>();
        }

        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        getPhoneNumber().postValue(phoneNumber);
    }

    public MutableLiveData<Double> getTotal() {
        if (total == null) {
            total = new MutableLiveData<>();
        }

        return total;
    }

    public void setTotal(Double total) {
        getTotal().postValue(total);
    }
}
