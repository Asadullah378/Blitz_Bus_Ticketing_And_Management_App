package com.ahmad_usman_asad.i200420_i200551_i200761;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmad_usman_asad.i200420_i200551_i200761.Adapters.AddShippingItemAdapter;
import com.ahmad_usman_asad.i200420_i200551_i200761.Interfaces.AddItemsInterface;
import com.ahmad_usman_asad.i200420_i200551_i200761.Models.ShippingItemModel;

import java.util.ArrayList;

public class AddItemsScreen extends AppCompatActivity implements AddItemsInterface {

    RecyclerView itemsRV;
    Button addItemsBtn;
    ImageView backBtn;
    AddShippingItemAdapter addShippingItemAdapter;
    ArrayList<ShippingItemModel> items;
    AddItemsInterface addItemsInterface;
    TextView shippingPrice;
    int current;
    double price=0;
    public static final String PRICE = "com.ahmad_usman_asad.i200420_i200551_i200761.PRICE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_items_screen);

        addItemsInterface = this;
        itemsRV = findViewById(R.id.itemsRV);
        backBtn = findViewById(R.id.backBtn);
        addItemsBtn = findViewById(R.id.addItemsBtn);
        shippingPrice = findViewById(R.id.shippingPrice);

        shippingPrice.setText("Total Charges: PKR. "+price);


        items = new ArrayList<ShippingItemModel>();
        items.add(new ShippingItemModel());
        current=0;

        itemsRV.setLayoutManager(new LinearLayoutManager(this));
        addShippingItemAdapter = new AddShippingItemAdapter(items,this, addItemsInterface,"Add");
        itemsRV.setAdapter(addShippingItemAdapter);

        addItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.putExtra(PRICE,price);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("Items", items);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void addItem(String name, double weight) {
        items.get(current).setName(name);
        items.get(current).setWeight(weight);
        price += weight*100;
        current++;
        items.add(new ShippingItemModel());
        addShippingItemAdapter = new AddShippingItemAdapter(items,this, addItemsInterface,"Add");
        itemsRV.setAdapter(addShippingItemAdapter);
        shippingPrice.setText("Total Charges: PKR. "+price);

    }

    @Override
    public void removeItem(int index) {

        price -= items.get(index).getWeight()*100;
        items.remove(index);
        current--;
        addShippingItemAdapter = new AddShippingItemAdapter(items,this, addItemsInterface,"Add");
        itemsRV.setAdapter(addShippingItemAdapter);
        shippingPrice.setText("Total Charges: PKR. "+price);
    }

}