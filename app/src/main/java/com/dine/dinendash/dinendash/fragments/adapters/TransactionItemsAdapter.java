package com.dine.dinendash.dinendash.fragments.adapters;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.TransactionItemBinding;
import com.dine.dinendash.dinendash.fragments.Payment;
import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.models.Transaction;
import com.dine.dinendash.dinendash.viewModels.NewReceiptViewModel;

import java.util.List;

public class TransactionItemsAdapter extends RecyclerView.Adapter<TransactionItemsAdapter.TransactionItemsViewHolder> {
    private LayoutInflater inflater;
    private NewReceiptViewModel viewModel;
    private Payment paymentFragment;

    public TransactionItemsAdapter(NewReceiptViewModel viewModel, LifecycleOwner owner, Payment paymentFragment) {
        this.viewModel = viewModel;
        this.paymentFragment = paymentFragment;

        this.viewModel.getReceipt().observe(owner, new Observer<Receipt>() {
            @Override
            public void onChanged(@Nullable Receipt receipt) {
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public TransactionItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(inflater == null) {
            inflater = LayoutInflater.from(viewGroup.getContext());
        }
        TransactionItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.transaction_item, viewGroup, false);

        return new TransactionItemsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionItemsAdapter.TransactionItemsViewHolder transactionItemsViewHolder, int i) {
        transactionItemsViewHolder.binding.setItem(viewModel.getTransactions().getValue().get(i));
        transactionItemsViewHolder.binding.setViewModel(viewModel);
        transactionItemsViewHolder.binding.setFragment(paymentFragment);
    }

    @Override
    public int getItemCount() {

        if(viewModel.getTransactions().getValue() != null) {
            return viewModel.getTransactions().getValue().size();
        }
        return 0;
    }

    public static class TransactionItemsViewHolder extends RecyclerView.ViewHolder {
        public TransactionItemBinding binding;

        public TransactionItemsViewHolder(TransactionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
