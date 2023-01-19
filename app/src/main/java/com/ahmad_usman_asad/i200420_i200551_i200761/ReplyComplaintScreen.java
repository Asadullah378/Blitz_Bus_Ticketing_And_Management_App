package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ComplaintModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReplyComplaintScreen extends AppCompatActivity {

    ImageView backBtn;
    TextView complaintUser;
    TextView complaint;
    EditText reply;
    String complaint_id;
    ComplaintModel current_complaint;
    String username;
    DatabaseReference replyRef;
    Button submitReplyBtn;
    ProgressBar progressBar;
    ArrayList<String> recieverPlayerIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reply_complaint_screen);

        recieverPlayerIDs = new ArrayList<String>();
        progressBar = findViewById(R.id.ProgressBar);
        backBtn = findViewById(R.id.backBtn);
        complaintUser = findViewById(R.id.complaintUser);
        complaint = findViewById(R.id.complaint);
        reply = findViewById(R.id.reply);
        submitReplyBtn = findViewById(R.id.replyComplaintBtn);
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        complaint_id = intent.getStringExtra(ManageComplaintsScreen.COMPLAINT_ID);
        username = intent.getStringExtra(ManagerManageComplaintsScreen.USERNAME);



        FirebaseDatabase.getInstance().getReference("Complaints").child(complaint_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                current_complaint = snapshot.getValue(ComplaintModel.class);

                complaintUser.setText(username);
                complaint.setText(current_complaint.getComplaint());

                replyRef = snapshot.getRef();


                FirebaseDatabase.getInstance().getReference("Users").child(current_complaint.getUser_id()).child("playerIDs").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot ds: snapshot.getChildren())
                            recieverPlayerIDs.add(ds.getValue(String.class));

                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

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


        submitReplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String rep = reply.getText().toString();

                if(rep.isEmpty())
                    Toast.makeText(getApplicationContext(), "Enter Reply First!!", Toast.LENGTH_SHORT).show();

                else{
                    progressBar.setVisibility(View.VISIBLE);

                    current_complaint.setReplyGiven(true);
                    current_complaint.setReply(rep);

                    replyRef.setValue(current_complaint).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                progressBar.setVisibility(View.GONE);

                                JSONObject json = new JSONObject();
                                JSONObject j1 = new JSONObject();
                                JSONObject j2 = new JSONObject();



                                try {
                                    j1.put("en","Reply: "+rep);
                                    j2.put("en","Your Complaint ("+current_complaint.getComplaint_type()+") has a Reply");
                                    json.put("contents",j1);
                                    json.put("headings",j2);
                                    json.put("include_player_ids", new JSONArray(recieverPlayerIDs.toArray()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                OneSignal.postNotification(json, new OneSignal.PostNotificationResponseHandler() {
                                    @Override
                                    public void onSuccess(JSONObject jsonObject) {
                                        System.out.println( "Notification Sent!!");
                                    }

                                    @Override
                                    public void onFailure(JSONObject jsonObject) {
                                        System.out.println( "Notification Not Sent!!"+jsonObject.toString());
                                    }
                                });

                                onBackPressed();
                            }

                            else{
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), "Reply Could Not be Sent!!!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });

    }
}