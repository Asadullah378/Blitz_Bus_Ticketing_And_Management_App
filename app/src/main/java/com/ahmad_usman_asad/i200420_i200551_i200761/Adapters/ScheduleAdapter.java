package com.ahmad_usman_asad.i200420_i200551_i200761.Adapters;

import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ReservationModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ScheduleModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.TransactionModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.UserModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.R;
import com.ahmad_usman_asad.i200420_i200551_i200761.SelectSchedulesScreen;
import com.ahmad_usman_asad.i200420_i200551_i200761.SelectSeatsScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    ArrayList<ScheduleModel> schedules;
    Context context;
    String role;

    public ScheduleAdapter(ArrayList<ScheduleModel> schedules, Context context, String role) {
        this.schedules = schedules;
        this.context = context;
        this.role = role;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.schedule_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        LocalDateTime arr = LocalDateTime.parse(schedules.get(position).getArrival_time(),DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));
        LocalDateTime dep = LocalDateTime.parse(schedules.get(position).getDeparture_time(),DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));

        String arrival = schedules.get(position).getArrival();
        String departure = schedules.get(position).getDeparture();
        String id = schedules.get(position).getSchedule_id();

        holder.arrLocation.setText(schedules.get(position).getArrival());
        holder.depLocation.setText(schedules.get(position).getDeparture());
        holder.arrTime.setText(DateTimeFormatter.ofPattern("hh:mm a").format(arr));
        holder.arrDate.setText(DateTimeFormatter.ofPattern("E, dd MMM yyyy").format(arr));
        holder.depTime.setText(DateTimeFormatter.ofPattern("hh:mm a").format(dep));
        holder.depDate.setText(DateTimeFormatter.ofPattern("E, dd MMM yyyy").format(dep));
        holder.price.setText("PKR. "+schedules.get(position).getTicket_price());

        if(holder.getAdapterPosition()%2==0){
            holder.schedule_card.setCardBackgroundColor(Color.parseColor("#A2E4A5"));

        }
        else{
            holder.schedule_card.setCardBackgroundColor(Color.parseColor("#59E15F"));
        }

        if(role.equals("Manager")) {
            holder.sched_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (dep.isBefore(LocalDateTime.now()))
                        Toast.makeText(context, "Cannot Delete a Schedule After Departure!!!", Toast.LENGTH_SHORT).show();

                    else {
                        FirebaseDatabase.getInstance().getReference("ScheduledBuses").orderByChild("schedule_id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot ds : snapshot.getChildren()) {

                                    ds.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        FirebaseDatabase.getInstance().getReference("Reservations").orderByChild("schedule_id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot ds : snapshot.getChildren()) {

                                    ReservationModel reserv = ds.getValue(ReservationModel.class);
                                    FirebaseDatabase.getInstance().getReference("Users").child(ds.getValue(ReservationModel.class).getUser_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            UserModel user = snapshot.getValue(UserModel.class);

                                            double refund = reserv.getTickets().size() * schedules.get(holder.getAdapterPosition()).getTicket_price();
                                            user.setBalance(user.getBalance() + refund);
                                            TransactionModel transac = new TransactionModel(refund, DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a").format(LocalDateTime.now()), "In", "Bus Dropped Refund (" + reserv.getTickets().size() + " Tickets)");
                                            user.getTransactions().add(transac);
                                            snapshot.getRef().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()) {

                                                        ArrayList<String> recieverPlayerIDs = new ArrayList<String>();

                                                        FirebaseDatabase.getInstance().getReference("Users").child(reserv.getUser_id()).child("playerIDs").addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                                for (DataSnapshot ds : snapshot.getChildren())
                                                                    recieverPlayerIDs.add(ds.getValue(String.class));

                                                                JSONObject json = new JSONObject();
                                                                JSONObject j1 = new JSONObject();
                                                                JSONObject j2 = new JSONObject();

                                                                try {
                                                                    j1.put("en", "You have been refunded PKR." + refund + " as Bus From " + departure + " To " + arrival + " is Dropped!!");
                                                                    j2.put("en", "Bus Dropped");
                                                                    json.put("contents", j1);
                                                                    json.put("headings", j2);
                                                                    json.put("include_player_ids", new JSONArray(recieverPlayerIDs.toArray()));
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }

                                                                OneSignal.postNotification(json, new OneSignal.PostNotificationResponseHandler() {
                                                                    @Override
                                                                    public void onSuccess(JSONObject jsonObject) {
                                                                        System.out.println("Notification Sent!!");
                                                                    }

                                                                    @Override
                                                                    public void onFailure(JSONObject jsonObject) {
                                                                        System.out.println("Notification Not Sent!!"+jsonObject.toString());
                                                                    }
                                                                });


                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError error) {

                                                            }
                                                        });


                                                    }

                                                }
                                            });


                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    ds.getRef().removeValue();

                                }

                                FirebaseDatabase.getInstance().getReference("Schedules").child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (!task.isSuccessful()) {
                                            Toast.makeText(context.getApplicationContext(), "Could Not Delete Schedule", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                }
            });
        }
        else if(role.equals("User")){
            holder.sched_del.setVisibility(View.INVISIBLE);
            holder.schedule_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    
                    if(FirebaseAuth.getInstance().getCurrentUser()!=null) {

                        Intent intent = new Intent(context, SelectSeatsScreen.class);
                        intent.putExtra(SelectSchedulesScreen.SCHEDULE_ID, schedules.get(holder.getAdapterPosition()).getSchedule_id());
                        context.startActivity(intent);
                    }
                    else{
                        Toast.makeText(context, "Login To Purchase Tickets!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView depTime;
        TextView arrTime;
        TextView depLocation;
        TextView arrLocation;
        TextView price;
        TextView arrDate;
        TextView depDate;
        CardView schedule_card;
        Button sched_del;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            depTime = itemView.findViewById(R.id.depTime);
            arrTime = itemView.findViewById(R.id.arrTime);
            depDate = itemView.findViewById(R.id.depDate);
            arrDate = itemView.findViewById(R.id.arrDate);
            depLocation = itemView.findViewById(R.id.depLocation);
            arrLocation = itemView.findViewById(R.id.arrLocation);
            price = itemView.findViewById(R.id.price);
            sched_del = itemView.findViewById(R.id.sched_del);
            schedule_card = itemView.findViewById(R.id.schedule_card);
        }
    }



}
