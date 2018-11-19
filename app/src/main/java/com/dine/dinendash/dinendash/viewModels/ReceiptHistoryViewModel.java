package com.dine.dinendash.dinendash.viewModels;

import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.models.ReceiptItem;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReceiptHistoryViewModel extends ViewModel {
    private MutableLiveData<List<Receipt>> receipts;

    public ReceiptHistoryViewModel() {
        Receipt receipt = new Receipt();
        receipt.addItem(new ReceiptItem("Pizza", 12.00));

        Receipt receipt2 = new Receipt();
        receipt2.addItem(new ReceiptItem("Pie", 100.00));

        addReceipt(receipt);
        addReceipt(receipt2);
    }

    public void addReceipt(Receipt receipt) {
        if (getReceipts().getValue() != null) {
            getReceipts().getValue().add(receipt);
        }

        setReceipts(getReceipts().getValue());
    }

    public MutableLiveData<List<Receipt>> getReceipts() {
        if (receipts == null) {
            receipts = new MutableLiveData<List<Receipt>>();
            receipts.setValue(new ArrayList<Receipt>());
        }

        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.getReceipts().postValue(receipts);
    }

    public void itemSelected(Receipt receipt) {

    }

}
