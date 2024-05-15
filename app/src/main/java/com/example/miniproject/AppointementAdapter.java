package com.example.miniproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class AppointementAdapter extends FirestoreRecyclerAdapter<Appointement, AppointementAdapter.AppointementViewHolder> {
    Context context;

    public AppointementAdapter(@NonNull FirestoreRecyclerOptions<Appointement> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull AppointementViewHolder holder, int position, @NonNull Appointement appointement) {
        holder.nameTextView.setText(appointement.name);
        holder.dateTextView.setText(Utility.timestampToString(appointement.scheduledDate));
        holder.timestampTextView.setText(Utility.timestampToString(appointement.timestamp));

        holder.itemView.setOnClickListener((v)->{
            Intent intent = new Intent(context,AppointementDetailsActivity.class);
            intent.putExtra("name",appointement.name);
            intent.putExtra("date",appointement.scheduledDate);
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId",docId);
            context.startActivity(intent);
        });

    }

    @NonNull
    @Override
    public AppointementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_appointement_item,parent,false);
        return new AppointementViewHolder(view);
    }

    class AppointementViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView,dateTextView,timestampTextView;

        public AppointementViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.app_name_text_view);
            dateTextView = itemView.findViewById(R.id.app_date_text_view);
            timestampTextView = itemView.findViewById(R.id.app_timestamp_text_view);
        }
    }
}
