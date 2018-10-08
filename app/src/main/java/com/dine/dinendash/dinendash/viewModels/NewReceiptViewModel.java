package com.dine.dinendash.dinendash.viewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.dine.dinendash.dinendash.util.PhotoAnalyzer;

public class NewReceiptViewModel extends ViewModel {
    private MutableLiveData<Bitmap> receiptBitmap;

    public NewReceiptViewModel(){

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
        analyzer.analyze();
    }
}
