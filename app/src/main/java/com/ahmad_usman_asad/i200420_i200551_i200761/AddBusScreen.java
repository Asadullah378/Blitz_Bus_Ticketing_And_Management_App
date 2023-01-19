package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ahmad_usman_asad.i200420_i200551_i200761.Models.BusModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddBusScreen extends AppCompatActivity {

    ImageView backBtn;
    EditText busNum;
    EditText busSeats;
    MaterialCardView hinoModel;
    MaterialCardView volvoModel;
    MaterialCardView daewooModel;
    Button addBusBtn;
    String model;
    ArrayList<BusModel> all_buses;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bus_screen);

        all_buses = this.getIntent().getExtras().getParcelableArrayList("Buses");

        progressBar = findViewById(R.id.ProgressBar);
        model="";
        backBtn = findViewById(R.id.backBtn);
        busNum = findViewById(R.id.busNum);
        busSeats = findViewById(R.id.busSeats);
        hinoModel = findViewById(R.id.hinoModel);
        volvoModel = findViewById(R.id.volvoModel);
        daewooModel = findViewById(R.id.daewooModel);
        addBusBtn = findViewById(R.id.addBusBtn);

        hinoModel.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        volvoModel.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        daewooModel.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

        hinoModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                model = "Hino";
                hinoModel.setCardBackgroundColor(Color.parseColor("#02FA58"));
                volvoModel.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                daewooModel.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });

        volvoModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model = "Volvo";
                hinoModel.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                volvoModel.setCardBackgroundColor(Color.parseColor("#02FA58"));
                daewooModel.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            }
        });

        daewooModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model = "Daewoo";
                hinoModel.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                volvoModel.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                daewooModel.setCardBackgroundColor(Color.parseColor("#02FA58"));
            }
        });

        addBusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String bus_num = busNum.getText().toString();
                String bus_seats = busSeats.getText().toString();

                if(model.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Select Model First!!", Toast.LENGTH_SHORT).show();
                }

                else if(bus_num.isEmpty() || bus_seats.isEmpty()){

                    Toast.makeText(getApplicationContext(), "One or More Field is Empty!!", Toast.LENGTH_SHORT).show();

                }

                else if(Integer.parseInt(bus_seats)<20 || Integer.parseInt(bus_seats)>40){

                    Toast.makeText(getApplicationContext(), "Number of Seats must be Between 20 and 40!!", Toast.LENGTH_SHORT).show();
                }

                else if(Integer.parseInt(bus_seats)%4!=0){
                    Toast.makeText(getApplicationContext(), "Number of Seats must be Divisible by 4!!", Toast.LENGTH_SHORT).show();
                }

                else{

                    boolean check=true;
                    for(int i=0; i<all_buses.size(); i++){

                        if(all_buses.get(i).getBusNumber().equals(bus_num)){
                            Toast.makeText(getApplicationContext(), "Bus Number Already Exists", Toast.LENGTH_SHORT).show();
                            check=false;
                            break;
                        }

                    }

                    if(check) {
                        progressBar.setVisibility(View.VISIBLE);
                        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Buses").child(bus_num);
                        db.setValue(new BusModel(bus_num, Integer.parseInt(bus_seats), model)).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);

                                    Toast.makeText(getApplicationContext(), "Bus Added Successfully!!", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                } else {
                                    progressBar.setVisibility(View.GONE);

                                    Toast.makeText(getApplicationContext(), "Bus Could Not be Added!!", Toast.LENGTH_SHORT).show();
                                    model = "";
                                    hinoModel.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                                    volvoModel.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                                    daewooModel.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                                    busNum.setText("");
                                    busSeats.setText("");
                                }


                            }
                        });
                    }

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
}