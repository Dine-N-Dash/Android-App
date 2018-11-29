package com.dine.dinendash.dinendash.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.FragmentPaymentBinding;
import com.dine.dinendash.dinendash.fragments.adapters.TransactionItemsAdapter;
import com.dine.dinendash.dinendash.models.Transaction;
import com.dine.dinendash.dinendash.util.DBController;
import com.dine.dinendash.dinendash.viewModels.NewReceiptViewModel;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

public class Payment extends Fragment implements NameReceiptDialogFragment.NameReceiptDialogListener {
    private NewReceiptViewModel viewModel;

    public Payment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the correct view model
        if(getActivity()!=null) {
            viewModel = ViewModelProviders.of(getActivity()).get(NewReceiptViewModel.class);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentPaymentBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false);

        // Bind fragment and view model to View
        binding.setViewModel(viewModel);
        binding.setFragment(this);
        binding.setLifecycleOwner(this);

        // Set up the RecyclerView
        binding.transactionItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.transactionItemsRecyclerView.setAdapter(new TransactionItemsAdapter(viewModel, this, this));


        return binding.getRoot();
    }

    public void sendMessageTo(Transaction transaction) {
        // Get Paypal.me username from SharedPreferences
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        String username = sharedPreferences.getString("username", "");

        // Construct and send SMS intent
        Uri uri = Uri.parse("smsto:"+transaction.getPhoneNumber().getValue() );
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", "Hi " + transaction.getName().getValue() + "! You owe me $" +
                String.format(Locale.US, "%.2f", transaction.getTotal().getValue()) +
                ". You can pay me using this link: https://www.paypal.me/" + username + "/" +
                String.format(Locale.US, "%.2f", transaction.getTotal().getValue()));
        startActivity(intent);

        // Set transaction to be completed and update binding
        transaction.setCompleted(true);
    }

    public void finishTransactions() {
        NameReceiptDialogFragment dialog = new NameReceiptDialogFragment();
        dialog.setListener(this);

        if (getFragmentManager() != null) {
            dialog.show(getFragmentManager(), "Name");
        }
    }

    public void calculateTip() {
        AddTipDialogFragment dialog = new AddTipDialogFragment();

        if (getFragmentManager() != null) {
            dialog.show(getFragmentManager(), "Tip");
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // Save in database
        if (viewModel.getReceipt().getValue() != null) {
            DBController.addReceipt(viewModel.getReceipt().getValue());
        }

        // Reset the view model so that new receipts can be processed
        viewModel.reset();

        // Navigate back to options view
        if (getView() != null) {
            Navigation.findNavController(getView()).navigate(R.id.action_payment_to_options );
        }
    }
}