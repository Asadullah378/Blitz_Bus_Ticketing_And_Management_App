package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ChooseRoleScreen extends AppCompatActivity {

    LinearLayout userLogin;
    LinearLayout guest;
    LinearLayout managerLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_role_screen);

        userLogin = findViewById(R.id.userScreen);
        guest = findViewById(R.id.guest);
        managerLogin = findViewById(R.id.managerScreen);

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseRoleScreen.this, UserLoginScreen.class);
                startActivity(intent);
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseRoleScreen.this, GuestScreen.class);
                startActivity(intent);
            }
        });

        managerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseRoleScreen.this, ManagerLoginScreen.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {

    }
}