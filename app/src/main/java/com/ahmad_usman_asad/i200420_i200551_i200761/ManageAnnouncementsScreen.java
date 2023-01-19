package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ahmad_usman_asad.i200420_i200551_i200761.Adapters.AnnouncementAdapter;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.AnnouncementModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ManageAnnouncementsScreen extends AppCompatActivity {

    ImageView backBtn;
    RecyclerView announcementsRV;
    AnnouncementAdapter announcementAdapter;
    ArrayList<AnnouncementModel> announcements;
    FloatingActionButton addAnnouncementsScreen;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_announcements_screen);

        backBtn = findViewById(R.id.backBtn);
        announcementsRV = findViewById(R.id.announcementsRV);
        addAnnouncementsScreen = findViewById(R.id.addAnnouncementScreen);

        role = getIntent().getStringExtra("Role");

        announcements = new ArrayList<AnnouncementModel>();
        announcementsRV.setLayoutManager(new LinearLayoutManager(this));

        if(role.equals("User"))
            addAnnouncementsScreen.setVisibility(View.GONE);

        announcementAdapter = new AnnouncementAdapter(announcements,this,role);
        announcementsRV.setAdapter(announcementAdapter);

        FirebaseDatabase.getInstance().getReference("Announcements").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                announcements.add(snapshot.getValue(AnnouncementModel.class));
                announcementAdapter.notifyItemInserted(announcements.size());
                announcementsRV.smoothScrollToPosition(announcements.size());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                for(int i=0; i<announcements.size(); i++) {

                    if (announcements.get(i).getId().equals(snapshot.getValue(AnnouncementModel.class).getId())) {
                        announcements.remove(i);
                        announcementAdapter.notifyItemRemoved(i);
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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        addAnnouncementsScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageAnnouncementsScreen.this, MakeAnnouncementScreen.class);
                startActivity(intent);
            }
        });

    }
}