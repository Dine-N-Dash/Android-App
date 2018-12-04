// Written by: Ryan Filkins

package com.dine.dinendash.dinendash.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.FragmentNameReceiptDialogBinding;
import com.dine.dinendash.dinendash.viewModels.NewReceiptViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

public class NameReceiptDialogFragment extends DialogFragment {
    private NewReceiptViewModel viewModel;
    private NameReceiptDialogListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the correct view model
        if(getActivity()!=null) {
            viewModel = ViewModelProviders.of(getActivity()).get(NewReceiptViewModel.class);
        }
    }

    public void setListener(NameReceiptDialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getActivity() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            FragmentNameReceiptDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_name_receipt_dialog, null, false);

            // Bind fragment and view model to view
            binding.setViewModel(viewModel);
            binding.setFragment(this);
            binding.setLifecycleOwner(this);

            builder.setView(binding.getRoot());

            builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    viewModel.applyReceiptName();
                    listener.onDialogPositiveClick(NameReceiptDialogFragment.this);
                }
            });

            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    NameReceiptDialogFragment.this.getDialog().cancel();
                }
            });

            builder.setTitle(R.string.save_receipt);

            return builder.create();
        }

        return super.onCreateDialog(savedInstanceState);
    }

    // Used to send dialog click event to host fragment
    public interface NameReceiptDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
    }
}
