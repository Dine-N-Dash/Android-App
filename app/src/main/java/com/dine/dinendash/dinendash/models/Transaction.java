package com.dine.dinendash.dinendash.models;

import androidx.lifecycle.MutableLiveData;

import androidx.annotation.NonNull;

public class Transaction {
    private MutableLiveData<String> name;
    private MutableLiveData<String> phoneNumber;
    private MutableLiveData<Double> total;
    private MutableLiveData<Boolean> completed;
    private double tipPercent;

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
            tipPercent = percent;
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
        getPhoneNumber().setValue(phoneNumber);
    }

    public MutableLiveData<Double> getTotal() {
        if (total == null) {
            total = new MutableLiveData<>();
        }

        return total;
    }

    public void setTotal(Double total) {
        getTotal().setValue(total);
    }

    public MutableLiveData<Boolean> getCompleted() {
        if (completed == null) {
            completed = new MutableLiveData<>();
        }

        return completed;
    }

    public void setCompleted(Boolean completed) {
        getCompleted().setValue(completed);
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
}
