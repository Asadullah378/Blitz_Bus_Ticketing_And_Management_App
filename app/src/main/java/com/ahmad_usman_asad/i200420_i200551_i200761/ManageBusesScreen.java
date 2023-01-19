package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ahmad_usman_asad.i200420_i200551_i200761.Adapters.BusAdapter;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.BusModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class ManageBusesScreen extends AppCompatActivity {

    FloatingActionButton addBusesScreen;
    RecyclerView busRV;
    ImageView backBtn;
    ArrayList<BusModel> all_buses;
    BusAdapter busAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_buses_screen);
        addBusesScreen = findViewById(R.id.addBusScreen);
        busRV = findViewById(R.id.busRV);
        backBtn = findViewById(R.id.backBtn);
        all_buses = new ArrayList<BusModel>();

        busRV.setLayoutManager(new LinearLayoutManager(this));
        busAdapter = new BusAdapter(all_buses,this);
        busRV.setAdapter(busAdapter);

        FirebaseDatabase.getInstance().getReference("Buses").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                all_buses.add(snapshot.getValue(BusModel.class));
                busAdapter.notifyItemInserted(all_buses.size());
                busRV.scrollToPosition(all_buses.size());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                for(int i=0; i<all_buses.size(); i++){

                    if(all_buses.get(i).getBusNumber().equals(snapshot.getValue(BusModel.class).getBusNumber())){

                        all_buses.remove(i);
                        busAdapter.notifyDataSetChanged();
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

        addBusesScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ManageBusesScreen.this, AddBusScreen.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("Buses", all_buses);
                intent.putExtras(bundle);
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