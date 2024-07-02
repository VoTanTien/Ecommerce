package com.example.ecommerce.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerce.Activity.DetailActivity;
import com.example.ecommerce.Activity.RateActivity;
import com.example.ecommerce.Domain.ItemsDomain;
import com.example.ecommerce.databinding.ViewholderHistoryItemBinding;

import java.util.ArrayList;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.Viewholder>{
    ArrayList<ItemsDomain> items;
    Context context;

    public OrderItemAdapter(ArrayList<ItemsDomain> items) {
        this.items = items;
    }
    @NonNull
    @Override
    public OrderItemAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderHistoryItemBinding binding = ViewholderHistoryItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemAdapter.Viewholder holder, int position) {
        holder.binding.titleTxt.setText(items.get(position).getTitle());
        holder.binding.brandTxt.setText(items.get(position).getBrand());
        holder.binding.priceTxt.setText("$"+String.valueOf(items.get(position).getPrice()));

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop());

        Glide.with(context)
                .load(items.get(position).getPicUrl().get(0))
                .apply(requestOptions)
                .into(holder.binding.pic);

        holder.binding.rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RateActivity.class);
                intent.putExtra("object", items.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ViewholderHistoryItemBinding binding;
        public Viewholder(ViewholderHistoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
