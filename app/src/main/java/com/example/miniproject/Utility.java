package com.example.miniproject;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class Utility {

    static void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    static CollectionReference getCollectionReferenceForNotes(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("notes")
                .document(currentUser.getUid()).collection("my_notes");
    }

//    static CollectionReference getCollectionReferenceForAppointement(){
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        return FirebaseFirestore.getInstance().collection("appointements")
//                .document(currentUser.getUid()).collection("my_appointements");
//    }

    static CollectionReference getCollectionReferenceForAppointement(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("appointements")
                .document(currentUser.getUid()).collection("my_appointements");
    }

    static CollectionReference getCollectionReferenceForAllAppointement(){
//        return FirebaseFirestore.getInstance().collection("appointements").document().collection("my_appointments");
        return FirebaseFirestore.getInstance().collection("appointments").document().collection("my_appointements");

    }
    static String timestampToString(Timestamp timestamp){
        return new SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate());
    }

}
