package com.example.tracingcovid;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomerInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView shoppname,shopaddress,shopphone;
    public ItemClickListner listner;

    public CustomerInfoViewHolder(@NonNull View itemView) {
        super(itemView);
        shoppname = itemView.findViewById(R.id.shoppname);
        shopaddress = itemView.findViewById(R.id.shopaddress);
        shopphone = itemView.findViewById(R.id.shopphone);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }
    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(), false);
    }
}
