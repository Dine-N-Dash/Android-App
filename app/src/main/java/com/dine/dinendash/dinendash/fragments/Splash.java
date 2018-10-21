package com.dine.dinendash.dinendash.fragments;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.FragmentSplashBinding;

import androidx.navigation.Navigation;

public class Splash extends Fragment {

    private String username;

    public Splash() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }catch (Exception e){}

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSplashBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false);
        View view = binding.getRoot();
        binding.setFragment(this);

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        username = sharedPreferences.getString("username", "");

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {

        // Show splash screen with app logo until timer runs out
        CountDownTimer timer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
                // Do nothing
            }
            @Override
            public void onFinish() {
                if(username.isEmpty()) {
                    if(getView()!=null) {
                        Navigation.findNavController(getView()).navigate(R.id.action_splash_to_firstTimeLogin, null);
                    }
                }
                else{
                    if(getView()!=null) {
                        Navigation.findNavController(getView()).navigate(R.id.action_splash_to_options, null);
                    }
                }
            }
        }.start();

        super.onResume();
    }
}
