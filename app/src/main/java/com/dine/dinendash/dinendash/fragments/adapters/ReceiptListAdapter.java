package com.dine.dinendash.dinendash.fragments.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.ReceiptListBinding;
import com.dine.dinendash.dinendash.fragments.History;
import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.viewModels.ReceiptHistoryViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

public class ReceiptListAdapter extends RecyclerView.Adapter<ReceiptListAdapter.ReceiptListViewHolder> {
    private LayoutInflater inflater;
    private final ReceiptHistoryViewModel viewModel;
    private History historyFragment;

    public ReceiptListAdapter(ReceiptHistoryViewModel viewModel, LifecycleOwner owner, History historyFragment) {
        this.viewModel = viewModel;
        this.historyFragment = historyFragment;

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
    public ReceiptListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(inflater == null) {
            inflater = LayoutInflater.from(viewGroup.getContext());
        }

        ReceiptListBinding binding = DataBindingUtil.inflate(inflater, R.layout.receipt_list, viewGroup, false);

        return new ReceiptListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptListViewHolder receiptListViewHolder, int i) {
        if (viewModel.getReceipts().getValue() != null && viewModel.getReceipts().getValue().get(0).getItems().getValue() != null) {
            // Bind view model and correct receipt to ViewHolder
            receiptListViewHolder.binding.setReceipt(viewModel.getReceipts().getValue().get(i));
            receiptListViewHolder.binding.setViewModel(viewModel);
            receiptListViewHolder.binding.setFragment(historyFragment);
        }
    }

    @Override
    public int getItemCount() {
        if(viewModel.getReceipts().getValue() != null) {
            return viewModel.getReceipts().getValue().size();
        }

        return 0;
    }

    @Override
    public long getItemId(int position) {
        // Prevents items from being duplicated and shuffled as the RecyclerView tries to reuse ViewHolders
        return position;
    }

    public static class ReceiptListViewHolder extends RecyclerView.ViewHolder {
        public final ReceiptListBinding binding;

        public ReceiptListViewHolder(ReceiptListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
