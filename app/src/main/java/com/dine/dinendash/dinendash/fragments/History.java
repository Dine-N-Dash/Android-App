// Written by: Shelby Heffron

package com.dine.dinendash.dinendash.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.FragmentHistoryBinding;
import com.dine.dinendash.dinendash.fragments.adapters.ReceiptListAdapter;
import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.util.DBController;
import com.dine.dinendash.dinendash.viewModels.ReceiptHistoryViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            String id = sharedPreferences.getString("username", "");

            viewModel = ViewModelProviders.of(getActivity()).get(ReceiptHistoryViewModel.class);
            DBController.getReceipts(id, viewModel);
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

        //add horizontal dividers
        RecyclerView recyclerView = view.findViewById(R.id.receiptListRecyclerView);
        if (getContext() != null) {
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        }

        showBackButton();

        return view;
    }

    public void receiptSelected(Receipt receipt) {
        if (viewModel.getReceipts().getValue() != null) {
            int index = viewModel.getReceipts().getValue().indexOf(receipt);
            Bundle bundle = new Bundle();
            bundle.putInt("index", index);

            if (getView() != null) {
                Navigation.findNavController(getView()).navigate(R.id.action_history_to_historyTransactions, bundle);
            }
        }
    }

    public void showBackButton() {
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();

            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }
}