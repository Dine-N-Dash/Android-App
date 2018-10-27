package com.dine.dinendash.dinendash.util;

import android.graphics.Bitmap;

import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.models.ReceiptItem;
import com.dine.dinendash.dinendash.models.Transaction;

import java.util.ArrayList;

public class PhotoAnalyzer {

    public static Receipt analyze(Bitmap bitmap) {
        Receipt receipt = new Receipt();
        ArrayList<ReceiptItem> items = new ArrayList<>();

        items.add(new ReceiptItem("Big Italian", 7.56));
        items.add(new ReceiptItem("25\" Meat Lovers Pizza", 23.50));
        items.add(new ReceiptItem("Captain and Coke", 2.30));
        items.add(new ReceiptItem("Veggies", 5.50));
        items.add(new ReceiptItem("Wings", 4.45));
        items.add(new ReceiptItem("Big Italian", 7.56));
        items.add(new ReceiptItem("25\" Meat Lovers Pizza", 23.50));
        items.add(new ReceiptItem("Captain and Coke", 2.30));
        items.add(new ReceiptItem("Veggies", 5.50));
        items.add(new ReceiptItem("Wings", 4.45));

        receipt.setItems(items);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return receipt;
    }
}
