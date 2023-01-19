package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ReservationModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ScheduleModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ShowReservationScreen extends AppCompatActivity {


    TextView departLocation;
    TextView departDate;
    TextView departTime;
    TextView arriLocation;
    TextView arriDate;
    TextView arriTime;
    TextView seatPrice;
    TextView seatNo;
    Button printBtn;
    ImageView backBtn;
    String reservation_id;
    String seat_id;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_reservation_screen);

        departLocation = findViewById(R.id.departLocation);
        departDate = findViewById(R.id.departDate);
        departTime = findViewById(R.id.departTime);
        arriLocation = findViewById(R.id.arriLocation);
        arriDate = findViewById(R.id.arriDate);
        arriTime = findViewById(R.id.arriTime);
        seatNo = findViewById(R.id.seatNo);
        seatPrice = findViewById(R.id.seatPrice);
        printBtn = findViewById(R.id.printBtn);
        backBtn = findViewById(R.id.backBtn);
        progressBar = findViewById(R.id.ProgressBar);

        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();

        reservation_id = intent.getStringExtra("RES_ID");
        seat_id = intent.getStringExtra("SEAT_ID");


        FirebaseDatabase.getInstance().getReference("Reservations").child(reservation_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ReservationModel reserv = snapshot.getValue(ReservationModel.class);

                FirebaseDatabase.getInstance().getReference("Schedules").child(reserv.getSchedule_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        ScheduleModel schedule = snapshot.getValue(ScheduleModel.class);

                        LocalDateTime dep = LocalDateTime.parse(schedule.getDeparture_time(), DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));
                        LocalDateTime arr = LocalDateTime.parse(schedule.getArrival_time(), DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));
                        departLocation.setText(schedule.getDeparture());
                        arriLocation.setText(schedule.getArrival());
                        departDate.setText("Date: "+DateTimeFormatter.ofPattern("E, dd MMM yyyy").format(dep));
                        departTime.setText("Time: "+DateTimeFormatter.ofPattern("hh:mm a").format(dep));
                        arriDate.setText("Date: "+DateTimeFormatter.ofPattern("E, dd MMM yyyy").format(arr));
                        arriTime.setText("Time: "+DateTimeFormatter.ofPattern("hh:mm a").format(arr));
                        seatPrice.setText("Ticket Price: "+schedule.getTicket_price());
                        seatNo.setText("Seat No: "+seat_id);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });





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

        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Ticket Printed Successfully!!", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });



    }
}