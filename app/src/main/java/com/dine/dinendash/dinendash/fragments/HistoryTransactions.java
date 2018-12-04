// Written by: Shelby Heffron

package com.dine.dinendash.dinendash.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.FragmentHistoryTransactionsBinding;
import com.dine.dinendash.dinendash.fragments.adapters.HistoryTransactionItemsAdapter;
import com.dine.dinendash.dinendash.viewModels.ReceiptHistoryViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryTransactions extends Fragment {
    private ReceiptHistoryViewModel viewModel;
    private int index;

    public HistoryTransactions() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the correct view model
        if (getActivity() != null) {
            viewModel = ViewModelProviders.of(getActivity()).get(ReceiptHistoryViewModel.class);
        }

        // If a index was sent, display the transactions for that index of the receipts in the viewmodel
        if (getArguments() != null) {
            this.index = getArguments().getInt("index");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHistoryTransactionsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history_transactions, container, false);
        View view = binding.getRoot();

        // Bind fragment and view model to view
        binding.setViewModel(viewModel);
        binding.setFragment(this);
        binding.setLifecycleOwner(this);


        // Set up the RecyclerView
        binding.historyTransactionsListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.historyTransactionsListRecyclerView.setAdapter(new HistoryTransactionItemsAdapter(viewModel, this, this, index));

        //add horizontal line diveders
        RecyclerView recyclerView = view.findViewById(R.id.historyTransactionsListRecyclerView);

        if (getContext() != null) {
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        }

        return view;
    }
}
