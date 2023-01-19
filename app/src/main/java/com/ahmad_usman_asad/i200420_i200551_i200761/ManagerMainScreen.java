package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ManagerMainScreen extends AppCompatActivity {

    ImageView logout;
    CardView manageBusesBtn;
    CardView manageSchedulesBtn;
    CardView makeAnnouncementsBtn;
    CardView viewComplaintsBtn;
    ImageView qrScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_main_screen);

        logout = findViewById(R.id.logout);
        manageBusesBtn = findViewById(R.id.manageBusesBtn);
        manageSchedulesBtn = findViewById(R.id.manageSchedulesBtn);
        makeAnnouncementsBtn = findViewById(R.id.makeAnnoucementsBtn);
        viewComplaintsBtn = findViewById(R.id.viewComplaintsBtn);
        qrScanner = findViewById(R.id.qrScanner);

        qrScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

                    startActivityForResult(intent, 0);

                } catch (Exception e) {

                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                    startActivity(marketIntent);

                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ManagerMainScreen.this, ManagerLoginScreen.class);
                startActivity(intent);
            }
        });

        manageBusesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerMainScreen.this, ManageBusesScreen.class);
                startActivity(intent);
            }
        });

        manageSchedulesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerMainScreen.this, ManageSchedulesScreen.class);
                startActivity(intent);
            }
        });

        makeAnnouncementsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerMainScreen.this, ManageAnnouncementsScreen.class);
                intent.putExtra("Role","Manager");
                startActivity(intent);
            }
        });

        viewComplaintsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerMainScreen.this, ManagerManageComplaintsScreen.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");

                String[] words=contents.split("/");

                Intent intent = new Intent(ManagerMainScreen.this, ShowReservationScreen.class);
                intent.putExtra("RES_ID",words[0]);
                intent.putExtra("SEAT_ID",words[1]);
                startActivity(intent);



            }
            if(resultCode == RESULT_CANCELED){



            }
        }
    }
}