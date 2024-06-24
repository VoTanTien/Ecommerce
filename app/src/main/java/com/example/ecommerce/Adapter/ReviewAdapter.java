package com.example.ecommerce.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerce.Domain.ReviewDomain;
import com.example.ecommerce.databinding.ViewholderReviewBinding;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    ArrayList<ReviewDomain> reviews;
    Context context;

    public ReviewAdapter(ArrayList<ReviewDomain> items) {
        reviews = items;
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderReviewBinding binding = ViewholderReviewBinding.inflate(LayoutInflater.from(context), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, int position) {
        ReviewDomain review = reviews.get(position);
        holder.binding.name.setText(review.Name);
        holder.binding.descriptiontxt.setText(review.Description);
        Log.d("abc", "rating: " + review.Rating);
        holder.binding.ratingtxt.setText("" + review.Rating);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop());

        Glide.with(holder.itemView.getContext())
                .load(review.PicUrl)
                .apply(requestOptions)
                .into(holder.binding.pictureReview);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderReviewBinding binding;
        public ViewHolder(ViewholderReviewBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
