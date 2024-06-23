package com.example.ecommerce.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.ecommerce.R;

public class CartActivity extends AppCompatActivity {
    ImageView btnBack;
    AppCompatButton btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        btnBack = (ImageView) findViewById(R.id.backBtn);
        btnCheckout = (AppCompatButton) findViewById(R.id.checkOutBtn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(CartActivity.this, MainActivity.class));
                finish();
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                làm thêm chức năng để create order mới trên firebase luôn nha

                startActivity(new Intent(CartActivity.this, SuccessActivity.class));
            }
        });


    }
}
