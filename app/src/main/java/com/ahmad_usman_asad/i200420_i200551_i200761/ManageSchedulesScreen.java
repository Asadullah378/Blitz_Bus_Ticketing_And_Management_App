package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ahmad_usman_asad.i200420_i200551_i200761.Adapters.BusAdapter;
import com.ahmad_usman_asad.i200420_i200551_i200761.Adapters.ScheduleAdapter;
import com.ahmad_usman_asad.i200420_i200551_i200761.External.CustomDateTimePicker;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.BusModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ScheduleModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ManageSchedulesScreen extends AppCompatActivity {

    FloatingActionButton addSchedulesBtn;
    ImageView backBtn;
    RecyclerView scheduleRV;
    ScheduleAdapter scheduleAdapter;
    ArrayList<ScheduleModel> all_schedules;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_schedules_screen);

        addSchedulesBtn = findViewById(R.id.addScheduleScreen);
        backBtn = findViewById(R.id.backBtn);

        all_schedules = new ArrayList<ScheduleModel>();
        scheduleRV = findViewById(R.id.scheduleRV);
        scheduleRV.setLayoutManager(new LinearLayoutManager(this));
        scheduleAdapter = new ScheduleAdapter(all_schedules,this,"Manager");
        scheduleRV.setAdapter(scheduleAdapter);

        FirebaseDatabase.getInstance().getReference("Schedules").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                all_schedules.add(snapshot.getValue(ScheduleModel.class));
                scheduleAdapter.notifyItemInserted(all_schedules.size());
                scheduleRV.scrollToPosition(all_schedules.size());
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

        addSchedulesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageSchedulesScreen.this, AddSchedulesScreen.class);
                startActivity(intent);
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