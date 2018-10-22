package com.dine.dinendash.dinendash.viewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.models.ReceiptItem;
import com.dine.dinendash.dinendash.models.Transaction;
import com.dine.dinendash.dinendash.util.PhotoAnalyzer;

import java.util.List;

public class NewReceiptViewModel extends ViewModel {
    private MutableLiveData<Receipt> receipt;
    private MutableLiveData<List<Transaction>> transactions;
    private MutableLiveData<Transaction> currentTransaction;
    private MutableLiveData<Boolean> processed;

    public NewReceiptViewModel() {
        getProcessed().setValue(false);
    }

    public MutableLiveData<Receipt> getReceipt() {
        if(receipt == null) {
            receipt = new MutableLiveData<>();
        }

        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        getReceipt().setValue(receipt);
    }

    public MutableLiveData<List<Transaction>> getTransactions() {
        if (transactions == null) {
            transactions = new MutableLiveData<>();
        }

        return transactions;
    }

    public void addTransaction(String name, String phoneNumber) {
        if (getTransactions().getValue() != null) {
            Transaction transaction = new Transaction(name, phoneNumber);
            getTransactions().getValue().add(transaction);
            currentTransaction.postValue(transaction);
        }
    }

    public void itemSelected(ReceiptItem item, boolean add) {
        if (add) {
            if (getCurrentTransaction().getValue() != null) {
                getCurrentTransaction().getValue().addItem(item);
            }
        } else {
            if (getCurrentTransaction().getValue() != null) {
                getCurrentTransaction().getValue().removeItem(item);
            }
        }
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

    public MutableLiveData<Boolean> getProcessed() {
        if (processed == null) {
            processed = new MutableLiveData<>();
        }

        return processed;
    }

    public void setProcessed(Boolean processed) {
        getProcessed().postValue(processed);
    }

    public void analyzeImage(String path, ContentResolver resolver) {
        new AnalyzePhotoTask(path,this, resolver).execute();
    }

    private static class AnalyzePhotoTask extends AsyncTask<Void, Void, Void> {
        private String path;
        private NewReceiptViewModel viewModel;
        private ContentResolver resolver;

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
                Log.e("PHOTOERROR", e.toString());
            }

            Receipt receipt = PhotoAnalyzer.analyze(bitmap);

            viewModel.getReceipt().postValue(receipt);

            viewModel.setProcessed(true);

            if (bitmap != null) {
                bitmap.recycle();
                bitmap = null;
            }

            return null;
        }
    }
}
