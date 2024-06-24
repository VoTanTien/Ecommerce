package com.example.ecommerce.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerce.Domain.ItemsDomain;
import com.example.ecommerce.R;
import com.example.ecommerce.helper.ManagementCart;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    ImageView btnBack;
    TextView productNameText, ratingText, priceText;
    RatingBar ratingBar;
    AppCompatButton addToCartBtn;

    ImageView picture;
    ItemsDomain item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();

        item = (ItemsDomain) getIntent().getSerializableExtra("object");
        if (item != null) {
            initInformation(item);
        }

        initListener();
    }

    private void initView() {
         productNameText = findViewById(R.id.titleTxt);
         ratingBar = findViewById(R.id.ratingBar);
         ratingText = findViewById(R.id.ratingTxt);
         priceText = findViewById(R.id.priceTxt);
         btnBack = (ImageView) findViewById(R.id.backBtn);
         addToCartBtn = findViewById(R.id.addToCartBtn);
         picture = findViewById(R.id.picture);
    }

    private void initInformation(ItemsDomain item) {
        productNameText.setText(item.getTitle());
        ratingBar.setRating((float)item.getRating());
        ratingText.setText(String.valueOf(item.getRating()) + " rate");
        priceText.setText("$" + String.valueOf(item.getPrice()));

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop());

        Glide.with(this)
                .load(item.getPicUrl().get(0))
                .apply(requestOptions)
                .into(picture);
    }

    private void initListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(DetailActivity.this, MainActivity.class));
                finish();
            }
        });
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManagementCart.instance.insertItems(item);
            }
        });
    }
}