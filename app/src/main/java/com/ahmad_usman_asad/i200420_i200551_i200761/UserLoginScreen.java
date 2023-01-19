package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmad_usman_asad.i200420_i200551_i200761.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

public class UserLoginScreen extends AppCompatActivity {

    TextView signupPage;
    EditText signin_email;
    EditText signin_password;
    Button signin;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        signupPage = findViewById(R.id.signupPage);
        signin_email = findViewById(R.id.signin_email);
        signin_password = findViewById(R.id.signin_password);
        signin = findViewById(R.id.signin);
        progressBar = findViewById(R.id.ProgressBar);
        progressBar.setVisibility(View.GONE);

        signupPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLoginScreen.this, UserSignupScreen.class);
                startActivity(intent);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = signin_email.getText().toString();
                String password = signin_password.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "One or More Fields Are Empty", Toast.LENGTH_SHORT).show();
                }

                else{
                    progressBar.setVisibility(View.VISIBLE);
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                String player_id = OneSignal.getDeviceState().getUserId();
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        UserModel user = snapshot.getValue(UserModel.class);

                                        boolean check=true;
                                        for(int i=0; i<user.getPlayerIDs().size(); i++){

                                            if(user.getPlayerIDs().get(i).equals(player_id))
                                                check=false;

                                        }

                                        if(check){

                                            user.getPlayerIDs().add(player_id);
                                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).setValue(user);

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                Intent intent = new Intent(UserLoginScreen.this, UserMainScreen.class);
                                startActivity(intent);
                                progressBar.setVisibility(View.GONE);
                            }

                            else{
                                signin_email.setText("");
                                signin_password.setText("");
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Login Failed!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(UserLoginScreen.this, ChooseRoleScreen.class);
        startActivity(intent);
    }
}