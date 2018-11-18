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

        // Update UI when transactions change
        this.viewModel.getReceipt().getValue().getTransactions().observe(owner, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                notifyDataSetChanged();
            }
        });

        // Prevents items from being duplicated and shuffled as the RecyclerView tries to reuse ViewHolders
        setHasStableIds(true);
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
        if (viewModel.getReceipt().getValue().getTransactions().getValue() != null) {
            // Bind view model, fragment, and correct transaction to ViewHolder
            transactionItemsViewHolder.binding.setItem(viewModel.getReceipt().getValue().getTransactions().getValue().get(i));
            transactionItemsViewHolder.binding.setViewModel(viewModel);
            transactionItemsViewHolder.binding.setFragment(paymentFragment);
        }
    }

    @Override
    public int getItemCount() {
        if(viewModel.getReceipt().getValue().getTransactions().getValue() != null) {
            return viewModel.getReceipt().getValue().getTransactions().getValue().size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        // Prevents items from being duplicated and shuffled as the RecyclerView tries to reuse ViewHolders
        return position;
    }

    public static class TransactionItemsViewHolder extends RecyclerView.ViewHolder {
        public final TransactionItemBinding binding;

        public TransactionItemsViewHolder(TransactionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
