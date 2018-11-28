package com.dine.dinendash.dinendash.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.FragmentAddTipDialogBinding;
import com.dine.dinendash.dinendash.viewModels.NewReceiptViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

public class AddTipDialogFragment extends DialogFragment {
    private NewReceiptViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the correct view model
        if(getActivity()!=null) {
            viewModel = ViewModelProviders.of(getActivity()).get(NewReceiptViewModel.class);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getActivity() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            FragmentAddTipDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_add_tip_dialog, null, false);

            binding.setViewModel(viewModel);
            binding.setFragment(this);
            binding.setLifecycleOwner(this);

            binding.tipPercentPicker.setMinValue(0);
            binding.tipPercentPicker.setMaxValue(99);

            binding.tipPercentPickerDecimal.setMinValue(0);
            binding.tipPercentPickerDecimal.setMaxValue(9);

            builder.setView(binding.getRoot());

            builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    viewModel.addTip();
                }
            });

            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    AddTipDialogFragment.this.getDialog().cancel();
                }
            });

            builder.setTitle(R.string.add_tip);

            return builder.create();
        }

        return super.onCreateDialog(savedInstanceState);
    }
}
