// Written by: Shelby Heffron
// Tested by: Shelby Heffron

package com.dine.dinendash.dinendash.viewModels;

import com.dine.dinendash.dinendash.models.Receipt;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReceiptHistoryViewModel extends ViewModel {
    private MutableLiveData<List<Receipt>> receipts;

    public ReceiptHistoryViewModel() {
    }

    public void addReceipt(Receipt receipt) {
        if (getReceipts().getValue() != null) {
            getReceipts().getValue().add(receipt);
        }

        setReceipts(getReceipts().getValue());
    }

    public MutableLiveData<List<Receipt>> getReceipts() {
        if (receipts == null) {
            receipts = new MutableLiveData<>();
            receipts.setValue(new ArrayList<Receipt>());
        }

        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.getReceipts().postValue(receipts);
    }

}
