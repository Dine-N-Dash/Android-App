package com.dine.dinendash.dinendash.viewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.dine.dinendash.dinendash.models.Contact;
import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.util.PhotoAnalyzer;

import java.util.List;

public class NewReceiptViewModel extends ViewModel {
    private MutableLiveData<Bitmap> receiptBitmap;
    private MutableLiveData<Receipt> receipt;
    private MutableLiveData<List<Contact>> contacts;

    public NewReceiptViewModel(){

    }

    public MutableLiveData<Receipt> getReceipt() {
        if(receipt == null) {
            receipt = new MutableLiveData<>();
        }

        return receipt;
    }

    public MutableLiveData<List<Contact>> getContacts() {
        if(contacts == null) {
            contacts = new MutableLiveData<>();
        }

        return contacts;
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

    public void setReceiptBitmap(Bitmap bitmap){
        getReceiptBitmap().setValue(bitmap);
        PhotoAnalyzer analyzer = new PhotoAnalyzer(getReceiptBitmap().getValue());
        setReceipt(analyzer.analyze());
    }
}
