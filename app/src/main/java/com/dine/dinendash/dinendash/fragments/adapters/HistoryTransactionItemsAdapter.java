// Written by: Shelby Heffron

package com.dine.dinendash.dinendash.fragments.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.HistoryTransactionItemBinding;
import com.dine.dinendash.dinendash.fragments.HistoryTransactions;
import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.viewModels.ReceiptHistoryViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryTransactionItemsAdapter extends RecyclerView.Adapter<HistoryTransactionItemsAdapter.HistoryTransactionItemsViewHolder> {
    private final int index;
    private LayoutInflater inflater;
    private final ReceiptHistoryViewModel viewModel;
    private final HistoryTransactions historyTransactionsFragment;

    public HistoryTransactionItemsAdapter(ReceiptHistoryViewModel viewModel, LifecycleOwner owner, HistoryTransactions historyTransactionsFragment, int index) {
        this.viewModel = viewModel;
        this.historyTransactionsFragment = historyTransactionsFragment;
        this.index = index;

        // Update UI when receipt is updated
        this.viewModel.getReceipts().observe(owner, new Observer<List<Receipt>>() {
            @Override
            public void onChanged(List<Receipt> receipts) {
                notifyDataSetChanged();
            }
        });

        // Prevents items from being duplicated and shuffled as the RecyclerView tries to reuse ViewHolders
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public HistoryTransactionItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(inflater == null) {
            inflater = LayoutInflater.from(viewGroup.getContext());
        }
        HistoryTransactionItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.history_transaction_item, viewGroup, false);

        return new HistoryTransactionItemsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryTransactionItemsAdapter.HistoryTransactionItemsViewHolder transactionItemsViewHolder, int i) {
        if (viewModel.getReceipts().getValue() != null && viewModel.getReceipts().getValue().get(index).getTransactions().getValue() != null) {
            // Bind view model, fragment, and correct transaction to ViewHolder
            transactionItemsViewHolder.binding.setItem(viewModel.getReceipts().getValue().get(index).getTransactions().getValue().get(i));
            transactionItemsViewHolder.binding.setViewModel(viewModel);
            transactionItemsViewHolder.binding.setFragment(historyTransactionsFragment);
        }
    }

    @Override
    public int getItemCount() {
        if(viewModel.getReceipts().getValue()!= null && viewModel.getReceipts().getValue().get(index).getTransactions().getValue() != null) {
            return viewModel.getReceipts().getValue().get(index).getTransactions().getValue().size();
        }

        return 0;
    }

    @Override
    public long getItemId(int position) {
        // Prevents items from being duplicated and shuffled as the RecyclerView tries to reuse ViewHolders
        return position;
    }

    public static class HistoryTransactionItemsViewHolder extends RecyclerView.ViewHolder {
        public final HistoryTransactionItemBinding binding;

        public HistoryTransactionItemsViewHolder(HistoryTransactionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

