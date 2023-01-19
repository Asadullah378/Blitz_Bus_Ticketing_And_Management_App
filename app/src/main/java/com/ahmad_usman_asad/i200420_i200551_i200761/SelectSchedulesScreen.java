package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ahmad_usman_asad.i200420_i200551_i200761.Adapters.ScheduleAdapter;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ScheduleModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SelectSchedulesScreen extends AppCompatActivity {

    ImageView backBtn;
    RecyclerView scheduleRV;
    ScheduleAdapter scheduleAdapter;
    ArrayList<ScheduleModel> all_schedules;
    public static final String SCHEDULE_ID = "com.ahmad_usman_asad.i200420_i200551_i200761.SCHEDULE_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_schedules_screen);

        backBtn = findViewById(R.id.backBtn);
        scheduleRV = findViewById(R.id.selectScheduleRV);
        all_schedules = new ArrayList<ScheduleModel>();
        scheduleRV.setLayoutManager(new LinearLayoutManager(this));
        scheduleAdapter = new ScheduleAdapter(all_schedules,this,"User");
        scheduleRV.setAdapter(scheduleAdapter);


        FirebaseDatabase.getInstance().getReference("Schedules").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                ScheduleModel sched = snapshot.getValue(ScheduleModel.class);

                LocalDateTime dep = LocalDateTime.parse(sched.getDeparture_time(), DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));

                if(dep.isAfter(LocalDateTime.now())){
                    all_schedules.add(sched);
                    scheduleAdapter.notifyItemInserted(all_schedules.size());
                    scheduleRV.scrollToPosition(all_schedules.size());
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                for(int i=0; i<all_schedules.size(); i++){

                    if(all_schedules.get(i).getSchedule_id().equals(snapshot.getValue(ScheduleModel.class).getSchedule_id())){

                        all_schedules.remove(i);
                        scheduleAdapter.notifyDataSetChanged();
                    }

                }

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