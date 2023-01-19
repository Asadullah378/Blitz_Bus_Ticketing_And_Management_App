package com.ahmad_usman_asad.i200420_i200551_i200761;

import static com.ahmad_usman_asad.i200420_i200551_i200761.Notifications.ApplicationClass.CHANNEL_1_ID;
import static com.ahmad_usman_asad.i200420_i200551_i200761.Notifications.ApplicationClass.CHANNEL_2_ID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmad_usman_asad.i200420_i200551_i200761.Adapters.AddShippingItemAdapter;
import com.ahmad_usman_asad.i200420_i200551_i200761.Adapters.ScheduleAdapter;
import com.ahmad_usman_asad.i200420_i200551_i200761.External.CustomDateTimePicker;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ShippingItemModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ShippingModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.TransactionModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddShippingScreen extends AppCompatActivity {

    EditText destinationAddress;
    MaterialCardView selectCheckout;
    MaterialCardView regularShipping;
    MaterialCardView fastShipping;
    MaterialCardView extremeShipping;
    TextView checkoutTime;
    String shippingType;
    Button addShippingBtn;
    ImageView backBtn;
    ArrayList<ShippingItemModel> items;
    LocalDateTime coutTime;
    TextView itemsCount;
    Button addItemsScreen;
    double price;
    UserModel user;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_shipping_screen);

        progressBar=findViewById(R.id.ProgressBar);
        destinationAddress = findViewById(R.id.destinationAddress);
        selectCheckout = findViewById(R.id.selectCheckout);
        checkoutTime = findViewById(R.id.checkoutTime);
        regularShipping = findViewById(R.id.regularShipping);
        fastShipping = findViewById(R.id.fastShipping);
        extremeShipping = findViewById(R.id.extremeShipping);
        shippingType = "";
        regularShipping.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        fastShipping.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        extremeShipping.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        addShippingBtn = findViewById(R.id.addShippingBtn);
        backBtn = findViewById(R.id.backBtn);
        itemsCount = findViewById(R.id.itemCount);
        addItemsScreen = findViewById(R.id.addItemsScreen);
        checkoutTime.setText("Select Checkout Time");

        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(UserModel.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        regularShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                regularShipping.setCardBackgroundColor(Color.parseColor("#02FA58"));
                fastShipping.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                extremeShipping.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                shippingType = "Regular";
            }
        });

        fastShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                regularShipping.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                fastShipping.setCardBackgroundColor(Color.parseColor("#02FA58"));
                extremeShipping.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                shippingType = "Fast";
            }
        });

        extremeShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                regularShipping.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                fastShipping.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                extremeShipping.setCardBackgroundColor(Color.parseColor("#02FA58"));
                shippingType = "Extreme";
            }
        });


        items = new ArrayList<ShippingItemModel>();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });

        selectCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDateTimePicker checkoutTimeDialog = new CustomDateTimePicker(AddShippingScreen.this, new CustomDateTimePicker.ICustomDateTimeListener() {

                    @Override
                    public void onSet(Dialog dialog, Calendar calendarSelected,
                                      Date dateSelected, int year, String monthFullName,
                                      String monthShortName, int monthNumber, int day,
                                      String weekDayFullName, String weekDayShortName,
                                      int hour24, int hour12, int min, int sec,
                                      String AM_PM) {

                        coutTime = LocalDateTime.ofInstant(dateSelected.toInstant(), ZoneId.systemDefault());
                        String d = DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a").format(coutTime);
                        checkoutTime.setText(d);
                    }

                    @Override
                    public void onCancel() {

                    }
                });

                checkoutTimeDialog.set24HourFormat(false);
                checkoutTimeDialog.setDate(Calendar.getInstance());
                checkoutTimeDialog.showDialog();
            }
        });


        addShippingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String destination = destinationAddress.getText().toString();
                String checkout = checkoutTime.getText().toString();
                if(destination.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter Destination Address!!", Toast.LENGTH_SHORT).show();
                }
                else if(shippingType.isEmpty()){
                    Toast.makeText(AddShippingScreen.this, "Choose Shipping Type!!", Toast.LENGTH_SHORT).show();
                }
                else if(checkout.equals("Select Checkout Time")){
                    Toast.makeText(AddShippingScreen.this, "Select Checkout Time!!", Toast.LENGTH_SHORT).show();
                }
                else if(items.isEmpty()){
                    Toast.makeText(AddShippingScreen.this, "Add Atleast one Item!!", Toast.LENGTH_SHORT).show();
                }
                else if(user.getBalance() < price){
                    Toast.makeText(AddShippingScreen.this, "Insufficient Balance!!", Toast.LENGTH_SHORT).show();
                }
                else if(coutTime.isBefore(LocalDateTime.now())){
                    Toast.makeText(AddShippingScreen.this, "Invalid Checkout Time!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    user.setBalance(user.getBalance()-price);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Shippings").push();

                    String delivery = "";
                    if(shippingType.equals("Regular"))
                        delivery = DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a").format(coutTime.plusDays(7));

                    else if(shippingType.equals("Fast"))
                        delivery = DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a").format(coutTime.plusDays(3));

                    if(shippingType.equals("Extreme"))
                        delivery = DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a").format(coutTime.plusDays(1));

                    ShippingModel shipping = new ShippingModel(ref.getKey(), destination, checkout, delivery, shippingType, items, price, FirebaseAuth.getInstance().getUid());

                    ref.setValue(shipping).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), "Shipping Added Successfully!!", Toast.LENGTH_SHORT).show();
                                TransactionModel transac = new TransactionModel(price, DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a").format(LocalDateTime.now()),"Out","Shipping Items");
                                user.getTransactions().add(transac);
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).setValue(user);
                                Intent activityIntent=new Intent(AddShippingScreen.this, MainActivity.class);

                                PendingIntent contentIntent=PendingIntent.getActivity(AddShippingScreen.this,
                                        0, activityIntent, PendingIntent.FLAG_MUTABLE);

                                NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                                Notification notification;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


                                        notification = new NotificationCompat.Builder(AddShippingScreen.this, CHANNEL_2_ID)
                                                .setSmallIcon(R.drawable.ic_stat_onesignal_default)
                                                .setContentTitle("Shipping Order Successful")
                                                .setContentText("Your shipping order of " + shipping.getItems().size() + " items to " + shipping.getAddress() + " was placed successfully. Hold Tight!!")
                                                .setStyle(new NotificationCompat.BigTextStyle()
                                                        .bigText("Your shipping order of " + shipping.getItems().size() + " items to " + shipping.getAddress() + " was placed successfully. Hold Tight!!"))
                                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                                .setColor(Color.GREEN)
                                                .setContentIntent(contentIntent) // Set the intent that will fire when the user taps the notification
                                                .setAutoCancel(true)
                                                .setOnlyAlertOnce(true)
                                                .build();
                                        notificationManager.notify(2, notification);



                                }
                                onBackPressed();
                            }
                            else{
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(getApplicationContext(), "Shipping Could Not Be Added!!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }
        });

        addItemsScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AddShippingScreen.this, AddItemsScreen.class);
                startActivityForResult(intent,378);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 378){

            if(resultCode == RESULT_OK){

                items =  data.getExtras().getParcelableArrayList("Items");
                items.remove(items.size()-1);
                price = data.getDoubleExtra(AddItemsScreen.PRICE,0);
                itemsCount.setText("Items Added: "+items.size());


            }

        }

    }
}