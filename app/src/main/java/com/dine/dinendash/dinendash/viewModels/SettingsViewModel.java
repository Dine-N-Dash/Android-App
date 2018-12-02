package com.dine.dinendash.dinendash.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<String> username;

    public SettingsViewModel() {
    }

    public MutableLiveData<String> getUsername() {
        if(username == null) {
            username = new MutableLiveData<>();
        }

        return username;
    }

    public void setUsername(String username) {
        getUsername().postValue(username);
    }
}
