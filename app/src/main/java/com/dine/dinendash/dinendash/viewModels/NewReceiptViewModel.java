package com.dine.dinendash.dinendash.viewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.dine.dinendash.dinendash.models.Transaction;
import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.util.PhotoAnalyzer;

import java.util.List;

public class NewReceiptViewModel extends ViewModel {
    private MutableLiveData<Bitmap> receiptBitmap;
    private MutableLiveData<Receipt> receipt;
    private MutableLiveData<List<Transaction>> transactions;
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

    public MutableLiveData<List<Transaction>> getTransactions() {
        if(transactions == null) {
            transactions = new MutableLiveData<>();
        }

        return transactions;
    }

    public void setReceipt(Receipt receipt) {
        getReceipt().setValue(receipt);
    }

    public MutableLiveData<Bitmap> getReceiptBitmap(){
        if(receiptBitmap == null){
            receiptBitmap = new MutableLiveData<>();
        }

        return receiptBitmap;
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

    public void setReceiptBitmap(Bitmap bitmap){
        getReceiptBitmap().setValue(bitmap);
        PhotoAnalyzer analyzer = new PhotoAnalyzer(getReceiptBitmap().getValue());

        new AnalyzePhotoTask(analyzer, this).execute();
    }

    private static class AnalyzePhotoTask extends AsyncTask<Void, Void, Void> {
        private PhotoAnalyzer analyzer;
        private NewReceiptViewModel viewModel;

        public AnalyzePhotoTask(PhotoAnalyzer analyzer, NewReceiptViewModel viewModel) {
            this.analyzer = analyzer;
            this.viewModel = viewModel;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Receipt receipt = analyzer.analyze();

            viewModel.getReceipt().postValue(receipt);

            viewModel.setProcessed(true);

            return null;
        }
    }
}
