package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.FloatEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ahmad_usman_asad.i200420_i200551_i200761.Adapters.UserComplaintAdapter;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ComplaintModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ManageComplaintsScreen extends AppCompatActivity {

    ImageView backBtn;
    RecyclerView complaintsRV;
    UserComplaintAdapter userComplaintAdapter;
    FloatingActionButton addComplaintsScreen;
    ArrayList<ComplaintModel> complaints;
    public static final String COMPLAINT_ID = "com.ahmad_usman_asad.i200420_i200551_i200761.COMPLAINT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_complaints_screen);

        backBtn = findViewById(R.id.backBtn);
        complaintsRV = findViewById(R.id.complaintsRV);
        addComplaintsScreen = findViewById(R.id.addComplaintScreen);

        complaints = new ArrayList<ComplaintModel>();
        complaintsRV.setLayoutManager(new LinearLayoutManager(this));
        userComplaintAdapter = new UserComplaintAdapter(complaints,this);
        complaintsRV.setAdapter(userComplaintAdapter);

        FirebaseDatabase.getInstance().getReference("Complaints").orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                complaints.add(snapshot.getValue(ComplaintModel.class));
                userComplaintAdapter.notifyItemInserted(complaints.size()-1);
                complaintsRV.smoothScrollToPosition(complaints.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                for(int i=0; i<complaints.size(); i++){

                    if(complaints.get(i).getComplaint_id().equals(snapshot.getValue(ComplaintModel.class).getComplaint_id())){
                        complaints.set(i,snapshot.getValue(ComplaintModel.class));
                        userComplaintAdapter.notifyItemChanged(i);
                        break;
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

        addComplaintsScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ManageComplaintsScreen.this, AddComplaintScreen.class);
                startActivity(intent);

            }
        });



    }
}