package com.ahmad_usman_asad.i200420_i200551_i200761.Adapters;

import android.content.Context;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad_usman_asad.i200420_i200551_i200761.Models.AnnouncementModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.BusModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {
    ArrayList<AnnouncementModel> announcements;
    Context context;
    String role;

    public AnnouncementAdapter(ArrayList<AnnouncementModel> announcements, Context context, String role) {
        this.announcements = announcements;
        this.context = context;
        this.role = role;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.announcement_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.announcementTitle.setText(announcements.get(position).getTitle());
        holder.announcementDetails.setText(announcements.get(position).getBody());

        LocalDateTime ldt = LocalDateTime.parse(announcements.get(position).getDate(), DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));

        holder.announcementTime.setText(DateTimeFormatter.ofPattern("hh:mm a").format(ldt));

        String s1 ="";
        String s2 ="";

        LocalDateTime cDate = LocalDateTime.now();

        if (ChronoUnit.DAYS.between(ldt, cDate) == 0)
            s1 = "Today";

        else if (ChronoUnit.DAYS.between(ldt, cDate) == 1)
            s1 = "Yesterday";

        else if (ChronoUnit.DAYS.between(ldt, cDate) < 7)
            s1 = DateTimeFormatter.ofPattern("EEEE").format(ldt);

        else if (ChronoUnit.YEARS.between(ldt, cDate) < 1)
            s1 = DateTimeFormatter.ofPattern("E, MMM d").format(ldt);

        else
            s1 = DateTimeFormatter.ofPattern("MMM dd, yyyy").format(ldt);
        if (holder.getAdapterPosition() - 1 >= 0) {

            LocalDateTime ldt2 = LocalDateTime.parse(announcements.get(holder.getAdapterPosition()-1).getDate(), DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));

                if (ChronoUnit.DAYS.between(ldt2, cDate) == 0)
                    s2 = "Today";

                else if (ChronoUnit.DAYS.between(ldt2, cDate) == 1)
                    s2 = "Yesterday";

                else if (ChronoUnit.DAYS.between(ldt2, cDate) < 7)
                    s2 = DateTimeFormatter.ofPattern("EEEE").format(ldt2);

                else if (ChronoUnit.YEARS.between(ldt2, cDate) < 1)
                    s2 = DateTimeFormatter.ofPattern("E, MMM d").format(ldt2);

                else
                    s2 = DateTimeFormatter.ofPattern("MMM dd, yyyy").format(ldt2);

            }

            if (s1.equals(s2))
                holder.dateCard.setVisibility(View.GONE);
            else {
                holder.announcementDate.setText(s1);
            }
            
            
            if(role.equals("User"))
                holder.delAnnouncement.setVisibility(View.INVISIBLE);
            
            else if(role.equals("Manager")) {
                
                holder.delAnnouncement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        
                        FirebaseDatabase.getInstance().getReference("Announcements").child(announcements.get(holder.getAdapterPosition()).getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                
                                if(!task.isSuccessful()){
                                    Toast.makeText(context, "Could not Delete Announcement!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        
                    }
                });
                
            }

    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView announcementTitle;
        TextView announcementTime;
        TextView announcementDetails;
        TextView announcementDate;
        CardView dateCard;
        Button delAnnouncement;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            announcementTitle = itemView.findViewById(R.id.announcementTitle);
            announcementTime = itemView.findViewById(R.id.annoucementTime);
            announcementDetails = itemView.findViewById(R.id.announcementDetails);
            announcementDate = itemView.findViewById(R.id.annoucementDate);
            dateCard = itemView.findViewById(R.id.dateCard);
            delAnnouncement = itemView.findViewById(R.id.delAnnouncement);
        }
    }



}
