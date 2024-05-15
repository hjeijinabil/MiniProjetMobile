package com.example.miniproject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Utility {

    static void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
    private static final String TAG = "FirestoreData";

    public static CollectionReference getAllCollectionReferenceForAppointement(String uuid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        return FirebaseFirestore.getInstance().collection("appointements")
                .document(uuid).collection("my_appointements");
    }
    public static CollectionReference getCollectionReferenceForAppointement(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("appointements")
                .document(currentUser.getUid()).collection("my_appointements");
    }

    public static CollectionReference getCollectionReferenceForUser(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("users");
    }


    static String timestampToString(Timestamp timestamp){
        return new SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate());
    }

}
