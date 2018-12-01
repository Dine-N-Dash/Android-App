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

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewReceiptViewModel extends ViewModel {
    private MutableLiveData<Receipt> receipt;
    private MutableLiveData<Transaction> currentTransaction;
    private MutableLiveData<Boolean> processed;
    private MutableLiveData<Integer> tipPercent;
    private MutableLiveData<Integer> tipPercentDecimal;
    private MutableLiveData<String> receiptName;

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
            currentTransaction.setValue(new Transaction());
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
            processed.setValue(false);
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

    public MutableLiveData<Integer> getTipPercent() {
        if (tipPercent == null) {
            tipPercent = new MutableLiveData<>();
            tipPercent.setValue(0);
        }

        return tipPercent;
    }

    public void setTipPercent(Integer percent) {
        getTipPercent().postValue(percent);
    }

    public MutableLiveData<Integer> getTipPercentDecimal() {
        if (tipPercentDecimal == null) {
            tipPercentDecimal = new MutableLiveData<>();
            tipPercentDecimal.setValue(0);
        }

        return tipPercentDecimal;
    }

    public void setTipPercentDecimal(Integer percent) {
        getTipPercentDecimal().postValue(percent);
    }

    public void addTip() {
        if (getTipPercent().getValue() != null && getTipPercentDecimal().getValue() != null) {
            double percent = getTipPercent().getValue().doubleValue();
            percent += (getTipPercentDecimal().getValue().doubleValue() / 10.0);
            percent /= 100.0;

            if (getReceipt().getValue() != null && getReceipt().getValue().getTransactions().getValue() != null) {
                for (Transaction transaction : getReceipt().getValue().getTransactions().getValue()) {
                    transaction.applyTip(percent);
                }
            }

            getReceipt().getValue().setTransactions(getReceipt().getValue().getTransactions().getValue());
        }
    }

    public MutableLiveData<String> getReceiptName() {
        if (receiptName == null) {
            receiptName = new MutableLiveData<>();
            receiptName.setValue("");
        }

        return receiptName;
    }

    public void setReceiptName(String name) {
        getReceiptName().postValue(name);
    }

    public void applyReceiptName() {
        if (getReceipt().getValue() != null) {
            getReceipt().getValue().setName(getReceiptName().getValue());
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

    public void analyzeImage(String path, ContentResolver resolver, int rotate) {
        new AnalyzePhotoTask(path,this, resolver, rotate).execute();
    }

    public void reset() {
        receipt = null;
        currentTransaction = null;
        processed = null;
    }

    private static class AnalyzePhotoTask extends AsyncTask<Void, Void, Void> {
        private final String path;
        private final NewReceiptViewModel viewModel;
        private final ContentResolver resolver;
        private final int rotate;

        protected AnalyzePhotoTask(String path, NewReceiptViewModel viewModel, ContentResolver resolver, int rotate) {
            this.path = path;
            this.viewModel = viewModel;
            this.resolver = resolver;
            this.rotate = rotate;
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

            PhotoAnalyzer.analyze(bitmap, viewModel, rotate);

            return null;
        }
    }
}
