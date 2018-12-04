// Written by: Brian Lasker

package com.dine.dinendash.dinendash.util;

import android.util.Log;

import com.dine.dinendash.dinendash.models.Receipt;
import com.dine.dinendash.dinendash.viewModels.ReceiptHistoryViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.NonNull;

public class DBController {

    private static DatabaseReference databaseReference;

    // NOTE: The signature of this method is different than the one in Report 3 as it was changed after Report 3 was submitted
    // This was necessary because we need a user's ID in order to save the receipt to their account
    public static void addReceipt(Receipt receipt, String id) {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        if (receipt.getName().getValue() != null) {
            databaseReference.child(id).child(receipt.getName().getValue()).setValue(receipt);
        }
    }

    // NOTE: The signature of this method is different than the one in Report 3 as it was changed after Report 3 was submitted
    // This was necessary because we need a user's ID in order to save the receipt to their account
    // Additionally, this method needs a reference to the view model that is calling it because the results of the database query are returned
    // in a callback. This means that they cannot be returned from the method itself
    public static void getReceipts(String id, final ReceiptHistoryViewModel vm) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(id);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                GenericTypeIndicator<Map<String, Receipt>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Receipt>>() {};

                Map<String, Receipt> map = dataSnapshot.getValue(genericTypeIndicator);

                if(map !=null) {
                    vm.setReceipts(new ArrayList<>(map.values()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DB-ERROR", databaseError.toString());
            }
        });
    }

    //NOTE: Update and delete methods for our database were determined to not be necessary for our use cases and were therefore eliminated.
}
