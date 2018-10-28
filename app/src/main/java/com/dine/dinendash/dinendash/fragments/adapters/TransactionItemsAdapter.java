package com.dine.dinendash.dinendash.fragments.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.TransactionItemBinding;
import com.dine.dinendash.dinendash.fragments.Payment;
import com.dine.dinendash.dinendash.models.Transaction;
import com.dine.dinendash.dinendash.viewModels.NewReceiptViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionItemsAdapter extends RecyclerView.Adapter<TransactionItemsAdapter.TransactionItemsViewHolder> {
    private LayoutInflater inflater;
    private final NewReceiptViewModel viewModel;
    private final Payment paymentFragment;

    public TransactionItemsAdapter(NewReceiptViewModel viewModel, LifecycleOwner owner, Payment paymentFragment) {
        this.viewModel = viewModel;
        this.paymentFragment = paymentFragment;

        this.viewModel.getTransactions().observe(owner, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
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
        if (viewModel.getTransactions().getValue() != null) {
            transactionItemsViewHolder.binding.setItem(viewModel.getTransactions().getValue().get(i));
            transactionItemsViewHolder.binding.setViewModel(viewModel);
            transactionItemsViewHolder.binding.setFragment(paymentFragment);
        }
    }

    @Override
    public int getItemCount() {

        if(viewModel.getTransactions().getValue() != null) {
            return viewModel.getTransactions().getValue().size();
        }
        return 0;
    }

    public static class TransactionItemsViewHolder extends RecyclerView.ViewHolder {
        public final TransactionItemBinding binding;

        public TransactionItemsViewHolder(TransactionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
