package com.dine.dinendash.dinendash.fragments.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.dine.dinendash.dinendash.models.Transaction;
import com.dine.dinendash.dinendash.viewModels.NewReceiptViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

public class TransactionSpinnerAdapter extends ArrayAdapter<Transaction> implements SpinnerAdapter {
    public TransactionSpinnerAdapter(@NonNull Context context, int resource, LifecycleOwner owner, final NewReceiptViewModel viewModel) {
        super(context, resource, viewModel.getTransactions().getValue());

        viewModel.getTransactions().observe(owner, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                notifyDataSetChanged();
            }
        });
        viewModel.getCurrentTransaction().observe(owner, new Observer<Transaction>() {
            @Override
            public void onChanged(Transaction transaction) {
                notifyDataSetChanged();
            }
        });
    }
}
