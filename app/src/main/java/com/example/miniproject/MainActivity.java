package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject.adapter.AppointementAdapter;
import com.example.miniproject.auth.LoginActivity;
import com.example.miniproject.model.Appointement;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addNoteBtn;
    RecyclerView recyclerView;
    ImageButton menuBtn;
    AppointementAdapter appointementAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNoteBtn = findViewById(R.id.add_note_btn);
        recyclerView = findViewById(R.id.recyler_view);
        menuBtn = findViewById(R.id.menu_btn);

        addNoteBtn.setOnClickListener((v)-> startActivity(new Intent(MainActivity.this, AppointementDetailsActivity.class)) );
        menuBtn.setOnClickListener((v)->showMenu() );
        setupRecyclerView();
    }

    void showMenu(){
        PopupMenu popupMenu  = new PopupMenu(MainActivity.this,menuBtn);
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle()=="Logout"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                    return true;
                }
                return false;
            }
        });

    }

    void setupRecyclerView(){
        Query query  = Utility.getCollectionReferenceForAppointement().orderBy("timestamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Appointement> options = new FirestoreRecyclerOptions.Builder<Appointement>()
                .setQuery(query,Appointement.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        appointementAdapter = new AppointementAdapter(options,this);
        recyclerView.setAdapter(appointementAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        appointementAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        appointementAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        appointementAdapter.notifyDataSetChanged();
    }
}