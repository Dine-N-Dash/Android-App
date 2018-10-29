package com.dine.dinendash.dinendash.util;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.models.ReceiptItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;

public class PhotoAnalyzer {
    private Bitmap bitmap;

    private class lineObj implements Comparable{
        String line;
        int midpoint;
        boolean isPrice;
        lineObj connect4;

        lineObj(String l, int c, boolean p){
            this.line = l;
            this.midpoint = c;
            this.isPrice = p;
            this.connect4 = null;
        }

        void addConnection(lineObj c){
            this.connect4 = c;
        }

        @Override
        public int compareTo(Object o){ return this.midpoint - ((lineObj) o).midpoint; }

        @Override
        @NonNull
        public String toString(){ return this.line; }

    }

    public PhotoAnalyzer(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public Receipt analyze() {
        FirebaseVisionImage img = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

        textRecognizer.processImage(img)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText result) {
                        String resultText = result.getText();
                        int count = 0, avH = 0, midLow = Integer.MAX_VALUE, midHigh = 0;
                        Pattern p = Pattern.compile("\\d*\\.\\d{1,2}(?=\\D|\\b)");
                        ArrayList<lineObj> prices = new ArrayList<>();
                        ArrayList<lineObj> notPrices = new ArrayList<>();
                        for (FirebaseVisionText.TextBlock block: result.getTextBlocks()) {
                            for (FirebaseVisionText.Line line: block.getLines()) {
                                String lineText = line.getText();
                                Point[] lineCornerPoints = line.getCornerPoints();
                                int midpoint = lineCornerPoints[2].y - ((lineCornerPoints[2].y - lineCornerPoints[0].y)/2);
                                Matcher m = p.matcher(lineText);
                                if(m.find()) {
                                    lineText = m.group(0);
                                    if(midpoint < midLow) midLow = midpoint;
                                    if(midpoint > midHigh) midHigh = midpoint;
                                    prices.add(new lineObj(lineText, midpoint, true));
                                }
                                else{
                                    notPrices.add(new lineObj(lineText, midpoint, false));
                                }
                                count++;
                                avH += lineCornerPoints[2].y - lineCornerPoints[0].y;
                            }
                        }
                        avH /= count*2;
                        Iterator<lineObj> i = notPrices.iterator();
                        Iterator<lineObj> j;
                        boolean found;
                        while (i.hasNext()){
                            lineObj o = i.next();
                            if (o.midpoint > midHigh + avH || o.midpoint < midLow - avH){
                                i.remove();
                            }
                            else{
                                found = false;
                                j = prices.iterator();
                                while (j.hasNext() && !found){
                                    lineObj oP = j.next();
                                    if (o.midpoint <= oP.midpoint + avH && o.midpoint >= oP.midpoint - avH){
                                        oP.addConnection(o);
                                        found = true;
                                    }
                                }
                                if (!found)
                                    i.remove();
                            }
                        }
                        Receipt r = new Receipt();
                        for(lineObj q: prices)
                            if(q.connect4 != null)
                                r.AddItem(new ReceiptItem(q.connect4.line, Double.parseDouble(q.line)));
                        //return r; fuck
                    }
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("yikes");
                            }
                        });


        return new Receipt();
    }
}
