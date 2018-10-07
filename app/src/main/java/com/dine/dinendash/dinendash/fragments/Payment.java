package com.dine.dinendash.dinendash.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.FragmentPaymentBinding;
import com.dine.dinendash.dinendash.viewModels.NewReceiptViewModel;

public class Payment extends Fragment {
    private NewReceiptViewModel viewModel;

    public Payment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(NewReceiptViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentPaymentBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false);
        View view = binding.getRoot();
        binding.setViewModel(viewModel);
        binding.setFragment(this);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}