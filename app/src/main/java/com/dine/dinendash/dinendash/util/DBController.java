package com.dine.dinendash.dinendash.util;

import android.util.Log;

import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.models.ReceiptItem;
import com.dine.dinendash.dinendash.models.Transaction;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

public class DBController {

    private static DatabaseReference databaseReference;

    public static void addReceipt(Receipt receipt, String id) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(id).child(receipt.getName().getValue()).setValue(receipt);
    }

    public static List<Receipt> getReceipts(String id) {
        ArrayList receiptList = new ArrayList();


        return receiptList;
    }
}
