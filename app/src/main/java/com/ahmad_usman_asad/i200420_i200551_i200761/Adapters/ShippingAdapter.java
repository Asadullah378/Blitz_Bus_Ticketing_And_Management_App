package com.ahmad_usman_asad.i200420_i200551_i200761.Adapters;

import android.content.Context;

import android.content.Intent;
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

import com.ahmad_usman_asad.i200420_i200551_i200761.ManageShippingScreen;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.BusModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ShippingModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.R;
import com.ahmad_usman_asad.i200420_i200551_i200761.ShowShippingScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ShippingAdapter extends RecyclerView.Adapter<ShippingAdapter.ViewHolder> {
    ArrayList<ShippingModel> shippings;
    Context context;

    public ShippingAdapter(ArrayList<ShippingModel> shippings, Context context) {
        this.shippings = shippings;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.shipping_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(shippings.get(holder.getAdapterPosition()).getAddress().length() <= 15)
            holder.shippingDestination.setText(shippings.get(holder.getAdapterPosition()).getAddress());
        else
            holder.shippingDestination.setText(shippings.get(holder.getAdapterPosition()).getAddress().substring(0,15)+"....");

        holder.itemsCount.setText("Items: "+shippings.get(holder.getAdapterPosition()).getItems().size());

        LocalDateTime del = LocalDateTime.parse(shippings.get(holder.getAdapterPosition()).getDeliveryDate(), DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));

        if(del.isBefore(LocalDateTime.now())){

            holder.shippingStatus.setText("Completed");
            holder.shippingStatus.setTextColor(Color.parseColor("#0ABA11"));
            holder.deliveryDate.setText("Delivered: "+DateTimeFormatter.ofPattern("E, dd MMM yyyy").format(del));
        }

        else{
            holder.shippingStatus.setText("Pending");
            holder.shippingStatus.setTextColor(Color.parseColor("#F60000"));
            holder.deliveryDate.setText("Delivery: "+DateTimeFormatter.ofPattern("E, dd MMM yyyy").format(del));
        }

        holder.shippingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ShowShippingScreen.class);
                intent.putExtra(ManageShippingScreen.SHIPPING_ID,shippings.get(holder.getAdapterPosition()).getShippingID());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return shippings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView shippingDestination;
        TextView shippingStatus;
        TextView deliveryDate;
        TextView itemsCount;
        CardView shippingCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

                shippingDestination = itemView.findViewById(R.id.shippingDestination);
                shippingStatus = itemView.findViewById(R.id.shippingStatus);
                deliveryDate = itemView.findViewById(R.id.deliveryDate);
                itemsCount = itemView.findViewById(R.id.shippingItems);
                shippingCard = itemView.findViewById(R.id.shipping_card);
        }
    }



}
