package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmad_usman_asad.i200420_i200551_i200761.Models.TransactionModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import java.util.ArrayList;

public class UserSignupScreen extends AppCompatActivity {

    TextView signinPage;
    CardView female_gender;
    CardView male_gender;
    CardView other_gender;
    EditText user_name;
    EditText user_email;
    EditText user_password;
    Button signup;
    String gender;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);

        signinPage = findViewById(R.id.signinPage);
        female_gender = findViewById(R.id.female_gender);
        male_gender = findViewById(R.id.male_gender);
        other_gender = findViewById(R.id.other_gender);
        user_email = findViewById(R.id.user_email);
        user_name = findViewById(R.id.user_name);
        user_password = findViewById(R.id.user_password);
        signup = findViewById(R.id.signup);
        progressBar = findViewById(R.id.ProgressBar);


        gender="";
        progressBar.setVisibility(View.GONE);
        female_gender.setCardBackgroundColor(Color.parseColor("#000000"));
        male_gender.setCardBackgroundColor(Color.parseColor("#000000"));
        other_gender.setCardBackgroundColor(Color.parseColor("#000000"));

        signinPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserSignupScreen.this, UserLoginScreen.class);
                startActivity(intent);
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

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = user_name.getText().toString();
                String email = user_email.getText().toString();
                String password = user_password.getText().toString();

                if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "One or More Field is Empty", Toast.LENGTH_SHORT).show();
                }

                else if(gender.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Select a Gender First!!!", Toast.LENGTH_SHORT).show();
                }

                else{

                    progressBar.setVisibility(View.VISIBLE);
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if(task.isSuccessful()){

                                DatabaseReference db =  FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid());

                                ArrayList<String> playerIDs = new ArrayList<String>();
                                playerIDs.add(OneSignal.getDeviceState().getUserId());
                                db.setValue(new UserModel(name,email,gender,0,new ArrayList<TransactionModel>(),playerIDs)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){

                                            Intent intent = new Intent(UserSignupScreen.this, UserMainScreen.class);
                                            startActivity(intent);
                                            progressBar.setVisibility(View.GONE);

                                        }

                                        else{
                                            user_name.setText("");
                                            user_email.setText("");
                                            user_password.setText("");
                                            female_gender.setCardBackgroundColor(Color.parseColor("#000000"));
                                            male_gender.setCardBackgroundColor(Color.parseColor("#000000"));
                                            other_gender.setCardBackgroundColor(Color.parseColor("#000000"));
                                            gender="";
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(), "Signup Failed", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                            }

                            else{
                                user_name.setText("");
                                user_email.setText("");
                                user_password.setText("");
                                female_gender.setCardBackgroundColor(Color.parseColor("#000000"));
                                male_gender.setCardBackgroundColor(Color.parseColor("#000000"));
                                other_gender.setCardBackgroundColor(Color.parseColor("#000000"));
                                gender="";
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Signup Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }
        });
    }
}