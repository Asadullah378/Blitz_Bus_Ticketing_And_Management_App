package com.ahmad_usman_asad.i200420_i200551_i200761.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad_usman_asad.i200420_i200551_i200761.Interfaces.AddItemsInterface;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ShippingItemModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.R;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;

public class AddShippingItemAdapter extends RecyclerView.Adapter<AddShippingItemAdapter.ViewHolder> {
    ArrayList<ShippingItemModel> items;
    Context context;
    AddItemsInterface addItemsInterface;
    String role;
    public AddShippingItemAdapter(ArrayList<ShippingItemModel> items, Context context, AddItemsInterface addItemsInterface, String role) {
        this.items = items;
        this.context = context;
        this.addItemsInterface = addItemsInterface;
        this.role = role;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.add_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if(role.equals("Add")) {
            if (holder.getAdapterPosition() == getItemCount() - 1) {
                holder.delItemBtn.setVisibility(View.GONE);

            } else {
                holder.addItemBtn.setVisibility(View.GONE);
                holder.itemName.setFocusable(false);
                holder.itemWeight.setFocusable(false);
                holder.itemName.setText(items.get(holder.getAdapterPosition()).getName());
                holder.itemWeight.setText("" + items.get(holder.getAdapterPosition()).getWeight());
            }

            holder.addItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String name = holder.itemName.getText().toString();
                    String weight = holder.itemWeight.getText().toString();
                    if (name.isEmpty())
                        Toast.makeText(context, "Enter Item Name First", Toast.LENGTH_SHORT).show();

                    else if (weight.isEmpty())
                        Toast.makeText(context, "Enter Item Weight First", Toast.LENGTH_SHORT).show();

                    else {

                        addItemsInterface.addItem(name, Double.parseDouble(weight));
                    }

                }
            });

            holder.delItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addItemsInterface.removeItem(holder.getLayoutPosition());
                }
            });
        }

        else if(role.equals("View")){
            holder.delItemBtn.setVisibility(View.GONE);
            holder.addItemBtn.setVisibility(View.GONE);
            holder.itemName.setText(items.get(holder.getAdapterPosition()).getName());
            holder.itemWeight.setText("" + items.get(holder.getAdapterPosition()).getWeight());
        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText itemName;
        EditText itemWeight;
        CardView addItemBtn;
        ImageView delItemBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            itemWeight = itemView.findViewById(R.id.itemWeight);
            addItemBtn = itemView.findViewById(R.id.addItemBtn);
            delItemBtn = itemView.findViewById(R.id.delItemBtn);
        }
    }



}
