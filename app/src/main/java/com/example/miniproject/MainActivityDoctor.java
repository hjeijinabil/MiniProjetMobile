package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject.adapter.DoctorAppointementAdaper;
import com.example.miniproject.auth.LoginActivity;
import com.example.miniproject.model.Appointement;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

public class MainActivityDoctor extends AppCompatActivity {
    private static final String TAG = "MainActivityDoctor";
//    FloatingActionButton addAppoitementBtn;
    RecyclerView recyclerView;
    ImageButton menuBtn;
    DoctorAppointementAdaper doctorAppointementAdaper;

    String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doctor);

//        addAppoitementBtn = findViewById(R.id.add_note_btn);
        recyclerView = findViewById(R.id.recyler_view);
        menuBtn = findViewById(R.id.menu_btn);



//        addAppoitementBtn.setOnClickListener((v)-> startActivity(new Intent(MainActivityDoctor.this, AppointementDetailsActivity.class)) );
        menuBtn.setOnClickListener((v)->showMenu() );
        setupRecyclerView();
    }

    void showMenu(){
        PopupMenu popupMenu  = new PopupMenu(MainActivityDoctor.this,menuBtn);
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle()=="Logout"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivityDoctor.this, LoginActivity.class));
                    finish();
                    return true;
                }
                return false;
            }
        });

    }

    void setupRecyclerView(){

        uuid = getIntent().getStringExtra("uuid");
        Query query = Utility.getAllCollectionReferenceForAppointement(uuid).orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Appointement> options = new FirestoreRecyclerOptions.Builder<Appointement>()
                .setQuery(query, Appointement.class)
                .build();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctorAppointementAdaper = new DoctorAppointementAdaper(options,this);
        recyclerView.setAdapter(doctorAppointementAdaper);
    }

    @Override
    protected void onStart() {
        super.onStart();
        doctorAppointementAdaper.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        doctorAppointementAdaper.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        doctorAppointementAdaper.notifyDataSetChanged();
    }
}