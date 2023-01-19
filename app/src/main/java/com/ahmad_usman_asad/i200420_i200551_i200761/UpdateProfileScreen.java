package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ahmad_usman_asad.i200420_i200551_i200761.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfileScreen extends AppCompatActivity {

    ImageView backBtn;
    ProgressBar progressBar;
    EditText user_name;
    EditText user_email;
    Button update_profile;
    UserModel user;
    String gender;
    CardView female_gender;
    CardView male_gender;
    CardView other_gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile_screen);

        female_gender = findViewById(R.id.female_gender);
        male_gender = findViewById(R.id.male_gender);
        other_gender = findViewById(R.id.other_gender);
        backBtn = findViewById(R.id.backBtn);
        progressBar = findViewById(R.id.ProgressBar);
        user_name = findViewById(R.id.user_name);
        user_email = findViewById(R.id.user_email);
        update_profile = findViewById(R.id.updateProfile);


        progressBar.setVisibility(View.VISIBLE);

        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                user = snapshot.getValue(UserModel.class);


                gender = user.getGender();

                user_name.setText(user.getName());
                user_email.setText(user.getEmail());
                user_email.setFocusable(false);
                if(gender.equals("Female")){
                    female_gender.setCardBackgroundColor(Color.parseColor("#02FA58"));
                    male_gender.setCardBackgroundColor(Color.parseColor("#000000"));
                    other_gender.setCardBackgroundColor(Color.parseColor("#000000"));
                }
                else if(gender.equals("Male")){
                    female_gender.setCardBackgroundColor(Color.parseColor("#000000"));
                    male_gender.setCardBackgroundColor(Color.parseColor("#02FA58"));
                    other_gender.setCardBackgroundColor(Color.parseColor("#000000"));
                }

                else if(gender.equals("Other")){
                    female_gender.setCardBackgroundColor(Color.parseColor("#000000"));
                    male_gender.setCardBackgroundColor(Color.parseColor("#000000"));
                    other_gender.setCardBackgroundColor(Color.parseColor("#02FA58"));
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        female_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                female_gender.setCardBackgroundColor(Color.parseColor("#02FA58"));
                male_gender.setCardBackgroundColor(Color.parseColor("#000000"));
                other_gender.setCardBackgroundColor(Color.parseColor("#000000"));
                gender = "Female";
            }
        });

        male_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                female_gender.setCardBackgroundColor(Color.parseColor("#000000"));
                male_gender.setCardBackgroundColor(Color.parseColor("#02FA58"));
                other_gender.setCardBackgroundColor(Color.parseColor("#000000"));
                gender = "Male";
            }
        });

        other_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                female_gender.setCardBackgroundColor(Color.parseColor("#000000"));
                male_gender.setCardBackgroundColor(Color.parseColor("#000000"));
                other_gender.setCardBackgroundColor(Color.parseColor("#02FA58"));
                gender = "Other";
            }
        });

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String n = user_name.getText().toString();

                if(!(user.getName().equals(n) && user.getGender().equals(gender))){

                    if(n.isEmpty())
                        Toast.makeText(getApplicationContext(), "Name Cannot Be Empty!!!", Toast.LENGTH_SHORT).show();

                    else if(gender.isEmpty())
                        Toast.makeText(getApplicationContext(), "Choose a Gender!!!", Toast.LENGTH_SHORT).show();

                    else{

                        user.setName(n);
                        user.setGender(gender);
                        progressBar.setVisibility(View.VISIBLE);

                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){

                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Profile Updated!!", Toast.LENGTH_SHORT).show();
                                }

                                else{

                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Profile Could not be Updated!!", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


                    }
                }

                else{
                    Toast.makeText(getApplicationContext(), "No Update in Profile!!!", Toast.LENGTH_SHORT).show();

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