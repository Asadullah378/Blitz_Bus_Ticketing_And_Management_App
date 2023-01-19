package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {



    BroadcastReceiver broadcastReceiver;
    BroadcastReceiver orientationBroadcastReceiver;
    class Helper extends TimerTask
    {
        public void run()
        {

                if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                    Intent intent = new Intent(MainActivity.this, ChooseRoleScreen.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(MainActivity.this, UserMainScreen.class);
                    startActivity(intent);
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase.getInstance().getReference("Users").keepSynced(true);
        FirebaseDatabase.getInstance().getReference("Shippings").keepSynced(true);
        FirebaseDatabase.getInstance().getReference("Reservations").keepSynced(true);
        FirebaseDatabase.getInstance().getReference("Schedules").keepSynced(true);
        FirebaseDatabase.getInstance().getReference("ScheduledBuses").keepSynced(true);
        FirebaseDatabase.getInstance().getReference("Buses").keepSynced(true);
        FirebaseDatabase.getInstance().getReference("Announcements").keepSynced(true);
        FirebaseDatabase.getInstance().getReference("Complaints").keepSynced(true);
        FirebaseDatabase.getInstance().getReference("Manager").keepSynced(true);
        broadcastReceiver = new NetworkChangeReceiver();
        orientationBroadcastReceiver = new OrientationBroadcastReceiver();
        registerBroadcastReciever();

        Timer timer = new Timer();
        TimerTask task = new Helper();
        timer.schedule(task, 3000);
    }

    public void registerBroadcastReciever(){


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            registerReceiver(orientationBroadcastReceiver, new IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED));
        }

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            registerReceiver(orientationBroadcastReceiver, new IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED));

        }
    }

    protected void unregisterNetwork(){

        try{
            unregisterReceiver(broadcastReceiver);
            unregisterReceiver(orientationBroadcastReceiver);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetwork();
    }
}