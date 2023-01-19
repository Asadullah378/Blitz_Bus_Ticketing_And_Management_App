package com.ahmad_usman_asad.i200420_i200551_i200761.Adapters;

import android.content.Context;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad_usman_asad.i200420_i200551_i200761.Models.BusModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolder> {
    ArrayList<BusModel> buses;
    Context context;

    public BusAdapter(ArrayList<BusModel> buses, Context context) {
        this.buses = buses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.bus_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bus_number.setText(buses.get(position).getBusNumber());
        holder.bus_seats.setText("Seats: "+buses.get(position).getNumSeats());
        holder.bus_model.setText(buses.get(position).getModel());

        if(holder.getAdapterPosition()%2==0){
            holder.bus_card.setCardBackgroundColor(Color.parseColor("#A2E4A5"));

        }
        else{
            holder.bus_card.setCardBackgroundColor(Color.parseColor("#59E15F"));
        }



        holder.del_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase.getInstance().getReference("Buses").child(buses.get(holder.getAdapterPosition()).getBusNumber()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(!task.isSuccessful()){
                            Toast.makeText(context.getApplicationContext(), "Unable to Delete Bus!!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return buses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView bus_number;
        TextView bus_seats;
        TextView bus_model;
        CardView bus_card;
        ImageView del_bus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bus_number = itemView.findViewById(R.id.bus_num);
            bus_seats = itemView.findViewById(R.id.bus_seats);
            bus_card = itemView.findViewById(R.id.bus_card);
            del_bus = itemView.findViewById(R.id.del_bus);
            bus_model = itemView.findViewById(R.id.bus_model);
        }
    }



}
