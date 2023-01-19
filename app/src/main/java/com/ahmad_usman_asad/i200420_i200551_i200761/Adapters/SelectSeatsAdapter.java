package com.ahmad_usman_asad.i200420_i200551_i200761.Adapters;

import android.content.Context;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad_usman_asad.i200420_i200551_i200761.Interfaces.SelectSeatInterface;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.SeatRowModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.R;


import java.util.ArrayList;

public class SelectSeatsAdapter extends RecyclerView.Adapter<SelectSeatsAdapter.ViewHolder> {
    ArrayList<SeatRowModel> seatRows;
    Context context;
    SelectSeatInterface selectSeatInterface;

    public SelectSeatsAdapter(ArrayList<SeatRowModel> seatRows, Context context, SelectSeatInterface selectSeatInterface) {
        this.seatRows = seatRows;
        this.context = context;
        this.selectSeatInterface = selectSeatInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.seats_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.seat1id.setText(seatRows.get(position).getSeats().get(0).getSeat_id()+"");
        holder.seat2id.setText(seatRows.get(position).getSeats().get(1).getSeat_id()+"");
        holder.seat3id.setText(seatRows.get(position).getSeats().get(2).getSeat_id()+"");
        holder.seat4id.setText(seatRows.get(position).getSeats().get(3).getSeat_id()+"");


        if(seatRows.get(position).getSeats().get(0).isBooking_status()){
            holder.seat1.setCardBackgroundColor(Color.parseColor("#D15D5D"));
        }
        else{
            holder.seat1.setCardBackgroundColor(Color.parseColor("#A0E365"));

            holder.seat1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!seatRows.get(holder.getAdapterPosition()).isSelect1()) {
                        selectSeatInterface.addSeat(seatRows.get(holder.getAdapterPosition()).getSeats().get(0).getSeat_id());
                        holder.seat1.setCardBackgroundColor(Color.parseColor("#73CBD5"));
                        seatRows.get(holder.getAdapterPosition()).setSelect1(true);
                    }
                    else{
                        selectSeatInterface.removeSeat(seatRows.get(holder.getAdapterPosition()).getSeats().get(0).getSeat_id());
                        holder.seat1.setCardBackgroundColor(Color.parseColor("#A0E365"));
                        seatRows.get(holder.getAdapterPosition()).setSelect1(false);
                    }
                }
            });

        }

        if(seatRows.get(position).getSeats().get(1).isBooking_status()){
            holder.seat2.setCardBackgroundColor(Color.parseColor("#D15D5D"));
        }
        else{
            holder.seat2.setCardBackgroundColor(Color.parseColor("#A0E365"));

            holder.seat2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!seatRows.get(holder.getAdapterPosition()).isSelect2()) {
                        selectSeatInterface.addSeat(seatRows.get(holder.getAdapterPosition()).getSeats().get(1).getSeat_id());

                        holder.seat2.setCardBackgroundColor(Color.parseColor("#73CBD5"));
                        seatRows.get(holder.getAdapterPosition()).setSelect2(true);
                    }
                    else{
                        selectSeatInterface.removeSeat(seatRows.get(holder.getAdapterPosition()).getSeats().get(1).getSeat_id());
                        holder.seat2.setCardBackgroundColor(Color.parseColor("#A0E365"));
                        seatRows.get(holder.getAdapterPosition()).setSelect2(false);
                    }
                }
            });

        }

        if(seatRows.get(position).getSeats().get(2).isBooking_status()){
            holder.seat3.setCardBackgroundColor(Color.parseColor("#D15D5D"));
        }
        else{
            holder.seat3.setCardBackgroundColor(Color.parseColor("#A0E365"));

            holder.seat3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!seatRows.get(holder.getAdapterPosition()).isSelect3()) {
                        selectSeatInterface.addSeat(seatRows.get(holder.getAdapterPosition()).getSeats().get(2).getSeat_id());
                        holder.seat3.setCardBackgroundColor(Color.parseColor("#73CBD5"));
                        seatRows.get(holder.getAdapterPosition()).setSelect3(true);
                    }
                    else{
                        selectSeatInterface.removeSeat(seatRows.get(holder.getAdapterPosition()).getSeats().get(2).getSeat_id());
                        holder.seat3.setCardBackgroundColor(Color.parseColor("#A0E365"));
                        seatRows.get(holder.getAdapterPosition()).setSelect3(false);
                    }
                }
            });

        }

        if(seatRows.get(position).getSeats().get(3).isBooking_status()){
            holder.seat4.setCardBackgroundColor(Color.parseColor("#D15D5D"));
        }
        else{
            holder.seat4.setCardBackgroundColor(Color.parseColor("#A0E365"));

            holder.seat4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!seatRows.get(holder.getAdapterPosition()).isSelect4()) {
                        selectSeatInterface.addSeat(seatRows.get(holder.getAdapterPosition()).getSeats().get(3).getSeat_id());
                        holder.seat4.setCardBackgroundColor(Color.parseColor("#73CBD5"));
                        seatRows.get(holder.getAdapterPosition()).setSelect4(true);
                    }
                    else{
                        selectSeatInterface.removeSeat(seatRows.get(holder.getAdapterPosition()).getSeats().get(3).getSeat_id());
                        holder.seat4.setCardBackgroundColor(Color.parseColor("#A0E365"));
                        seatRows.get(holder.getAdapterPosition()).setSelect4(false);
                    }
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return seatRows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView seat1;
        CardView seat2;
        CardView seat3;
        CardView seat4;
        TextView seat1id;
        TextView seat2id;
        TextView seat3id;
        TextView seat4id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            seat1 = itemView.findViewById(R.id.seat1);
            seat2 = itemView.findViewById(R.id.seat2);
            seat3 = itemView.findViewById(R.id.seat3);
            seat4 = itemView.findViewById(R.id.seat4);
            seat1id = itemView.findViewById(R.id.seat1id);
            seat2id = itemView.findViewById(R.id.seat2id);
            seat3id = itemView.findViewById(R.id.seat3id);
            seat4id = itemView.findViewById(R.id.seat4id);

        }
    }



}
