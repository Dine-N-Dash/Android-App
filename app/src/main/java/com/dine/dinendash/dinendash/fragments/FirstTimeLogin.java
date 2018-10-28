package com.dine.dinendash.dinendash.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.FragmentFirstTimeLoginBinding;
import com.dine.dinendash.dinendash.viewModels.FirstTimeLoginViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

public class FirstTimeLogin extends Fragment {

    private FirstTimeLoginViewModel viewModel;

    public FirstTimeLogin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        } catch (Exception e){
            Log.e("FirstTimeLoginError", e.getMessage());
        }
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(FirstTimeLoginViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentFirstTimeLoginBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first_time_login, container, false);
        View view = binding.getRoot();
        binding.setFragment(this);
        binding.setViewmodel(viewModel);
        return view;
    }

    public void continueButtonPressed() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", viewModel.getUsername().getValue());
        editor.apply();

        if(getView()!=null) {
            Navigation.findNavController(getView()).navigate(R.id.action_firstTimeLogin_to_options, null);
        }
    }


}
