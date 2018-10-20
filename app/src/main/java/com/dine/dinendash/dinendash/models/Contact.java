package com.dine.dinendash.dinendash.models;

import android.arch.lifecycle.MutableLiveData;

public class Contact {
    private MutableLiveData<String> name;
    private MutableLiveData<String> phoneNumber;

    public Contact() {
        this("", "");
    }

    public Contact(String name, String phoneNumber){
        setName(name);
        setPhoneNumber(phoneNumber);
    }

    public MutableLiveData<String> getName() {
        if (name == null) {
            name = new MutableLiveData<>();
        }

        return name;
    }

    public void setName(String name) {
        getName().setValue(name);
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
}
