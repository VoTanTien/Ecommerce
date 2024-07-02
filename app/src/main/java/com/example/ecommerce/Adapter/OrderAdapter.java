package com.example.ecommerce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerce.Domain.ItemsDomain;
import com.example.ecommerce.Domain.OrderDomain;
import com.example.ecommerce.databinding.ViewholderHistoryOrderBinding;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.Viewholder> {
    ArrayList<OrderDomain> orders;
    Context context;

    public OrderAdapter(ArrayList<OrderDomain> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderHistoryOrderBinding binding =ViewholderHistoryOrderBinding.inflate(LayoutInflater.from(context),parent,false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.Viewholder holder, int position) {
        holder.binding.dateTxt.setText(orders.get(position).getDate());
        holder.binding.totalTxt.setText("$"+orders.get(position).getTotal());

        OrderItemAdapter orderItemAdapter = new OrderItemAdapter((ArrayList<ItemsDomain>) orders.get(position).getItems());
        holder.binding.orderItemView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.binding.orderItemView.setAdapter(orderItemAdapter);


    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ViewholderHistoryOrderBinding binding;
        public Viewholder(ViewholderHistoryOrderBinding binding){
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
