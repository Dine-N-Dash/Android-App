package com.dine.dinendash.dinendash.util;

import android.graphics.Bitmap;

public class PhotoAnalyzer {
    private Bitmap bitmap;

    public PhotoAnalyzer(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public String analyze(){
        return "wow";
    }
}
