package com.dine.dinendash.dinendash.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.FragmentReceiptItemsBinding;
import com.dine.dinendash.dinendash.viewModels.NewReceiptViewModel;

public class ReceiptItems extends Fragment {
    private NewReceiptViewModel viewModel;

    public ReceiptItems() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(NewReceiptViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentReceiptItemsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_receipt_items, container, false);
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
