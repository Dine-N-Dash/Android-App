package com.dine.dinendash.dinendash.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.FragmentSplashBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class Splash extends Fragment {

    private String username;

    public Splash() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the action bar so it doesn't interfere with the Splash screen
        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        } catch (Exception e){
            Log.e("SplashError", e.getMessage());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSplashBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false);
        View view = binding.getRoot();

        // Bind fragment to View
        binding.setFragment(this);

        // Retrieve username from SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        username = sharedPreferences.getString("username", "");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Show splash screen with app logo until timer runs out
        CountDownTimer timer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
                // Do nothing
            }
            @Override
            public void onFinish() {
                if (username.isEmpty()) {
                    // If there is no saved username, navigate to the login page
                    if(getView()!=null) {
                        Navigation.findNavController(getView()).navigate(R.id.action_splash_to_firstTimeLogin, null);
                    }
                }
                else {
                    // Otherwise, navigate to the options page
                    if(getView()!=null) {
                        Navigation.findNavController(getView()).navigate(R.id.action_splash_to_options, null);
                    }
                }
            }
        };

        timer.start();
    }
}
