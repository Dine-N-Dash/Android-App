package com.dine.dinendash.dinendash.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.FragmentPaymentBinding;
import com.dine.dinendash.dinendash.fragments.adapters.TransactionItemsAdapter;
import com.dine.dinendash.dinendash.models.Transaction;
import com.dine.dinendash.dinendash.viewModels.NewReceiptViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

public class Payment extends Fragment {
    private NewReceiptViewModel viewModel;

    public Payment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity()!=null) {
            viewModel = ViewModelProviders.of(getActivity()).get(NewReceiptViewModel.class);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentPaymentBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false);
        binding.setViewModel(viewModel);
        binding.setFragment(this);
        binding.setLifecycleOwner(this);

        Log.d("viewmodel",viewModel.toString());

        binding.transactionItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.transactionItemsRecyclerView.setAdapter(new TransactionItemsAdapter(viewModel, this, this));


        return binding.getRoot();
    }

    public void sendMessageTo(Transaction transaction) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        String username = sharedPreferences.getString("username", "");

        Uri uri = Uri.parse("smsto:"+transaction.getPhoneNumber().getValue() );
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", "Hi "+transaction.getName().getValue()+"! You owe me $" + transaction.getTotal().getValue() +". You can pay me using this link: https://www.paypal.me/"+username+"/"+transaction.getTotal().getValue());
        startActivity(intent);

        transaction.setCompleted(true);
        viewModel.getTransactions().setValue(viewModel.getTransactions().getValue());
    }

    public void finishTransactions() {
        // Save in database here

        viewModel.reset();

        if (getView() != null) {
            Navigation.findNavController(getView()).navigate(R.id.action_payment_to_options );
        }
    }
}