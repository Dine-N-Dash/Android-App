package com.dine.dinendash.dinendash.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
