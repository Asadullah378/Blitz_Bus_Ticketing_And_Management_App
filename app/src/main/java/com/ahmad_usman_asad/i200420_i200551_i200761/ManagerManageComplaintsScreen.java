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

import com.ahmad_usman_asad.i200420_i200551_i200761.Adapters.ManagerComplaintAdapter;
import com.ahmad_usman_asad.i200420_i200551_i200761.Adapters.UserComplaintAdapter;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ComplaintModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ManagerManageComplaintsScreen extends AppCompatActivity {

    ImageView backBtn;
    RecyclerView complaintsRV;
    ManagerComplaintAdapter managerComplaintAdapter;
    ArrayList<ComplaintModel> complaints;

    public static final String USERNAME = "com.ahmad_usman_asad.i200420_i200551_i200761.USERNAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_manage_complaints_screen);

        backBtn = findViewById(R.id.backBtn);
        complaintsRV = findViewById(R.id.complaintsRV);

        complaints = new ArrayList<ComplaintModel>();
        complaintsRV.setLayoutManager(new LinearLayoutManager(this));
        managerComplaintAdapter = new ManagerComplaintAdapter(complaints, this);
        complaintsRV.setAdapter(managerComplaintAdapter);

        FirebaseDatabase.getInstance().getReference("Complaints").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                complaints.add(snapshot.getValue(ComplaintModel.class));
                managerComplaintAdapter.notifyItemInserted(complaints.size() - 1);
                complaintsRV.smoothScrollToPosition(complaints.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


                for(int i=0; i<complaints.size(); i++) {

                    if (snapshot.getValue(ComplaintModel.class).getComplaint_id().equals(complaints.get(i).getComplaint_id())){
                        complaints.set(i, snapshot.getValue(ComplaintModel.class));
                        managerComplaintAdapter.notifyItemChanged(i);
                    }
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

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


    }
}