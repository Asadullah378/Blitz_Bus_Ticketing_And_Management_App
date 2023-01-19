package com.ahmad_usman_asad.i200420_i200551_i200761;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        try{

            if(isOnline(context)){
                Toast.makeText(context, "Network Connected!!", Toast.LENGTH_SHORT).show();
            }

            else{
                Toast.makeText(context, "Network Disconnected!!", Toast.LENGTH_SHORT).show();
                
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public boolean isOnline(Context context){

        try{

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            return networkInfo!=null && networkInfo.isConnected();

        }catch (Exception e){


            e.printStackTrace();
            return false;
        }

    }

}