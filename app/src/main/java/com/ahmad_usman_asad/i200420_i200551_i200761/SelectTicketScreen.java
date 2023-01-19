package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ahmad_usman_asad.i200420_i200551_i200761.Adapters.TicketAdapter;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ReservationModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ScheduleModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ScheduleTicketMergeModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SelectTicketScreen extends AppCompatActivity {

    RecyclerView ticketsRV;
    ImageView backBtn;
    ArrayList<ScheduleTicketMergeModel> ticketsList;
    TicketAdapter ticketAdapter;
    public static final String SEAT_ID = "com.ahmad_usman_asad.i200420_i200551_i200761.SEAT_ID";
    public static final String RESERVATION_ID = "com.ahmad_usman_asad.i200420_i200551_i200761.RESERVATION_ID";
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_ticket_screen);
        backBtn = findViewById(R.id.backBtn);
        ticketsRV = findViewById(R.id.ticketsRV);
        ticketsList = new ArrayList<ScheduleTicketMergeModel>();
        progressBar = findViewById(R.id.ProgressBar);
        ticketsRV.setLayoutManager(new LinearLayoutManager(this));
        ticketAdapter = new TicketAdapter(ticketsList,SelectTicketScreen.this,"Refund");
        ticketsRV.setAdapter(ticketAdapter);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        progressBar.setVisibility(View.VISIBLE);

        FirebaseDatabase.getInstance().getReference("Reservations").orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressBar.setVisibility(View.VISIBLE);

                for(DataSnapshot ds: snapshot.getChildren()){

                    ReservationModel reserv = ds.getValue(ReservationModel.class);

                    FirebaseDatabase.getInstance().getReference("Schedules").child(reserv.getSchedule_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            ScheduleModel sched = snapshot.getValue(ScheduleModel.class);
                            LocalDateTime dep = LocalDateTime.parse(sched.getDeparture_time(), DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));

                            if(dep.isAfter(LocalDateTime.now())) {
                                for (int i = 0; i < reserv.getTickets().size(); i++) {
                                    ticketsList.add(new ScheduleTicketMergeModel(reserv.getTickets().get(i), sched, reserv.getReservation_id()));
                                    ticketAdapter.notifyItemInserted(ticketsList.size());
                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });




                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}