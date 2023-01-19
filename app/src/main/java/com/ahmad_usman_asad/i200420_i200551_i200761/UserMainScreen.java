package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ahmad_usman_asad.i200420_i200551_i200761.Models.UserModel;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

public class UserMainScreen extends AppCompatActivity {

    ImageView logout;
    TextView username;
    TextView balance;
    UserModel current_user;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView drawer_btn;
    CardView addCredit;
    CardView buyTickets;
    CardView myTickets;
    CardView refundTickets;
    CardView shipping;
    CardView complaint;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_user_main_screen);


        logout = findViewById(R.id.logout);
        username = findViewById(R.id.userName);
        balance = findViewById(R.id.balance);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.drawerNavigationView);
        drawer_btn = findViewById(R.id.drawerBtn);
        addCredit = findViewById(R.id.addCredit);
        buyTickets = findViewById(R.id.bookTickets);
        myTickets = findViewById(R.id.myTickets);
        refundTickets = findViewById(R.id.refundTickets);
        shipping = findViewById(R.id.shipping);
        complaint = findViewById(R.id.complaint);
        progressBar = findViewById(R.id.ProgressBar);
        progressBar.setVisibility(View.VISIBLE);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent intent;
                switch (item.getItemId()){

                    case R.id.editProfile:
                        intent = new Intent(UserMainScreen.this, UpdateProfileScreen.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.announcements:
                        intent = new Intent(UserMainScreen.this, ManageAnnouncementsScreen.class);
                        intent.putExtra("Role","User");
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.transactionsHistory:
                        intent = new Intent(UserMainScreen.this, ViewTransactionsScreen.class);
                        startActivity(intent);
                        return true;

                    case R.id.travelHistory:
                        intent = new Intent(UserMainScreen.this, ViewTravelHistoryScreen.class);
                        startActivity(intent);
                        return true;

                }

                return false;
            }
        });

        addCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMainScreen.this, AddCreditScreen.class);
                startActivity(intent);
            }
        });

        buyTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMainScreen.this, SelectSchedulesScreen.class);
                startActivity(intent);
            }
        });

        myTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMainScreen.this, MyTicketsScreen.class);
                startActivity(intent);
            }
        });

        refundTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMainScreen.this, SelectTicketScreen.class);
                startActivity(intent);
            }
        });

        shipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMainScreen.this, ManageShippingScreen.class);
                startActivity(intent);
            }
        });

        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMainScreen.this, ManageComplaintsScreen.class);
                startActivity(intent);

            }
        });

        drawer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String player_id = OneSignal.getDeviceState().getUserId();

                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).child("playerIDs").orderByValue().equalTo(player_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot ds: snapshot.getChildren())
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).child("playerIDs").child(ds.getKey()).removeValue();

                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(UserMainScreen.this, UserLoginScreen.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });


        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressBar.setVisibility(View.VISIBLE);
                current_user = snapshot.getValue(UserModel.class);
                username.setText(current_user.getName());
                balance.setText("PKR "+String.valueOf(current_user.getBalance()));
                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    @Override
    public void onBackPressed() {

    }
}