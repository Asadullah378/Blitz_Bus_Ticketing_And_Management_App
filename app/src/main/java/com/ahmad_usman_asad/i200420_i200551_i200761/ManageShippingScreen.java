package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ahmad_usman_asad.i200420_i200551_i200761.Adapters.ShippingAdapter;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ShippingModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ManageShippingScreen extends AppCompatActivity {

    ImageView backBtn;
    RecyclerView shippingsRV;
    FloatingActionButton addShippingScreen;
    ShippingAdapter shippingAdapter;
    ArrayList<ShippingModel> shippings;

    public static final String SHIPPING_ID = "com.ahmad_usman_asad.i200420_i200551_i200761.SCHEDULE_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_shipping_screen);

        backBtn = findViewById(R.id.backBtn);
        shippingsRV = findViewById(R.id.shippingRV);
        addShippingScreen = findViewById(R.id.addShippingScreen);

        shippings = new ArrayList<ShippingModel>();
        shippingsRV.setLayoutManager(new LinearLayoutManager(this));
        shippingAdapter = new ShippingAdapter(shippings,this);
        shippingsRV.setAdapter(shippingAdapter);

        FirebaseDatabase.getInstance().getReference("Shippings").orderByChild("user_id").equalTo(FirebaseAuth.getInstance().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                shippings.add(snapshot.getValue(ShippingModel.class));
                shippingAdapter.notifyItemInserted(shippings.size());
                shippingsRV.smoothScrollToPosition(shippings.size());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                for(int i=0; i<shippings.size(); i++) {

                    if (shippings.get(i).getShippingID().equals(snapshot.getValue(ShippingModel.class).getShippingID())){
                        shippings.set(i, snapshot.getValue(ShippingModel.class));
                        shippingAdapter.notifyItemChanged(i);
                        break;
                    }

                }

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

        addShippingScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ManageShippingScreen.this, AddShippingScreen.class);
                startActivity(intent);

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