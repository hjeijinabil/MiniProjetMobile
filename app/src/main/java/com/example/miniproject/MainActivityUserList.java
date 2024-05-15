package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivityUserList extends AppCompatActivity {
    private static final String TAG = "MainActivityUserList";
//    FloatingActionButton addAppoitementBtn;
    RecyclerView recyclerView;
    ImageButton menuBtn;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_list);

//        addAppoitementBtn = findViewById(R.id.add_note_btn);
        recyclerView = findViewById(R.id.recyler_view);
        menuBtn = findViewById(R.id.menu_btn);

//        addAppoitementBtn.setOnClickListener((v)-> startActivity(new Intent(MainActivityUserList.this, AppointementDetailsActivity.class)) );
        menuBtn.setOnClickListener((v)->showMenu() );
        setupRecyclerView();
    }

    void showMenu(){
        PopupMenu popupMenu  = new PopupMenu(MainActivityUserList.this,menuBtn);
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle()=="Logout"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivityUserList.this,LoginActivity.class));
                    finish();
                    return true;
                }
                return false;
            }
        });

    }

    void setupRecyclerView(){


        Query query = Utility.getCollectionReferenceForUser().orderBy("email", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<UserDto> options = new FirestoreRecyclerOptions.Builder<UserDto>()
                .setQuery(query, UserDto.class)
                .build();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(options,this);
        recyclerView.setAdapter(userAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        userAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        userAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userAdapter.notifyDataSetChanged();
    }
}