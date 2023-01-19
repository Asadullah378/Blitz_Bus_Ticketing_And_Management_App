package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ahmad_usman_asad.i200420_i200551_i200761.Adapters.AddShippingItemAdapter;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ShippingItemModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ShippingModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ShowShippingScreen extends AppCompatActivity {

    ImageView backBtn;
    TextView destination;
    TextView type;
    TextView checkoutDate;
    TextView checkoutTime;
    TextView deliveryDate;
    TextView deliveryTime;
    TextView payment;
    RecyclerView shipItemsRV;
    AddShippingItemAdapter addShippingItemAdapter;
    ArrayList<ShippingItemModel> items;
    String shipping_id;
    ShippingModel current_shipping;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_shipping_screen);

        backBtn = findViewById(R.id.backBtn);
        destination = findViewById(R.id.shippingDest);
        type = findViewById(R.id.shippingType);
        checkoutDate = findViewById(R.id.checkoutDate);
        checkoutTime = findViewById(R.id.checkoutTime);
        deliveryDate = findViewById(R.id.deliveryDate);
        deliveryTime = findViewById(R.id.deliveryTime);
        payment = findViewById(R.id.shippingPrice);
        shipItemsRV = findViewById(R.id.shipItemsRV);
        progressBar = findViewById(R.id.ProgressBar);
        shipItemsRV.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        shipping_id = intent.getStringExtra(ManageShippingScreen.SHIPPING_ID);

        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference("Shippings").child(shipping_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                current_shipping = snapshot.getValue(ShippingModel.class);

                LocalDateTime c = LocalDateTime.parse(current_shipping.getCheckoutTime(), DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));
                LocalDateTime d = LocalDateTime.parse(current_shipping.getDeliveryDate(),DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));

                destination.setText("Destination: "+current_shipping.getAddress());
                type.setText("Type: "+current_shipping.getShippingType());
                checkoutDate.setText("Checkout Date: "+DateTimeFormatter.ofPattern("E, dd MMM yyyy").format(c));
                checkoutTime.setText("Checkout Time: "+DateTimeFormatter.ofPattern("hh:mm a").format(c));
                deliveryDate.setText("Delivery Date: "+DateTimeFormatter.ofPattern("E, dd MMM yyyy").format(d));
                deliveryTime.setText("Delivery Time: "+DateTimeFormatter.ofPattern("hh:mm a").format(d));
                payment.setText("Payment: PKR. "+current_shipping.getPrice());

                addShippingItemAdapter = new AddShippingItemAdapter(current_shipping.getItems(),ShowShippingScreen.this,null,"View");
                shipItemsRV.setAdapter(addShippingItemAdapter);
                progressBar.setVisibility(View.GONE);
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