// Written by: Ryan Filkins

package com.dine.dinendash.dinendash.fragments.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.ReceiptItemBinding;
import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.models.Transaction;
import com.dine.dinendash.dinendash.viewModels.NewReceiptViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

public class ReceiptItemsAdapter extends RecyclerView.Adapter<ReceiptItemsAdapter.ReceiptItemsViewHolder> {
    private LayoutInflater inflater;
    private final NewReceiptViewModel viewModel;

    public ReceiptItemsAdapter(NewReceiptViewModel viewModel, LifecycleOwner owner) {
        this.viewModel = viewModel;

        // Update UI when receipt is updated
        this.viewModel.getReceipt().observe(owner, new Observer<Receipt>() {
            @Override
            public void onChanged(@Nullable Receipt receipt) {
                notifyDataSetChanged();
            }
        });

        // Update UI when current transaction is updated
        this.viewModel.getCurrentTransaction().observe(owner, new Observer<Transaction>() {
            @Override
            public void onChanged(Transaction transactions) {
                notifyDataSetChanged();
            }
        });

        // Prevents items from being duplicated and shuffled as the RecyclerView tries to reuse ViewHolders
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ReceiptItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(inflater == null) {
            inflater = LayoutInflater.from(viewGroup.getContext());
        }

        ReceiptItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.receipt_item, viewGroup, false);

        return new ReceiptItemsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptItemsViewHolder receiptItemsViewHolder, int i) {
        if (viewModel.getReceipt().getValue() != null && viewModel.getReceipt().getValue().getItems().getValue() != null) {
            // Bind view model and correct receipt item to ViewHolder
            receiptItemsViewHolder.binding.setItem(viewModel.getReceipt().getValue().getItems().getValue().get(i));
            receiptItemsViewHolder.binding.setViewModel(viewModel);
        }
    }

    @Override
    public int getItemCount() {
        if(viewModel.getReceipt().getValue() != null) {
            Receipt receipt = viewModel.getReceipt().getValue();

            if (receipt.getItems().getValue() != null) {
                return receipt.getItems().getValue().size();
            }
        }

        return 0;
    }

    @Override
    public long getItemId(int position) {
        // Prevents items from being duplicated and shuffled as the RecyclerView tries to reuse ViewHolders
        return position;
    }

    public static class ReceiptItemsViewHolder extends RecyclerView.ViewHolder {
        public final ReceiptItemBinding binding;

        public ReceiptItemsViewHolder(ReceiptItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
