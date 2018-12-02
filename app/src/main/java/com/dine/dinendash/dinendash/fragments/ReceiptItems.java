package com.dine.dinendash.dinendash.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dine.dinendash.dinendash.R;
import com.dine.dinendash.dinendash.databinding.FragmentReceiptItemsBinding;
import com.dine.dinendash.dinendash.fragments.adapters.ReceiptItemsAdapter;
import com.dine.dinendash.dinendash.fragments.adapters.TransactionSpinnerAdapter;
import com.dine.dinendash.dinendash.models.Transaction;
import com.dine.dinendash.dinendash.util.Statics;
import com.dine.dinendash.dinendash.viewModels.NewReceiptViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

public class ReceiptItems extends Fragment {
    private NewReceiptViewModel viewModel;

    public ReceiptItems() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the correct view model
        if (getActivity() != null) {
            viewModel = ViewModelProviders.of(getActivity()).get(NewReceiptViewModel.class);
        }

        // If a photo path was sent as an argument, have the view model analyze it
        if (getArguments() != null) {
            String path = getArguments().getString("photoPath");
            int rotate = getArguments().getInt("rotate", 0);

            if (path != null && viewModel.getProcessed().getValue() != null && !viewModel.getProcessed().getValue()) {
                viewModel.analyzeImage(path, getActivity().getContentResolver(), rotate);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FragmentReceiptItemsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_receipt_items, container, false);

        // Bind the fragment and view model to the View
        binding.setViewModel(viewModel);
        binding.setFragment(this);
        binding.setLifecycleOwner(this);

        // Set up the RecyclerView
        binding.receiptItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.receiptItemsRecyclerView.setAdapter(new ReceiptItemsAdapter(viewModel, this));

        // When a new Transaction is added, set it to be the current transaction
        if (viewModel.getReceipt().getValue() != null) {
            viewModel.getReceipt().getValue().getTransactions().observe(this, new Observer<List<Transaction>>() {
                @Override
                public void onChanged(List<Transaction> transactions) {
                    if (viewModel.getReceipt().getValue().getTransactions().getValue() != null) {
                        binding.contactSpinner.setSelection(viewModel.getReceipt().getValue().getTransactions().getValue().size());
                    }
                }
            });
        }

        // Set up the Spinner
        if (getContext() != null) {
            binding.contactSpinner.setAdapter(new TransactionSpinnerAdapter(getContext(), R.layout.transaction_spinner_text_view, this, viewModel));
        }

        showBackButton();

        return binding.getRoot();
    }

    public void donePressed() {
        // Navigate to the payment view
        if (getView() != null) {
            Navigation.findNavController(getView()).navigate(R.id.action_receiptItems_to_payment);
        }
    }

    public void newTransaction() {
        chooseContact();
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri contactData = data.getData();
            Cursor cursor;  // Cursor object
            String mime;    // MIME type
            int dataIdx;    // Index of DATA1 column
            int mimeIdx;    // Index of MIMETYPE column
            int nameIdx;    // Index of DISPLAY_NAME column
            String name = "";
            String phone = "";

            if (getActivity() != null && contactData != null) {
                // Get the name
                cursor = getActivity().getContentResolver().query(contactData,
                        new String[]{ContactsContract.Contacts.DISPLAY_NAME},
                        null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    nameIdx = cursor.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME);
                    name = cursor.getString(nameIdx);
                    // Set up the projection
                    String[] projection = {
                            ContactsContract.Data.DISPLAY_NAME,
                            ContactsContract.Contacts.Data.DATA1,
                            ContactsContract.Contacts.Data.MIMETYPE};
                    // Query ContactsContract.Data
                    cursor = getActivity().getContentResolver().query(
                            ContactsContract.Data.CONTENT_URI, projection,
                            ContactsContract.Data.DISPLAY_NAME + " = ?",
                            new String[]{name},
                            null);
                    if (cursor != null && cursor.moveToFirst()) {
                        // Get the indexes of the MIME type and data
                        mimeIdx = cursor.getColumnIndex(
                                ContactsContract.Contacts.Data.MIMETYPE);
                        dataIdx = cursor.getColumnIndex(
                                ContactsContract.Contacts.Data.DATA1);
                        // Match the data to the MIME type, store in variables
                        do {
                            mime = cursor.getString(mimeIdx);
                            if (ContactsContract.CommonDataKinds.Phone
                                    .CONTENT_ITEM_TYPE.equalsIgnoreCase(mime)) {
                                phone = cursor.getString(dataIdx);
                            }
                        } while (cursor.moveToNext());
                    }
                }

                if (cursor != null) {
                    cursor.close();
                }

                if (viewModel.getReceipt().getValue() != null && viewModel.getReceipt().getValue().getTransactions().getValue() != null) {
                    for (Transaction t: viewModel.getReceipt().getValue().getTransactions().getValue()) {
                        if (t.getName().getValue() != null && t.getPhoneNumber().getValue() != null) {
                            if (t.getName().getValue().equals(name) && t.getPhoneNumber().getValue().equals(phone)) {
                                Toast.makeText(getActivity(), t.getName().getValue() + " has already been added!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                }

                viewModel.addTransaction(name, phone);
            }

        }
    }

    private void chooseContact() {
        // Create and launch an Intent to pick a contact
        Intent intent= new Intent(Intent.ACTION_PICK,  ContactsContract.Contacts.CONTENT_URI);

        if (getActivity() != null) {
            if (checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                        Statics.REQUEST_CODE_ASK_PERMISSIONS);
            }
            else {
                startActivityForResult(intent, Statics.PICK_CONTACT);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            chooseContact();
        }
        else {
            Toast.makeText(getActivity(),"This application requires the use of contacts", Toast.LENGTH_SHORT).show();
        }
    }

    public void showBackButton() {
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();

            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }
}
