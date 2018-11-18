package com.dine.dinendash.dinendash.viewModels;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.models.ReceiptItem;
import com.dine.dinendash.dinendash.models.Transaction;
import com.dine.dinendash.dinendash.util.PhotoAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewReceiptViewModel extends ViewModel {
    private MutableLiveData<Receipt> receipt;
    private MutableLiveData<Transaction> currentTransaction;
    private MutableLiveData<Boolean> processed;

    public NewReceiptViewModel() {
        getProcessed().setValue(false);
    }

    public MutableLiveData<Receipt> getReceipt() {
        if(receipt == null) {
            receipt = new MutableLiveData<>();
            receipt.setValue(new Receipt());
        }

        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        getReceipt().postValue(receipt);
    }

    public MutableLiveData<Transaction> getCurrentTransaction() {
        if (currentTransaction == null) {
            currentTransaction = new MutableLiveData<>();
        }

        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction transaction) {
        getCurrentTransaction().postValue(transaction);
    }

    public void setCurrentTransaction(int index) {
        if (getReceipt().getValue() != null && getReceipt().getValue().getTransactions().getValue() != null) {
            getCurrentTransaction().postValue(getReceipt().getValue().getTransactions().getValue().get(index));
        }
    }

    public MutableLiveData<Boolean> getProcessed() {
        if (processed == null) {
            processed = new MutableLiveData<>();
        }

        return processed;
    }

    public void setProcessed(Boolean processed) {
        getProcessed().postValue(processed);
    }

    public void addTransaction(String name, String phoneNumber) {
        // Add new Transaction with given name and number to transaction list
        if (getReceipt().getValue() != null) {
            Transaction transaction = new Transaction(name, phoneNumber);
            getReceipt().getValue().addTransaction(transaction);
        }
    }

    public void itemSelected(ReceiptItem item) {
        if (item.getOwner() == null) {
            // If the item has no owner, add it to the current transaction and update binding
            if (getCurrentTransaction().getValue() != null) {
                getCurrentTransaction().getValue().addItem(item);
                getCurrentTransaction().setValue(getCurrentTransaction().getValue());
            }
        } else if (item.getOwner() == getCurrentTransaction().getValue()) {
            // Otherwise, if the item's owner is the current transaction, remove it from the current transaction
            if (getCurrentTransaction().getValue() != null) {
                getCurrentTransaction().getValue().removeItem(item);
                getCurrentTransaction().setValue(getCurrentTransaction().getValue());
            }
        }
    }

    public void analyzeImage(String path, ContentResolver resolver) {
        new AnalyzePhotoTask(path,this, resolver).execute();
    }

    public void reset() {
        receipt = null;
        currentTransaction = null;
        processed.postValue(false);
    }

    private static class AnalyzePhotoTask extends AsyncTask<Void, Void, Void> {
        private final String path;
        private final NewReceiptViewModel viewModel;
        private final ContentResolver resolver;

        public AnalyzePhotoTask(String path, NewReceiptViewModel viewModel, ContentResolver resolver) {
            this.path = path;
            this.viewModel = viewModel;
            this.resolver = resolver;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(path));
            } catch (Exception e){
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(resolver,Uri.fromFile(new File(path)));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            PhotoAnalyzer.analyze(bitmap, viewModel);

            return null;
        }
    }
}
