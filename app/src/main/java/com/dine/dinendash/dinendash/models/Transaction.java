package com.dine.dinendash.dinendash.models;

import androidx.lifecycle.MutableLiveData;

import androidx.annotation.NonNull;

public class Transaction {
    private MutableLiveData<String> name;
    private MutableLiveData<String> phoneNumber;
    private MutableLiveData<Double> total;
    private MutableLiveData<Boolean> completed;

    public Transaction() {
        this("", "");
    }

    public Transaction(String name, String phoneNumber){
        setName(name);
        setPhoneNumber(phoneNumber);
        setTotal(0.0);
        setCompleted(false);
    }

    public void addItem(ReceiptItem item) {
        if (getTotal().getValue() != null) {
            getTotal().postValue(getTotal().getValue() + item.getPrice());
            item.setOwner(this);
        }
    }

    public void removeItem(ReceiptItem item) {
        if (getTotal().getValue() != null) {
            getTotal().postValue(getTotal().getValue() - item.getPrice());
            item.setOwner(null);
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

    public MutableLiveData<Boolean> getCompleted() {
        if (completed == null) {
            completed = new MutableLiveData<>();
        }

        return completed;
    }

    public void setCompleted(Boolean completed) {
        getCompleted().postValue(completed);
    }
}