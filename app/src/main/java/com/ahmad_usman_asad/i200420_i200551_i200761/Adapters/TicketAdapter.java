package com.ahmad_usman_asad.i200420_i200551_i200761.Adapters;

import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ScheduleModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ScheduleTicketMergeModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.R;
import com.ahmad_usman_asad.i200420_i200551_i200761.RefundTicketScreen;
import com.ahmad_usman_asad.i200420_i200551_i200761.SelectSchedulesScreen;
import com.ahmad_usman_asad.i200420_i200551_i200761.SelectSeatsScreen;
import com.ahmad_usman_asad.i200420_i200551_i200761.SelectTicketScreen;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.qrcode.encoder.QRCode;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {
    ArrayList<ScheduleTicketMergeModel> tickets;
    Context context;
    String role;

    public TicketAdapter(ArrayList<ScheduleTicketMergeModel> tickets, Context context, String role) {
        this.tickets = tickets;
        this.context = context;
        this.role = role;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.ticket_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        LocalDateTime arr = LocalDateTime.parse(tickets.get(position).getScheduleModel().getArrival_time(),DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));
        LocalDateTime dep = LocalDateTime.parse(tickets.get(position).getScheduleModel().getDeparture_time(),DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));

        holder.arrLocation.setText(tickets.get(position).getScheduleModel().getArrival());
        holder.depLocation.setText(tickets.get(position).getScheduleModel().getDeparture());
        holder.arrTime.setText(DateTimeFormatter.ofPattern("hh:mm a").format(arr));
        holder.arrDate.setText(DateTimeFormatter.ofPattern("E, dd MMM yyyy").format(arr));
        holder.depTime.setText(DateTimeFormatter.ofPattern("hh:mm a").format(dep));
        holder.depDate.setText(DateTimeFormatter.ofPattern("E, dd MMM yyyy").format(dep));
        holder.seat.setText("Seat "+tickets.get(position).getTicket().getSeat_id());



        if(role.equals("Refund")) {
            holder.qrLayout.setVisibility(View.GONE);
            holder.refundBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, RefundTicketScreen.class);
                    intent.putExtra(SelectSchedulesScreen.SCHEDULE_ID,tickets.get(holder.getAdapterPosition()).getScheduleModel().getSchedule_id());
                    intent.putExtra(SelectTicketScreen.SEAT_ID,tickets.get(holder.getAdapterPosition()).getTicket().getSeat_id());
                    intent.putExtra(SelectTicketScreen.RESERVATION_ID,tickets.get(holder.getAdapterPosition()).getReservation_id());
                    context.startActivity(intent);
                }
            });
        }

        else if(role.equals("My Tickets")){
            holder.refundBtn.setVisibility(View.INVISIBLE);
            Glide.with(context).load(tickets.get(position).getTicket().getQr()).into(holder.qrCode);


        }



    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView depTime;
        TextView arrTime;
        TextView depLocation;
        TextView arrLocation;
        TextView seat;
        TextView arrDate;
        TextView depDate;
        CardView ticket_card;
        Button refundBtn;
        ImageView qrCode;
        LinearLayout qrLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            depTime = itemView.findViewById(R.id.depTime);
            arrTime = itemView.findViewById(R.id.arrTime);
            depDate = itemView.findViewById(R.id.depDate);
            arrDate = itemView.findViewById(R.id.arrDate);
            depLocation = itemView.findViewById(R.id.depLocation);
            arrLocation = itemView.findViewById(R.id.arrLocation);
            seat = itemView.findViewById(R.id.seatID);
            refundBtn = itemView.findViewById(R.id.refundTicket);
            ticket_card = itemView.findViewById(R.id.ticket_card);
            qrCode = itemView.findViewById(R.id.qrCode);
            qrLayout = itemView.findViewById(R.id.qrLayout);
        }
    }



}
