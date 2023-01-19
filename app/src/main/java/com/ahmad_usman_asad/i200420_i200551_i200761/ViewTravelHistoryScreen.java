package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ahmad_usman_asad.i200420_i200551_i200761.Adapters.HistoryAdapter;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ReservationModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ReservationScheduleMergeModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ScheduleModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewTravelHistoryScreen extends AppCompatActivity {


    ArrayList<ReservationScheduleMergeModel> reservations;
    HistoryAdapter historyAdapter;
    ImageView backBtn;
    RecyclerView reservationsRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_travel_history_screen);

        backBtn = findViewById(R.id.backBtn);
        reservationsRV = findViewById(R.id.reservationsRV);

        reservationsRV.setLayoutManager(new LinearLayoutManager(this));
        reservations = new ArrayList<ReservationScheduleMergeModel>();
        historyAdapter = new HistoryAdapter(reservations,this);
        reservationsRV.setAdapter(historyAdapter);

        FirebaseDatabase.getInstance().getReference("Reservations").orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                ReservationModel reserv = snapshot.getValue(ReservationModel.class);

                FirebaseDatabase.getInstance().getReference("Schedules").child(reserv.getSchedule_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        ScheduleModel sch = snapshot.getValue(ScheduleModel.class);
                        reservations.add(new ReservationScheduleMergeModel(sch,reserv));
                        historyAdapter.notifyItemInserted(reservations.size());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}