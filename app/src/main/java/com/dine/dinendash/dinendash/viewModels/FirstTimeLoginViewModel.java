package com.dine.dinendash.dinendash.viewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class FirstTimeLoginViewModel extends ViewModel {
    private MutableLiveData<String> username;

    public FirstTimeLoginViewModel() {

    }

    public MutableLiveData<String> getUsername() {
        if(username == null) {
            username = new MutableLiveData<>();
        }

        return username;
    }

    public void setUsername(String username) {
        getUsername().setValue(username);
    }
}
