package com.example.tracingcovid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class customerDetail extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference ShopRef;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        ShopRef = FirebaseDatabase.getInstance().getReference().child("ShopHistory").child("123456789");
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Shop> options =
                new FirebaseRecyclerOptions.Builder<Shop>()
                        .setQuery(ShopRef, Shop.class)
                        .build();

        FirebaseRecyclerAdapter<Shop,CustomerInfoViewHolder> adapter =
                new FirebaseRecyclerAdapter<Shop, CustomerInfoViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CustomerInfoViewHolder holder, int i, @NonNull Shop shop) {
                        holder.shopphone.setText(shop.getShopphone());
                        holder.shoppname.setText(shop.getShopName());
                        holder.shopaddress.setText(shop.getShopaddress());

                    }

                    @NonNull
                    @Override
                    public CustomerInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customerinfolayout, parent, false);
                        CustomerInfoViewHolder holder = new CustomerInfoViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
