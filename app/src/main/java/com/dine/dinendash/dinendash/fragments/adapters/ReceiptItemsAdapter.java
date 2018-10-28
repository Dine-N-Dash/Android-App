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
    private NewReceiptViewModel viewModel;

    public ReceiptItemsAdapter(NewReceiptViewModel viewModel, LifecycleOwner owner) {
        this.viewModel = viewModel;

        this.viewModel.getReceipt().observe(owner, new Observer<Receipt>() {
            @Override
            public void onChanged(@Nullable Receipt receipt) {
                notifyDataSetChanged();
            }
        });

        this.viewModel.getCurrentTransaction().observe(owner, new Observer<Transaction>() {
            @Override
            public void onChanged(Transaction transactions) {
                notifyDataSetChanged();
            }
        });

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
        receiptItemsViewHolder.binding.setItem(viewModel.getReceipt().getValue().getItems().getValue().get(i));
        receiptItemsViewHolder.binding.setViewModel(viewModel);
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

    public static class ReceiptItemsViewHolder extends RecyclerView.ViewHolder {
        public ReceiptItemBinding binding;

        public ReceiptItemsViewHolder(ReceiptItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
