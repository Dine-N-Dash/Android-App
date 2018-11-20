package com.dine.dinendash.dinendash.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.FragmentHistoryBinding;
import com.dine.dinendash.dinendash.fragments.adapters.ReceiptListAdapter;
import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.viewModels.ReceiptHistoryViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

public class History extends Fragment {
    private ReceiptHistoryViewModel viewModel;

    public History() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the correct view model
        if (getActivity() != null) {
            viewModel = ViewModelProviders.of(getActivity()).get(ReceiptHistoryViewModel.class);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHistoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        View view = binding.getRoot();

        // Bind fragment and view model to view
        binding.setViewModel(viewModel);
        binding.setFragment(this);
        binding.setLifecycleOwner(this);

        // Set up the RecyclerView
        binding.receiptListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.receiptListRecyclerView.setAdapter(new ReceiptListAdapter(viewModel, this, this));

        return view;
    }

    public void receiptSelected(Receipt receipt) {

        int index = viewModel.getReceipts().getValue().indexOf(receipt);
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);

        Navigation.findNavController(getView()).navigate(R.id.action_history_to_historyTransactions, bundle);
    }

}