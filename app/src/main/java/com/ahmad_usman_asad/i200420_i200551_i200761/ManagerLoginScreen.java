package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagerLoginScreen extends AppCompatActivity {

    EditText signin_email;
    EditText signin_password;
    Button signin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_login_screen);

        signin_email = findViewById(R.id.manager_signin_email);
        signin_password = findViewById(R.id.manager_signin_password);
        signin = findViewById(R.id.manager_signin);
        progressBar = findViewById(R.id.ProgressBar);


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
                    FirebaseDatabase.getInstance().getReference("Manager").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String t_email = snapshot.child("email").getValue(String.class);
                            String t_password = snapshot.child("password").getValue(String.class);

                            if(t_email.equals(email) && t_password.equals(password)){
                                Intent intent =  new Intent(ManagerLoginScreen.this, ManagerMainScreen.class);
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

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            signin_email.setText("");
                            signin_password.setText("");
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Login Failed!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(ManagerLoginScreen.this, ChooseRoleScreen.class);
        startActivity(intent);
    }
}