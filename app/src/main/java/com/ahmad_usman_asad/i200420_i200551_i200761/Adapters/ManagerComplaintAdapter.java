package com.ahmad_usman_asad.i200420_i200551_i200761.Adapters;

import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad_usman_asad.i200420_i200551_i200761.ManageComplaintsScreen;
import com.ahmad_usman_asad.i200420_i200551_i200761.ManagerManageComplaintsScreen;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.BusModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ComplaintModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.R;
import com.ahmad_usman_asad.i200420_i200551_i200761.ReplyComplaintScreen;
import com.ahmad_usman_asad.i200420_i200551_i200761.ViewComplaintScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ManagerComplaintAdapter extends RecyclerView.Adapter<ManagerComplaintAdapter.ViewHolder> {
    ArrayList<ComplaintModel> complaints;
    Context context;

    public ManagerComplaintAdapter(ArrayList<ComplaintModel> complaints, Context context) {
        this.complaints = complaints;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.manager_complaint_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String complaint = complaints.get(position).getComplaint();

        holder.complaintType.setText(complaints.get(position).getComplaint_type());

        if(complaint.length()<=25){
            holder.complaintTxt.setText(complaint);

        }

        else{
            holder.complaintTxt.setText(complaint.substring(0,25) +"....");
        }

        FirebaseDatabase.getInstance().getReference("Users").child(complaints.get(position).getUser_id()).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.complaintUser.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewComplaintScreen.class);
                intent.putExtra(ManageComplaintsScreen.COMPLAINT_ID,complaints.get(holder.getAdapterPosition()).getComplaint_id());
                context.startActivity(intent);
            }
        });

        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if(!complaints.get(holder.getAdapterPosition()).getReplyGiven()) {

                    Intent intent = new Intent(context, ReplyComplaintScreen.class);
                    intent.putExtra(ManageComplaintsScreen.COMPLAINT_ID, complaints.get(holder.getAdapterPosition()).getComplaint_id());
                    intent.putExtra(ManagerManageComplaintsScreen.USERNAME, holder.complaintUser.getText().toString());
                    intent.putExtra("SEEN", false);
                    context.startActivity(intent);
                }
                else
                    Toast.makeText(context, "Already Replied to This Complaint!!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView complaintUser;
        TextView complaintType;
        TextView complaintTxt;
        ImageView reply;
        ImageView view;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            complaintUser = itemView.findViewById(R.id.complaintUser);
            complaintType = itemView.findViewById(R.id.complaintType);
            complaintTxt = itemView.findViewById(R.id.complaintTxt);
            reply = itemView.findViewById(R.id.replyBtn);
            view = itemView.findViewById(R.id.viewBtn);
        }
    }



}
