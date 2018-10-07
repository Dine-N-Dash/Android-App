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
import com.dine.dinendash.dinendash.databinding.FragmentHistoryBinding;
import com.dine.dinendash.dinendash.viewModels.ReceiptHistoryViewModel;

public class History extends Fragment {
    private ReceiptHistoryViewModel viewModel;

    public History() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(ReceiptHistoryViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHistoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
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