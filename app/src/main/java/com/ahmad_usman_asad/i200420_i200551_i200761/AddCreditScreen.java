package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ahmad_usman_asad.i200420_i200551_i200761.Models.TransactionModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddCreditScreen extends AppCompatActivity {

    ImageView backBtn;
    CardView debitMethod;
    CardView jazzcashMethod;
    CardView easypaiseMethod;
    EditText amount;
    Button addBalanceBtn;
    UserModel user;
    String method;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_credit_screen);

        backBtn = findViewById(R.id.backBtn);
        debitMethod = findViewById(R.id.debitMethod);
        jazzcashMethod = findViewById(R.id.jazzcashMethod);
        easypaiseMethod = findViewById(R.id.easypaisaMethod);
        amount = findViewById(R.id.amount);
        addBalanceBtn = findViewById(R.id.addCreditBtn);

        progressBar = findViewById(R.id.ProgressBar);

        method="";
        jazzcashMethod.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        easypaiseMethod.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        debitMethod.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                user = snapshot.getValue(UserModel.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        debitMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jazzcashMethod.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                easypaiseMethod.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                debitMethod.setCardBackgroundColor(Color.parseColor("#02FA58"));
                method = "Debit Card";
            }
        });

        easypaiseMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jazzcashMethod.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                easypaiseMethod.setCardBackgroundColor(Color.parseColor("#02FA58"));
                debitMethod.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                method = "Easypaisa";
            }
        });

        jazzcashMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jazzcashMethod.setCardBackgroundColor(Color.parseColor("#02FA58"));
                easypaiseMethod.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                debitMethod.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                method = "JazzCash";
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        addBalanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String money = amount.getText().toString();

                if(money.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter Amount First!!", Toast.LENGTH_SHORT).show();
                }

                else if(method.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Select Payment Method First!!", Toast.LENGTH_SHORT).show();
                }

                else{
                    progressBar.setVisibility(View.VISIBLE);
                    user.setBalance(user.getBalance()+Double.parseDouble(money));
                    TransactionModel transac = new TransactionModel(Double.parseDouble(money), DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a").format(LocalDateTime.now()),"In","Topup-"+method);
                    user.getTransactions().add(transac);

                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), "Credit Added Successfully!!", Toast.LENGTH_SHORT).show();
                                method="";
                                jazzcashMethod.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                                easypaiseMethod.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                                debitMethod.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                                onBackPressed();


                            }

                            else{
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), "Credit Could Not Be Added!!", Toast.LENGTH_SHORT).show();
                                method="";
                                jazzcashMethod.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                                easypaiseMethod.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                                debitMethod.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                            }



                        }
                    });

                }

            }
        });
    }
}