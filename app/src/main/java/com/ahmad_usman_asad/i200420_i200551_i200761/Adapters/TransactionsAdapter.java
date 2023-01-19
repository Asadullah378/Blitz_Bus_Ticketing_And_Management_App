package com.ahmad_usman_asad.i200420_i200551_i200761.Adapters;

import android.content.Context;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad_usman_asad.i200420_i200551_i200761.Models.BusModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.TransactionModel;
import com.ahmad_usman_asad.i200420_i200551_i200761.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {
    ArrayList<TransactionModel> transactions;
    Context context;

    public TransactionsAdapter(ArrayList<TransactionModel> transactions, Context context) {
        this.transactions = transactions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.transaction_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        String type = transactions.get(position).getType();
        holder.trxName.setText(transactions.get(position).getName());


        if(type.equals("In")){
            holder.trxCard.setCardBackgroundColor(Color.parseColor("#B5CC9D"));
            holder.trxAmount.setText("+"+transactions.get(holder.getAdapterPosition()).getAmount());
        }
        else{
            holder.trxCard.setCardBackgroundColor(Color.parseColor("#E3A5A5"));
            holder.trxAmount.setText("-"+transactions.get(holder.getAdapterPosition()).getAmount());
        }

        LocalDateTime ldt = LocalDateTime.parse(transactions.get(position).getTransactionTime(), DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));

        holder.trxTime.setText(DateTimeFormatter.ofPattern("hh:mm a").format(ldt));

        String s1 ="";
        String s2 ="";

        LocalDateTime cDate = LocalDateTime.now();

        if (ChronoUnit.DAYS.between(ldt, cDate) == 0)
            s1 = "Today";

        else if (ChronoUnit.DAYS.between(ldt, cDate) == 1)
            s1 = "Yesterday";

        else if (ChronoUnit.DAYS.between(ldt, cDate) < 7)
            s1 = DateTimeFormatter.ofPattern("EEEE").format(ldt);

        else if (ChronoUnit.YEARS.between(ldt, cDate) < 1)
            s1 = DateTimeFormatter.ofPattern("E, MMM d").format(ldt);

        else
            s1 = DateTimeFormatter.ofPattern("MMM dd, yyyy").format(ldt);
        if (holder.getAdapterPosition() - 1 >= 0) {

            LocalDateTime ldt2 = LocalDateTime.parse(transactions.get(holder.getAdapterPosition()-1).getTransactionTime(), DateTimeFormatter.ofPattern("E, dd MMM yyyy hh:mm a"));

            if (ChronoUnit.DAYS.between(ldt2, cDate) == 0)
                s2 = "Today";

            else if (ChronoUnit.DAYS.between(ldt2, cDate) == 1)
                s2 = "Yesterday";

            else if (ChronoUnit.DAYS.between(ldt2, cDate) < 7)
                s2 = DateTimeFormatter.ofPattern("EEEE").format(ldt2);

            else if (ChronoUnit.YEARS.between(ldt2, cDate) < 1)
                s2 = DateTimeFormatter.ofPattern("E, MMM d").format(ldt2);

            else
                s2 = DateTimeFormatter.ofPattern("MMM dd, yyyy").format(ldt2);

        }

        if (s1.equals(s2))
            holder.dateCard.setVisibility(View.GONE);
        else {


            holder.trxDate.setText(s1);
        }

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView trxName;
        TextView trxAmount;
        TextView trxDate;
        TextView trxTime;
        CardView dateCard;
        CardView trxCard;
        LinearLayout trxLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            trxName = itemView.findViewById(R.id.trxName);
            trxAmount = itemView.findViewById(R.id.trxAmount);
            trxDate = itemView.findViewById(R.id.trxDate);
            trxTime = itemView.findViewById(R.id.trxTime);
            dateCard = itemView.findViewById(R.id.dateCard);
            trxCard = itemView.findViewById(R.id.trxCard);
            trxLayout = itemView.findViewById(R.id.trxLayout);
        }
    }



}
