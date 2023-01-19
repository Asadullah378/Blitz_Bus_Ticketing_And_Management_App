package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmad_usman_asad.i200420_i200551_i200761.Adapters.SelectSeatsAdapter;
import com.ahmad_usman_asad.i200420_i200551_i200761.Interfaces.SelectSeatInterface;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ScheduledBusModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.SeatModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.SeatRowModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectSeatsScreen extends AppCompatActivity implements SelectSeatInterface {

    RecyclerView selectSeatsRV;
    String schedule_id;
    ScheduledBusModel scheduledBus;
    SelectSeatsAdapter selectSeatsAdapter;
    ImageView backBtn;
    Button purchaseTickets;
    TextView seatsCount;
    ArrayList<SeatModel> selectedSeats;
    SelectSeatInterface selectSeatInterface;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_seats_screen);
        backBtn = findViewById(R.id.backBtn);
        selectSeatsRV = findViewById(R.id.selectSeatsRV);
        selectSeatsRV.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        schedule_id = intent.getStringExtra(SelectSchedulesScreen.SCHEDULE_ID);
        purchaseTickets = findViewById(R.id.moveToPurchase);
        seatsCount = findViewById(R.id.seatsCount);
        selectedSeats = new ArrayList<SeatModel>();
        seatsCount.setText("Selected Seats: 0");
        selectSeatInterface = this;
        progressBar = findViewById(R.id.ProgressBar);
        progressBar.setVisibility(View.VISIBLE);

        FirebaseDatabase.getInstance().getReference("ScheduledBuses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressBar.setVisibility(View.VISIBLE);

                for(DataSnapshot ds: snapshot.getChildren()){

                    if(ds.getValue(ScheduledBusModel.class).getSchedule_id().equals(schedule_id)){

                        scheduledBus = ds.getValue(ScheduledBusModel.class);

                        ArrayList<SeatRowModel> seatRows = new ArrayList<SeatRowModel>();

                        for(int i=0; i<scheduledBus.getTotal_seats(); i+=4)
                            seatRows.add(new SeatRowModel(new ArrayList<SeatModel>(scheduledBus.getSeats().subList(i,i+4))));

                        selectSeatsAdapter=new SelectSeatsAdapter(seatRows,SelectSeatsScreen.this, selectSeatInterface);
                        selectSeatsRV.setAdapter(selectSeatsAdapter);

                        break;

                    }

                }
                progressBar.setVisibility(View.GONE);


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

        purchaseTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedSeats.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Select Atleast 1 Seat!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(SelectSeatsScreen.this, PurchaseTicketsScreen.class);
                    intent.putExtra(SelectSchedulesScreen.SCHEDULE_ID, schedule_id);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("Selected Seats", selectedSeats);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void addSeat(int id) {

        for(int i=0; i<scheduledBus.getSeats().size(); i++){

            if(scheduledBus.getSeats().get(i).getSeat_id()==id){
                selectedSeats.add(scheduledBus.getSeats().get(i));
                break;
            }
        }

        seatsCount.setText("Selected Seats: "+selectedSeats.size());

    }

    @Override
    public void removeSeat(int id) {

        for(int i=0; i<selectedSeats.size(); i++){

            if(selectedSeats.get(i).getSeat_id()==id){
                selectedSeats.remove(i);
                break;

            }

        }

        seatsCount.setText("Selected Seats: "+selectedSeats.size());

    }
}