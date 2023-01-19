package com.ahmad_usman_asad.i200420_i200551_i200761;

import static com.ahmad_usman_asad.i200420_i200551_i200761.Notifications.ApplicationClass.CHANNEL_1_ID;
import static com.ahmad_usman_asad.i200420_i200551_i200761.Notifications.ApplicationClass.CHANNEL_3_ID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmad_usman_asad.i200420_i200551_i200761.External.CustomDateTimePicker;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.BusModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ScheduleModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ScheduledBusModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.SeatModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddSchedulesScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText arrivalLocation;
    EditText departureLocation;
    EditText ticketPrice;
    TextView departureTime;
    TextView arrivalTime;
    MaterialCardView selectDeparture;
    MaterialCardView selectArrival;
    Button addScheduleBtn;
    ImageView backBtn;
    Spinner busSpinner;
    BusModel current_bus;
    ArrayList<BusModel> buses;
    LocalDateTime d_ldt;
    LocalDateTime a_ldt;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_schedules_screen);

        progressBar = findViewById(R.id.ProgressBar);
        arrivalTime = findViewById(R.id.arrivalTime);
        departureTime = findViewById(R.id.departureTime);
        backBtn = findViewById(R.id.backBtn);
        arrivalLocation = findViewById(R.id.arrivalLocation);
        departureLocation = findViewById(R.id.departureLocation);
        ticketPrice = findViewById(R.id.ticketPrice);
        selectDeparture = findViewById(R.id.selectDeparture);
        selectArrival = findViewById(R.id.selectArrival);
        addScheduleBtn = findViewById(R.id.addScheduleBtn);
        busSpinner = findViewById(R.id.busSpinner);
        buses = new ArrayList<BusModel>();

        ArrayList<String> bus_nums = new ArrayList<String>();

        FirebaseDatabase.getInstance().getReference("Buses").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds: snapshot.getChildren()){

                    buses.add(ds.getValue(BusModel.class));
                    bus_nums.add(ds.getValue(BusModel.class).getBusNumber());
                }


                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, bus_nums);
                busSpinner.setAdapter(adapter);
                busSpinner.setOnItemSelectedListener(AddSchedulesScreen.this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        selectDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDateTimePicker departureTimeDialog = new CustomDateTimePicker(AddSchedulesScreen.this, new CustomDateTimePicker.ICustomDateTimeListener() {

                            @Override
                            public void onSet(Dialog dialog, Calendar calendarSelected,
                                              Date dateSelected, int year, String monthFullName,
                                              String monthShortName, int monthNumber, int day,
                                              String weekDayFullName, String weekDayShortName,
                                              int hour24, int hour12, int min, int sec,
                                              String AM_PM) {

                                d_ldt = LocalDateTime.ofInstant(dateSelected.toInstant(), ZoneId.systemDefault());
                                String d = DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a").format(d_ldt);
                                departureTime.setText(d);
                            }

                            @Override
                            public void onCancel() {

                            }
                        });

                departureTimeDialog.set24HourFormat(false);
                departureTimeDialog.setDate(Calendar.getInstance());
                departureTimeDialog.showDialog();
            }
        });

        selectArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDateTimePicker arrivalTimeDialog = new CustomDateTimePicker(AddSchedulesScreen.this, new CustomDateTimePicker.ICustomDateTimeListener() {

                    @Override
                    public void onSet(Dialog dialog, Calendar calendarSelected,
                                      Date dateSelected, int year, String monthFullName,
                                      String monthShortName, int monthNumber, int day,
                                      String weekDayFullName, String weekDayShortName,
                                      int hour24, int hour12, int min, int sec,
                                      String AM_PM) {

                        a_ldt = LocalDateTime.ofInstant(dateSelected.toInstant(), ZoneId.systemDefault());
                        String a = DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a").format(a_ldt);
                        arrivalTime.setText(a);
                    }

                    @Override
                    public void onCancel() {

                    }
                });

                arrivalTimeDialog.set24HourFormat(false);
                arrivalTimeDialog.setDate(Calendar.getInstance());
                arrivalTimeDialog.showDialog();
            }
        });

        addScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String departure = departureLocation.getText().toString();
                String arrival = arrivalLocation.getText().toString();
                String price = ticketPrice.getText().toString();
                String atime = arrivalTime.getText().toString();
                String dtime = departureTime.getText().toString();

                if(departure.isEmpty() || arrival.isEmpty() || price.isEmpty()){
                    Toast.makeText(getApplicationContext(), "One or More Field is Empty!!", Toast.LENGTH_SHORT).show();
                }

                else if(atime.equals("Select Arrival Time")){
                    Toast.makeText(getApplicationContext(), "Select Arrival Time!!", Toast.LENGTH_SHORT).show();
                }

                else if(dtime.equals("Select Departure Time")){
                    Toast.makeText(getApplicationContext(), "Select Departure Time!!", Toast.LENGTH_SHORT).show();
                }

                else if(a_ldt.isBefore(d_ldt)){
                    Toast.makeText(getApplicationContext(), "Invalid Arrival Time!!!", Toast.LENGTH_SHORT).show();
                }
                else if(d_ldt.isBefore(LocalDateTime.now())){
                    Toast.makeText(getApplicationContext(), "Choose Future Time!!!", Toast.LENGTH_SHORT).show();
                }
                else if(current_bus.getBusNumber().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Select Bus For Schedule!!", Toast.LENGTH_SHORT).show();
                }

                else{

                    progressBar.setVisibility(View.VISIBLE);
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Schedules").push();

                    ScheduleModel sched = new ScheduleModel(db.getKey(),departure,arrival,dtime,atime,Double.parseDouble(price),current_bus.getBusNumber());
                    db.setValue(sched).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){


                                ScheduledBusModel schedBus = new ScheduledBusModel();
                                schedBus.setSchedule_id(db.getKey());
                                schedBus.setBus_number(current_bus.getBusNumber());
                                schedBus.setRemaining_seats(current_bus.getNumSeats());
                                schedBus.setTotal_seats(current_bus.getNumSeats());
                                ArrayList<SeatModel> seats = new ArrayList<SeatModel>();

                                for(int i=0; i<schedBus.getRemaining_seats(); i++){

                                    seats.add(new SeatModel(i+1,false));

                                }

                                schedBus.setSeats(seats);
                                FirebaseDatabase.getInstance().getReference("ScheduledBuses").push().setValue(schedBus).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){
                                            progressBar.setVisibility(View.GONE);

                                            Toast.makeText(AddSchedulesScreen.this, "Schedule Added Successfully!!", Toast.LENGTH_SHORT).show();

                                            Intent activityIntent=new Intent(AddSchedulesScreen.this, MainActivity.class);

                                            PendingIntent contentIntent=PendingIntent.getActivity(AddSchedulesScreen.this,
                                                    0, activityIntent, PendingIntent.FLAG_MUTABLE);

                                            NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                                            Notification notification;
                                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


                                                notification = new NotificationCompat.Builder(AddSchedulesScreen.this, CHANNEL_3_ID)
                                                        .setSmallIcon(R.drawable.ic_stat_onesignal_default)
                                                        .setContentTitle("Schedule Posted Successful")
                                                        .setContentText("The schedule for bus " + sched.getBus_number() + " from " + sched.getDeparture() + " to "+sched.getArrival()+" was posted successfully!!")
                                                        .setStyle(new NotificationCompat.BigTextStyle()
                                                                .bigText("The schedule for bus " + sched.getBus_number() + " from " + sched.getDeparture() + " to "+sched.getArrival()+" was posted successfully!!"))
                                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                                        .setColor(Color.GREEN)
                                                        .setContentIntent(contentIntent) // Set the intent that will fire when the user taps the notification
                                                        .setAutoCancel(true)
                                                        .setOnlyAlertOnce(true)
                                                        .build();
                                                notificationManager.notify(3, notification);



                                            }

                                            onBackPressed();
                                        }
                                        else{
                                            progressBar.setVisibility(View.GONE);

                                            Toast.makeText(AddSchedulesScreen.this, "Schedule Could Not be Added!!", Toast.LENGTH_SHORT).show();
                                            departureTime.setText("Select Departure Time");
                                            arrivalTime.setText("Select Arrival Time");
                                            departureLocation.setText("");
                                            arrivalLocation.setText("");
                                            ticketPrice.setText("");
                                        }

                                    }
                                });




                            }

                            else{
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(AddSchedulesScreen.this, "Schedule Could Not be Added!!", Toast.LENGTH_SHORT).show();
                                departureTime.setText("Select Departure Time");
                                arrivalTime.setText("Select Arrival Time");
                                departureLocation.setText("");
                                arrivalLocation.setText("");
                                ticketPrice.setText("");
                            }

                        }
                    });

                }

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        current_bus = buses.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}