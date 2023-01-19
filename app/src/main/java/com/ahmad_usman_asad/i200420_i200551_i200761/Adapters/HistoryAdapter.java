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
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ReservationScheduleMergeModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    ArrayList<ReservationScheduleMergeModel> reservations;
    Context context;

    public HistoryAdapter(ArrayList<ReservationScheduleMergeModel> reservations, Context context) {
        this.reservations = reservations;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.reservation_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        LocalDateTime arr = LocalDateTime.parse(reservations.get(position).getScheduleModel().getArrival_time(), DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));
        LocalDateTime dep = LocalDateTime.parse(reservations.get(position).getScheduleModel().getDeparture_time(),DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));

        LocalDateTime res = LocalDateTime.parse(reservations.get(position).getReservationModel().getReservation_date(), DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));
        holder.arrLocation.setText(reservations.get(position).getScheduleModel().getArrival());
        holder.depLocation.setText(reservations.get(position).getScheduleModel().getDeparture());
        holder.arrTime.setText(DateTimeFormatter.ofPattern("hh:mm a").format(arr));
        holder.arrDate.setText(DateTimeFormatter.ofPattern("E, dd MMM yyyy").format(arr));
        holder.depTime.setText(DateTimeFormatter.ofPattern("hh:mm a").format(dep));
        holder.depDate.setText(DateTimeFormatter.ofPattern("E, dd MMM yyyy").format(dep));
        holder.price.setText("PKR. "+reservations.get(position).getScheduleModel().getTicket_price());
        holder.seats.setText("Seats: "+reservations.get(position).getReservationModel().getTickets().size());
        holder.reservTime.setText(DateTimeFormatter.ofPattern("hh:mm a").format(res));
        holder.reservDate.setText("Reserved: "+DateTimeFormatter.ofPattern("E, dd MMM yyyy").format(res));

        if(holder.getAdapterPosition()%2==0){
            holder.reservCard.setCardBackgroundColor(Color.parseColor("#A2E4A5"));

        }
        else{
            holder.reservCard.setCardBackgroundColor(Color.parseColor("#59E15F"));
        }

    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView depTime;
        TextView arrTime;
        TextView depLocation;
        TextView arrLocation;
        TextView price;
        TextView arrDate;
        TextView seats;
        TextView depDate;
        TextView reservDate;
        TextView reservTime;
        CardView reservCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            depTime = itemView.findViewById(R.id.depTime);
            arrTime = itemView.findViewById(R.id.arrTime);
            depDate = itemView.findViewById(R.id.depDate);
            arrDate = itemView.findViewById(R.id.arrDate);
            depLocation = itemView.findViewById(R.id.depLocation);
            arrLocation = itemView.findViewById(R.id.arrLocation);
            price = itemView.findViewById(R.id.price);
            seats = itemView.findViewById(R.id.seats);
            reservDate = itemView.findViewById(R.id.reservDate);
            reservTime = itemView.findViewById(R.id.reservTime);
            reservCard = itemView.findViewById(R.id.reservation_card);
        }
    }



}
