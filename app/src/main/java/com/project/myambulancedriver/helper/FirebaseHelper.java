package com.project.myambulancedriver.helper;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.myambulancedriver.model.LokasiUser;

public class FirebaseHelper {
    private static final String ONLINE_DRIVER = "pesanan yang masuk";

    private DatabaseReference onlineUserDatabaseReference;

    public FirebaseHelper(String driver_id) {
        onlineUserDatabaseReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(ONLINE_DRIVER)
                .child(driver_id);
        onlineUserDatabaseReference
                .onDisconnect()
                .removeValue();
    }


    public void updateUser(LokasiUser lokasi) {
        onlineUserDatabaseReference
                .setValue(lokasi)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Berhasil", "updateUser:Success ");
                }).addOnFailureListener(Throwable::printStackTrace);
    }


    public void deleteUser() {
        onlineUserDatabaseReference
                .removeValue();
    }
}
