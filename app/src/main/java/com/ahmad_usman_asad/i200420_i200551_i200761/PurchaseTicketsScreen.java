package com.ahmad_usman_asad.i200420_i200551_i200761;

import static com.ahmad_usman_asad.i200420_i200551_i200761.Notifications.ApplicationClass.CHANNEL_1_ID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ReservationModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ScheduleModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ScheduledBusModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.SeatModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.TicketModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.TransactionModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.CountDownLatch;

public class PurchaseTicketsScreen extends AppCompatActivity {

    TextView departLocation;
    TextView departDate;
    TextView departTime;
    TextView arriLocation;
    TextView arriDate;
    TextView arriTime;
    TextView totalSeats;
    TextView seatPrice;
    TextView paymentAmount;
    Button purchaseBtn;
    ImageView backBtn;
    ArrayList<SeatModel> seats;
    String schedule_id;
    ScheduledBusModel scheduledBus;
    ScheduleModel schedule;
    DatabaseReference updateBusRef;
    UserModel user;
    ProgressBar progressBar;
    boolean uploaded;
    int total;
    int done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_tickets_screen);

        departLocation = findViewById(R.id.departLocation);
        departDate = findViewById(R.id.departDate);
        departTime = findViewById(R.id.departTime);
        arriLocation = findViewById(R.id.arriLocation);
        arriDate = findViewById(R.id.arriDate);
        arriTime = findViewById(R.id.arriTime);
        totalSeats = findViewById(R.id.totalSeats);
        seatPrice = findViewById(R.id.seatPrice);
        paymentAmount = findViewById(R.id.paymentAmount);
        purchaseBtn = findViewById(R.id.purchaseBtn);
        backBtn = findViewById(R.id.backBtn);

        progressBar = findViewById(R.id.ProgressBar);
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        seats = intent.getExtras().getParcelableArrayList("Selected Seats");
        schedule_id = intent.getStringExtra(SelectSchedulesScreen.SCHEDULE_ID);

        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(UserModel.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("ScheduledBuses").orderByChild("schedule_id").equalTo(schedule_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds: snapshot.getChildren()){

                    if(ds.getValue(ScheduledBusModel.class).getSchedule_id().equals(schedule_id)){
                        scheduledBus = ds.getValue(ScheduledBusModel.class);
                        updateBusRef = ds.getRef();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Schedules").orderByKey().equalTo(schedule_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds: snapshot.getChildren()){

                    if(ds.getValue(ScheduleModel.class).getSchedule_id().equals(schedule_id)){
                        schedule = ds.getValue(ScheduleModel.class);
                    }

                }

                LocalDateTime dep = LocalDateTime.parse(schedule.getDeparture_time(), DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));
                LocalDateTime arr = LocalDateTime.parse(schedule.getArrival_time(), DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));
                departLocation.setText(schedule.getDeparture());
                arriLocation.setText(schedule.getArrival());
                departDate.setText("Date: "+DateTimeFormatter.ofPattern("E, dd MMM yyyy").format(dep));
                departTime.setText("Time: "+DateTimeFormatter.ofPattern("hh:mm a").format(dep));
                arriDate.setText("Date: "+DateTimeFormatter.ofPattern("E, dd MMM yyyy").format(arr));
                arriTime.setText("Time: "+DateTimeFormatter.ofPattern("hh:mm a").format(arr));
                seatPrice.setText("Ticket Price: "+schedule.getTicket_price());
                totalSeats.setText("Total Seats: "+seats.size());
                paymentAmount.setText("Total Payment: "+seats.size()*schedule.getTicket_price());
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        purchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (user.getBalance() < seats.size() * schedule.getTicket_price()) {
                    Toast.makeText(getApplicationContext(), "Insufficient Balance", Toast.LENGTH_SHORT).show();
                } else {
                    user.setBalance(user.getBalance()-seats.size() * schedule.getTicket_price());
                    ArrayList<TicketModel> tickets = new ArrayList<TicketModel>();
                    for (int i = 0; i < seats.size(); i++) {

                        for (int j = 0; j < scheduledBus.getSeats().size(); j++) {

                            if (seats.get(i).getSeat_id() == scheduledBus.getSeats().get(j).getSeat_id()) {

                                scheduledBus.getSeats().get(j).setBooking_status(true);
                                scheduledBus.setRemaining_seats(scheduledBus.getRemaining_seats() - 1);
                                tickets.add(new TicketModel(seats.get(i).getSeat_id(), schedule.getTicket_price(),""));
                            }

                        }


                    }

                    progressBar.setVisibility(View.VISIBLE);
                    DatabaseReference reservRef = FirebaseDatabase.getInstance().getReference("Reservations").push();
                    ReservationModel reservation = new ReservationModel(reservRef.getKey(), FirebaseAuth.getInstance().getUid(), schedule_id, DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a").format(LocalDateTime.now()), tickets);

                    total=tickets.size();
                    done=0;
                    for(int i=0; i<tickets.size(); i++){

                        try {
                            Bitmap bmp = generateQrCode(reservation.getReservation_id()+"/"+tickets.get(i).getSeat_id());
                            uploadQRcode(bmp,reservation.getReservation_id()+"/"+tickets.get(i).getSeat_id(),tickets,i,reservRef.getKey());


                        } catch (WriterException e) {
                            e.printStackTrace();
                        }

                    }



                    reservRef.setValue(reservation).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                updateBusRef.setValue(scheduledBus).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            TransactionModel transac = new TransactionModel(seats.size() * schedule.getTicket_price(), DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a").format(LocalDateTime.now()),"Out","Tickets Purchase");
                                            user.getTransactions().add(transac);
                                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                                    if(task.isSuccessful()){
                                                        progressBar.setVisibility(View.GONE);
                                                        Toast.makeText(getApplicationContext(), "Purchase Successful!!", Toast.LENGTH_SHORT).show();
                                                        Intent activityIntent=new Intent(PurchaseTicketsScreen.this, MainActivity.class);

                                                        PendingIntent contentIntent=PendingIntent.getActivity(PurchaseTicketsScreen.this,
                                                                0, activityIntent, PendingIntent.FLAG_MUTABLE);

                                                        Notification notification;
                                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                                                            {
                                                                notification = new NotificationCompat.Builder(PurchaseTicketsScreen.this, CHANNEL_1_ID)
                                                                        .setSmallIcon(R.drawable.ic_stat_onesignal_default)
                                                                        .setContentTitle("Purchase Successful")
                                                                        .setContentText("Your purchase of " + reservation.getTickets().size() + " Tickets For Bus From " + schedule.getDeparture() + " To " + schedule.getArrival() + " was Successful.")
                                                                        .setStyle(new NotificationCompat.BigTextStyle()
                                                                                .bigText("Your purchase of " + reservation.getTickets().size() + " Tickets For Bus From " + schedule.getDeparture() + " To " + schedule.getArrival() + " was Successful."))
                                                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                                                        .setColor(Color.GREEN)
                                                                        .setContentIntent(contentIntent) // Set the intent that will fire when the user taps the notification
                                                                        .setAutoCancel(true)
                                                                        .setOnlyAlertOnce(true)
                                                                        .build();
                                                                notificationManager.notify(1, notification);
                                                            }
                                                            Intent intent = new Intent(PurchaseTicketsScreen.this, UserMainScreen.class);
                                                            startActivity(intent);
                                                        }
                                                    }
                                                    else{
                                                        progressBar.setVisibility(View.GONE);
                                                        Toast.makeText(getApplicationContext(), "Purchase Failed!!", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(PurchaseTicketsScreen.this, UserMainScreen.class);
                                                        startActivity(intent);
                                                    }



                                                }
                                            });

                                        } else {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(), "Purchase Failed!!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(PurchaseTicketsScreen.this, UserMainScreen.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Purchase Failed!!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PurchaseTicketsScreen.this, UserMainScreen.class);
                                startActivity(intent);
                            }

                        }
                    });


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

    void uploadQRcode(Bitmap qrBmp, String id, ArrayList<TicketModel> tickets, int i, String r_id){

        StorageReference storageRef =  FirebaseStorage.getInstance().getReference().child("QRs/"+id);

        uploaded=false;
        if(qrBmp!=null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            qrBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            storageRef.putBytes(data)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> resultTask = taskSnapshot.getStorage().getDownloadUrl();
                            resultTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    uploaded=true;
                                    tickets.get(i).setQr(uri.toString());
                                    done++;
                                    if(done==total){

                                        FirebaseDatabase.getInstance().getReference("Reservations").child(r_id).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                ReservationModel reserv = snapshot.getValue(ReservationModel.class);
                                                reserv.setTickets(tickets);
                                                snapshot.getRef().setValue(reserv);


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }

                            });
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });


        }


    }

    public static Bitmap generateQrCode(String myCodeText) throws WriterException {
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // H = 30% damage

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        int size = 256;

        BitMatrix bitMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size, size);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);



        return bitmap;
    }
}