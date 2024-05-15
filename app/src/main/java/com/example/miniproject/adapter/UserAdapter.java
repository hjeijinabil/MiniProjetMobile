package com.example.miniproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject.MainActivityDoctor;
import com.example.miniproject.R;
import com.example.miniproject.model.UserDto;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class UserAdapter extends FirestoreRecyclerAdapter<UserDto, UserAdapter.UserViewHolder> {
    Context context;

    public UserAdapter(@NonNull FirestoreRecyclerOptions<UserDto> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull UserDto user) {
        holder.emailTextView.setText(user.email);


        holder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context, MainActivityDoctor.class);
            intent.putExtra("uuid",user.uuid);
//            String docId = this.getSnapshots().getSnapshot(position).getId();
//            intent.putExtra("docId",docId);
            context.startActivity(intent);
        });

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_user_item,parent,false);
        return new UserViewHolder(view);
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        TextView emailTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            emailTextView = itemView.findViewById(R.id.user_email_text_view);

        }
    }
}
