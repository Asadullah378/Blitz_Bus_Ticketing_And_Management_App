package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ahmad_usman_asad.i200420_i200551_i200761.Models.AnnouncementModel;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MakeAnnouncementScreen extends AppCompatActivity {

    ImageView backBtn;
    EditText announcementTitle;
    EditText annoucementDetails;
    Button postAnnouncementBtn;
    ProgressBar progressBar;
    ArrayList<String> recieverPlayerIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_announcement_screen);

        backBtn = findViewById(R.id.backBtn);
        announcementTitle = findViewById(R.id.announcementTitle);
        annoucementDetails = findViewById(R.id.announcementDetails);
        postAnnouncementBtn = findViewById(R.id.postAnnouncementBtn);
        progressBar = findViewById(R.id.ProgressBar);


        recieverPlayerIDs = new ArrayList<String>();
        FirebaseDatabase.getInstance().getReference("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds: snapshot.getChildren()) {

                    FirebaseDatabase.getInstance().getReference("Users").child(ds.getKey()).child("playerIDs").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot ds: snapshot.getChildren())
                                recieverPlayerIDs.add(ds.getValue(String.class));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        postAnnouncementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = announcementTitle.getText().toString();
                String details  = annoucementDetails.getText().toString();

                if(title.isEmpty())
                    Toast.makeText(getApplicationContext(), "Title Cannot be Empty!!!", Toast.LENGTH_SHORT).show();

                else if(details.isEmpty())
                    Toast.makeText(getApplicationContext(), "Announcement Body Cannot be Empty!!!", Toast.LENGTH_SHORT).show();

                else{

                    progressBar.setVisibility(View.VISIBLE);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Announcements").push();
                    AnnouncementModel announcement = new AnnouncementModel(ref.getKey(),title,details, DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a").format(LocalDateTime.now()));

                    ref.setValue(announcement).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                progressBar.setVisibility(View.GONE);

                                JSONObject json = new JSONObject();
                                JSONObject j1 = new JSONObject();
                                JSONObject j2 = new JSONObject();

                                try {
                                    j1.put("en",details);
                                    j2.put("en","New Announcement: "+title);
                                    json.put("contents",j1);
                                    json.put("headings",j2);
                                    json.put("include_player_ids", new JSONArray(recieverPlayerIDs.toArray()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                OneSignal.postNotification(json, new OneSignal.PostNotificationResponseHandler() {
                                    @Override
                                    public void onSuccess(JSONObject jsonObject) {
                                        System.out.println("Notification Sent!!");
                                    }

                                    @Override
                                    public void onFailure(JSONObject jsonObject) {
                                        System.out.println("Notification Not Sent!!"+jsonObject.toString());
                                    }
                                });

                                Toast.makeText(getApplicationContext(), "Announcement Successfully Posted!!", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }

                            else{
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), "Announcement Could Not be Posted!!", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

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