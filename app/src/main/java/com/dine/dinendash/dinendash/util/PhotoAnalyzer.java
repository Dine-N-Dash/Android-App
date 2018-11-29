package com.dine.dinendash.dinendash.util;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;

import com.dine.dinendash.dinendash.models.ReceiptItem;
import com.dine.dinendash.dinendash.viewModels.NewReceiptViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;

public class PhotoAnalyzer {

    private static class lineObj implements Comparable{
        String line;
        int midpoint;
        lineObj itemName;

        lineObj(String l, int c){
            this.line = l;
            this.midpoint = c;
            this.itemName = null;
        }

        void addConnection(lineObj c){
            this.itemName = c;
        }

        @Override
        public int compareTo(Object o){ return this.midpoint - ((lineObj) o).midpoint; }

        @Override
        @NonNull
        public String toString(){ return this.line; }
    }


    public static void analyze(final Bitmap bitmap, final NewReceiptViewModel viewModel) {
        //Firebase API initializers
        FirebaseVisionImage img = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

        textRecognizer.processImage(img)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText result) {
                        int count = 0, averageHeight = 0;

                        //Pattern to look for prices
                        //Looks for any amount of numbers follow by a dot and then one/two numbers
                        Pattern p = Pattern.compile("\\d*\\.\\d{1,2}(?=\\D|\\b)");

                        //List to hold all of the found text blocks.
                        //Prices hold anything matched by the regex
                        //notPrices holds everything else
                        ArrayList<lineObj> prices = new ArrayList<>();
                        ArrayList<lineObj> notPrices = new ArrayList<>();

                        //Loop through all found text
                        for (FirebaseVisionText.TextBlock block: result.getTextBlocks()) {
                            for (FirebaseVisionText.Line line: block.getLines()) {
                                String lineText = line.getText();
                                Point[] lineCornerPoints = line.getCornerPoints();

                                if (lineCornerPoints != null) {
                                    //To match the item text to a given price we need to match their midponts
                                    //We can do this using the corner points that form the bounding box of the text
                                    int midpoint = lineCornerPoints[2].y - ((lineCornerPoints[2].y - lineCornerPoints[0].y)/2);

                                    Matcher m = p.matcher(lineText);
                                    if (m.find()) {
                                        lineText = m.group(0);
                                        prices.add(new lineObj(lineText, midpoint));
                                    }
                                    else {
                                        notPrices.add(new lineObj(lineText, midpoint));
                                    }

                                    count++;
                                    averageHeight += lineCornerPoints[2].y - lineCornerPoints[0].y;
                                }
                            }
                        }

                        if (count > 0) {
                            //To match the midpoints we need a degree of error
                            //Pictures will not always be taken 100% horizontally so the midpoints could be skewed
                            //To determine how many pixels of error to account for we need to know
                            //  the average height of a line of text.
                            //To do this we added up the total height of each bounding box
                            //Then we can divide by the count of boxes to get the average height
                            averageHeight /= count*2;
                        }

                        //Next we need to match the prices to their corresponding item names
                        Iterator<lineObj> itemIter = notPrices.iterator();
                        Iterator<lineObj> priceIter;
                        boolean found;

                        //Loop through all non price items
                        while (itemIter.hasNext()) {
                            lineObj item = itemIter.next();
                            found = false;

                            //Then loop through all price items
                            priceIter = prices.iterator();
                            while (priceIter.hasNext() && !found) {
                                lineObj price = priceIter.next();

                                //Check to see if the midpoints match up.
                                // If they do it can be assumed that they are on the same line
                                //Since they are on the same line it can also be assumed the price is for that item
                                //The level of error is currently fairly large
                                //Check to see if the midpoints are within an average height up or an average height down
                                //Could be halfed if deemed necessary in the future
                                if (item.midpoint <= price.midpoint + averageHeight && item.midpoint >= price.midpoint - averageHeight) {
                                    price.addConnection(item);
                                    found = true;
                                }
                            }
                            if (!found) {
                                itemIter.remove();
                            }
                        }

                        for(lineObj q: prices) {
                            if(q.itemName != null && viewModel.getReceipt().getValue() != null) {
                                String test = q.itemName.line.toLowerCase();
                                if(!(test.contains("tax") || test.contains("total") || test.contains("subtotal") || test.contains("sub total") || Double.parseDouble(q.line) == 0))
                                    viewModel.getReceipt().getValue().addItem(new ReceiptItem(q.itemName.line, Double.parseDouble(q.line)));
                            }
                        }

                        viewModel.setProcessed(true);

                        bitmap.recycle();
                    }
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("PHOTOANALYZER", e.toString());
                                bitmap.recycle();
                            }
                        });

    }
}
