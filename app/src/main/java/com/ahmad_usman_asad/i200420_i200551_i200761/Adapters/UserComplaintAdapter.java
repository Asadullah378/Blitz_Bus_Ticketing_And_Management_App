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
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.BusModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ComplaintModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.R;
import com.ahmad_usman_asad.i200420_i200551_i200761.ViewComplaintScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserComplaintAdapter extends RecyclerView.Adapter<UserComplaintAdapter.ViewHolder> {
    ArrayList<ComplaintModel> complaints;
    Context context;

    public UserComplaintAdapter(ArrayList<ComplaintModel> complaints, Context context) {
        this.complaints = complaints;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_complaint_item, parent, false);
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

        if(complaints.get(holder.getAdapterPosition()).getReplyGiven()){

            holder.complaintStatus.setText("Catered");
            holder.complaintStatus.setTextColor(Color.parseColor("#0ABA11"));

            if(!complaints.get(holder.getAdapterPosition()).getReplySeen()){
                holder.unseen.setVisibility(View.VISIBLE);
            }

        }

        else{
            holder.complaintStatus.setText("Pending");
            holder.complaintStatus.setTextColor(Color.parseColor("#F60000"));
        }


        holder.complaintCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewComplaintScreen.class);
                intent.putExtra(ManageComplaintsScreen.COMPLAINT_ID,complaints.get(holder.getAdapterPosition()).getComplaint_id());
                intent.putExtra("SEEN",true);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView complaintType;
        TextView complaintStatus;
        TextView complaintTxt;
        CardView complaintCard;
        ImageView unseen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            complaintType = itemView.findViewById(R.id.complaintType);
            complaintStatus = itemView.findViewById(R.id.complaintStatus);
            complaintTxt = itemView.findViewById(R.id.complaintTxt);
            complaintCard = itemView.findViewById(R.id.complaint_card);
            unseen = itemView.findViewById(R.id.unseenIcon);
        }
    }



}
