package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ComplaintModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewComplaintScreen extends AppCompatActivity {

    ImageView backBtn;
    LinearLayout noReplyLayout;
    CardView replyLayout;
    TextView complaintType;
    TextView complaint;
    TextView reply;
    String complaint_id;
    ComplaintModel current_complaint;
    boolean seenConfig;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_complaint_screen);

        backBtn = findViewById(R.id.backBtn);
        noReplyLayout = findViewById(R.id.noReplyLayout);
        replyLayout = findViewById(R.id.replyLayout);
        complaintType = findViewById(R.id.complaintType);
        complaint = findViewById(R.id.complaint);
        reply = findViewById(R.id.reply);

        progressBar = findViewById(R.id.ProgressBar);

        progressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        complaint_id = intent.getStringExtra(ManageComplaintsScreen.COMPLAINT_ID);
        seenConfig = intent.getBooleanExtra("SEEN",false);
        FirebaseDatabase.getInstance().getReference("Complaints").child(complaint_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                current_complaint = snapshot.getValue(ComplaintModel.class);

                complaintType.setText(current_complaint.getComplaint_type());
                complaint.setText(current_complaint.getComplaint());

                if(current_complaint.getReplyGiven()){

                    noReplyLayout.setVisibility(View.GONE);
                    replyLayout.setVisibility(View.VISIBLE);
                    reply.setText(current_complaint.getReply());

                    if(!current_complaint.getReplySeen() && seenConfig){
                        snapshot.getRef().child("replySeen").setValue(true);
                    }

                }
                else{
                    noReplyLayout.setVisibility(View.VISIBLE);
                    replyLayout.setVisibility(View.INVISIBLE);
                }

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

    }
}