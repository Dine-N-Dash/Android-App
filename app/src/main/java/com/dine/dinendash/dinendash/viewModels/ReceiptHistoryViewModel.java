package com.dine.dinendash.dinendash.viewModels;

import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.models.ReceiptItem;
import com.dine.dinendash.dinendash.models.Transaction;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReceiptHistoryViewModel extends ViewModel {
    private MutableLiveData<List<Receipt>> receipts;

    public ReceiptHistoryViewModel() {

        //dummy data
        Receipt receipt = new Receipt();

        ReceiptItem pizza = new ReceiptItem("Pizza", 12.00);
        receipt.addItem(pizza);
        ReceiptItem pie = new ReceiptItem("Pie", 4.00);
        receipt.addItem(pie);

        Transaction t1 = new Transaction("Shelby", "13023");
        receipt.addTransaction(t1);
        t1.addItem(pizza);

        Transaction t2 = new Transaction("Shelby123", "13023123123");
        receipt.addTransaction(t2);
        t2.addItem(pie);

        receipt.setName("Friday Food");

        Receipt receipt2 = new Receipt();
        receipt2.addItem(pizza);
        receipt2.addItem(pie);

        receipt2.addTransaction(t1);
        t1.addItem(pizza);

        receipt2.addTransaction(t2);
        t2.addItem(pie);

        receipt2.setName("Another Food Outing");

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
            receipts = new MutableLiveData<>();
            receipts.setValue(new ArrayList<Receipt>());
        }

        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.getReceipts().postValue(receipts);
    }

}
