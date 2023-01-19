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
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ScheduledBusModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.TransactionModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RefundTicketScreen extends AppCompatActivity {

    TextView departLocation;
    TextView departDate;
    TextView departTime;
    TextView arriLocation;
    TextView arriDate;
    TextView arriTime;
    TextView seatNum;
    TextView seatPrice;
    TextView refundAmount;
    Button refundBtn;
    ImageView backBtn;
    int seat_id;
    String schedule_id;
    String reservation_id;
    ScheduleModel schedule;
    UserModel user;
    ScheduledBusModel scheduledBus;
    ReservationModel reservation;
    DatabaseReference updateBusRef;
    DatabaseReference updateReservation;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refund_ticket_screen);

        departLocation = findViewById(R.id.departLocation);
        departDate = findViewById(R.id.departDate);
        departTime = findViewById(R.id.departTime);
        arriLocation = findViewById(R.id.arriLocation);
        arriDate = findViewById(R.id.arriDate);
        arriTime = findViewById(R.id.arriTime);
        seatNum = findViewById(R.id.seatID);
        seatPrice = findViewById(R.id.seatPrice);
        refundAmount = findViewById(R.id.refundAmount);
        refundBtn = findViewById(R.id.refundBtn);
        backBtn = findViewById(R.id.backBtn);

        Intent intent = getIntent();
        seat_id = intent.getIntExtra(SelectTicketScreen.SEAT_ID,0);
        schedule_id = intent.getStringExtra(SelectSchedulesScreen.SCHEDULE_ID);
        reservation_id = intent.getStringExtra(SelectTicketScreen.RESERVATION_ID);
        progressBar = findViewById(R.id.ProgressBar);
        progressBar.setVisibility(View.VISIBLE);

        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(UserModel.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Reservations").child(reservation_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reservation = snapshot.getValue(ReservationModel.class);
                updateReservation = snapshot.getRef();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("ScheduledBuses").orderByChild("schedule_id").equalTo(schedule_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds: snapshot.getChildren()){

                    if(ds.getValue(ScheduledBusModel.class).getSchedule_id().equals(schedule_id)){
                        scheduledBus = ds.getValue(ScheduledBusModel.class);
                        updateBusRef = ds.getRef();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Schedules").orderByKey().equalTo(schedule_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds: snapshot.getChildren()){

                    if(ds.getValue(ScheduleModel.class).getSchedule_id().equals(schedule_id)){
                        schedule = ds.getValue(ScheduleModel.class);
                    }

                }

                LocalDateTime dep = LocalDateTime.parse(schedule.getDeparture_time(), DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));
                LocalDateTime arr = LocalDateTime.parse(schedule.getArrival_time(), DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));
                departLocation.setText(schedule.getDeparture());
                arriLocation.setText(schedule.getArrival());
                departDate.setText("Date: "+DateTimeFormatter.ofPattern("E, dd MMM yyyy").format(dep));
                departTime.setText("Time: "+DateTimeFormatter.ofPattern("hh:mm a").format(dep));
                arriDate.setText("Date: "+DateTimeFormatter.ofPattern("E, dd MMM yyyy").format(arr));
                arriTime.setText("Time: "+DateTimeFormatter.ofPattern("hh:mm a").format(arr));
                seatPrice.setText("Ticket Price: "+schedule.getTicket_price());
                seatNum.setText("Seat No: "+seat_id);
                refundAmount.setText("Refund Payment: "+0.85*schedule.getTicket_price());
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

        refundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                user.setBalance(user.getBalance()+0.85*schedule.getTicket_price());

                for(int i=0; i<reservation.getTickets().size(); i++){

                    if(reservation.getTickets().get(i).getSeat_id() == seat_id)
                        reservation.getTickets().remove(i);

                }

                for(int i=0; i<scheduledBus.getSeats().size(); i++){

                    if(scheduledBus.getSeats().get(i).getSeat_id() == seat_id)
                        scheduledBus.getSeats().get(i).setBooking_status(false);

                }

                TransactionModel transac = new TransactionModel(0.85 * schedule.getTicket_price(), DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a").format(LocalDateTime.now()),"In","Tickets Refund");
                user.getTransactions().add(transac);

                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            updateReservation.setValue(reservation).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                        updateBusRef.setValue(scheduledBus).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){

                                                    progressBar.setVisibility(View.GONE);

                                                    Toast.makeText(getApplicationContext(), "Refund Successful!!", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(RefundTicketScreen.this, UserMainScreen.class);
                                                    startActivity(intent);



                                                }

                                                else{
                                                    progressBar.setVisibility(View.GONE);

                                                    Toast.makeText(getApplicationContext(), "Refund Failed!!", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(RefundTicketScreen.this, UserMainScreen.class);
                                                    startActivity(intent);
                                                }

                                            }
                                        });

                                    }

                                    else{
                                        progressBar.setVisibility(View.GONE);

                                        Toast.makeText(getApplicationContext(), "Refund Failed!!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RefundTicketScreen.this, UserMainScreen.class);
                                        startActivity(intent);
                                    }
                                }
                            });

                        }

                        else{
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(getApplicationContext(), "Refund Failed!!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RefundTicketScreen.this, UserMainScreen.class);
                            startActivity(intent);
                        }

                    }
                });


            }
        });

    }
}