package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class GuestScreen extends AppCompatActivity {

    ImageView backBtn;
    CardView schedulesBtn;
    CardView announcementsBtn;
    CardView createAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_screen);

        backBtn = findViewById(R.id.backBtn);
        announcementsBtn = findViewById(R.id.announcementsBtn);
        createAccountBtn = findViewById(R.id.createAccBtn);
        schedulesBtn = findViewById(R.id.schedulesBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        schedulesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GuestScreen.this, SelectSchedulesScreen.class);
                startActivity(intent);

            }
        });

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GuestScreen.this, UserSignupScreen.class);
                startActivity(intent);

            }
        });

        announcementsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(GuestScreen.this, ManageAnnouncementsScreen.class);
                intent.putExtra("Role","User");
                startActivity(intent);

            }
        });




    }
}