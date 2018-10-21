package com.dine.dinendash.dinendash.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.FragmentFirstTimeLoginBinding;
import com.dine.dinendash.dinendash.viewModels.FirstTimeLoginViewModel;

import androidx.navigation.Navigation;

public class FirstTimeLogin extends Fragment {

    private FirstTimeLoginViewModel viewModel;

    public FirstTimeLogin() {
        // Required empty public constructor
    }

    public static FirstTimeLogin newInstance() {
        FirstTimeLogin fragment = new FirstTimeLogin();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }catch (Exception e){}
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(FirstTimeLoginViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentFirstTimeLoginBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first_time_login, container, false);
        View view = binding.getRoot();
        binding.setFragment(this);
        binding.setViewmodel(viewModel);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
