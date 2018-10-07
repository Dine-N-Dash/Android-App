package com.dine.dinendash.dinendash.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.FragmentOptionsBinding;

public class Options extends Fragment {

    public Options() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentOptionsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_options, container, false);
        View view = binding.getRoot();
        binding.setFragment(this);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void takePhotoPressed() {
        Log.d("PIZZA", "take photo");
    }

    public void uploadPhotoPressed() {
        Log.d("PIZZA", "upload photo");
    }

    public void historyPressed() {
        Log.d("PIZZA", "history");
    }

    public void settingsPressed() {
        Log.d("PIZZA", "settings");
    }
}
