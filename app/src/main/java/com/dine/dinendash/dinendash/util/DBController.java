package com.dine.dinendash.dinendash.util;

import android.util.Log;

import com.dine.dinendash.dinendash.models.Receipt;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
        final ArrayList receiptList = new ArrayList();

        databaseReference = FirebaseDatabase.getInstance().getReference().child(id);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                GenericTypeIndicator<Map<String, Receipt>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Receipt>>() {};

                Map<String, Receipt> map = dataSnapshot.getValue(genericTypeIndicator);

                if(map !=null) {
                    String [] names = new String[]{};
                    map.keySet().toArray(names);

                    Log.d("PIZZA", map.values().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return receiptList;
    }
}
