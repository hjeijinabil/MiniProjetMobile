package com.example.miniproject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointementDetailsActivity extends AppCompatActivity {

    EditText nameEditText,dateEditText;
    ImageButton saveAppointementBtn;
    TextView pageTitleTextView;
    String name,date,docId;
    boolean isEditMode = false;
    TextView deleteAppointementTextViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointement_details);

        nameEditText = findViewById(R.id.app_name_text);
        dateEditText = findViewById(R.id.app_date_text);
        saveAppointementBtn = findViewById(R.id.save_app_btn);
        pageTitleTextView = findViewById(R.id.page_title);
        deleteAppointementTextViewBtn  = findViewById(R.id.delete_note_text_view_btn);

        //receive data
        name = getIntent().getStringExtra("name");
        date= getIntent().getStringExtra("date");
        docId = getIntent().getStringExtra("docId");

        if(docId!=null && !docId.isEmpty()){
            isEditMode = true;
        }

        nameEditText.setText(name);
        dateEditText.setText(date);
        if(isEditMode){
            pageTitleTextView.setText("Edit your appointement");
            deleteAppointementTextViewBtn.setVisibility(View.VISIBLE);
        }

        saveAppointementBtn.setOnClickListener( (v)-> saveNote());

        deleteAppointementTextViewBtn.setOnClickListener((v)-> deleteAppointementFromFirebase() );

    }

    void saveNote(){
        String appname = nameEditText.getText().toString();
        String namedate = dateEditText.getText().toString();
        if(appname==null || namedate.isEmpty() ){
            dateEditText.setError("name is required");
            return;
        }
        Appointement appointement = new Appointement();
        appointement.setName(appname);




        appointement.setScheduledDate(Timestamp.now()); // to be changed
        appointement.setTimestamp(Timestamp.now());

        saveAppointementToFirebase(appointement);

    }

    void saveAppointementToFirebase(Appointement appointement){
        DocumentReference documentReference;
        if(isEditMode){
            //update the note
            documentReference = Utility.getCollectionReferenceForAppointement().document(docId);
        }else{
            //create new note
            documentReference = Utility.getCollectionReferenceForAppointement().document();
        }



        documentReference.set(appointement).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //note is added
                    Utility.showToast(AppointementDetailsActivity.this,"Appointement added successfully");
                    finish();
                }else{
                    Utility.showToast(AppointementDetailsActivity.this,"Failed while adding Appointement");
                }
            }
        });

    }

    void deleteAppointementFromFirebase(){
        DocumentReference documentReference;
            documentReference = Utility.getCollectionReferenceForAppointement().document(docId);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //note is deleted
                    Utility.showToast(AppointementDetailsActivity.this,"Appointement deleted successfully");
                    finish();
                }else{
                    Utility.showToast(AppointementDetailsActivity.this,"Failed while deleting Appointement");
                }
            }
        });
    }


}