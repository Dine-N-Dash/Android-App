package com.dine.dinendash.dinendash.util;

import android.graphics.Bitmap;

import com.dine.dinendash.dinendash.models.Receipt;

public class PhotoAnalyzer {
    private Bitmap bitmap;

    public PhotoAnalyzer(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Receipt analyze() {
        // do analysis here
        return new Receipt();
    }
}
