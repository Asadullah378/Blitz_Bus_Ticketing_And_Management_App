package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ComplaintModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddComplaintScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner complaintType;
    EditText complaint;
    Button submitComplaintBtn;
    ImageView backBtn;
    int index;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_complaint_screen);

        progressBar = findViewById(R.id.ProgressBar);
        index=0;
        complaintType = findViewById(R.id.complaintType);
        complaint = findViewById(R.id.complaint);
        submitComplaintBtn = findViewById(R.id.submitComplaintBtn);
        backBtn = findViewById(R.id.backBtn);

        String[] types = {"Miscellaneous","Bus","Driver","Ticket","Shipping","Refund","Feedback"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, types);
        complaintType.setAdapter(adapter);
        complaintType.setOnItemSelectedListener(AddComplaintScreen.this);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        submitComplaintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String compl = complaint.getText().toString();

                if(compl.isEmpty())
                    Toast.makeText(getApplicationContext(), "Enter Your Complaint!!", Toast.LENGTH_SHORT).show();

                else{

                    progressBar.setVisibility(View.VISIBLE);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Complaints").push();

                    ComplaintModel complain = new ComplaintModel(ref.getKey(), FirebaseAuth.getInstance().getUid(), compl, types[index],"",false,false);

                    ref.setValue(complain).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), "Complaint Submitted Successfully!!", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }

                            else{
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), "Complaint Could Not be Submitted!!", Toast.LENGTH_SHORT).show();
                                complaintType.setSelection(0);
                                index=0;
                                complaint.setText("");
                            }
                        }
                    });

                }
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        index=i;

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}