package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ahmad_usman_asad.i200420_i200551_i200761.Adapters.TransactionsAdapter;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.TransactionModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewTransactionsScreen extends AppCompatActivity {

    ImageView backBtn;
    RecyclerView trxRV;
    TransactionsAdapter transactionsAdapter;
    ArrayList<TransactionModel> transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_transactions_screen);

        backBtn = findViewById(R.id.backBtn);
        trxRV = findViewById(R.id.trxRV);

        transactions = new ArrayList<TransactionModel>();
        trxRV.setLayoutManager(new LinearLayoutManager(this));
        transactionsAdapter = new TransactionsAdapter(transactions,this);
        trxRV.setAdapter(transactionsAdapter);


        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).child("transactions").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                transactions.add(snapshot.getValue(TransactionModel.class));
                transactionsAdapter.notifyItemInserted(transactions.size());
                trxRV.smoothScrollToPosition(transactions.size());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


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

    }
}