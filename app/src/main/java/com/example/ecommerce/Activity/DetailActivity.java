package com.example.ecommerce.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerce.Adapter.ViewPagerAdapter;
import com.example.ecommerce.Domain.ItemsDomain;
import com.example.ecommerce.Fragment.DescriptionFragment;
import com.example.ecommerce.Fragment.ReviewFragment;
import com.example.ecommerce.R;
import com.example.ecommerce.helper.ManagementCart;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    ImageView btnBack;
    TextView productNameText, ratingText, priceText;
    RatingBar ratingBar;
    AppCompatButton addToCartBtn;
    ViewPager viewPager;
    TabLayout tabLayout;
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

        setupViewPager();
    }

    private void initView() {
         productNameText = findViewById(R.id.titleTxt);
         ratingBar = findViewById(R.id.ratingBar);
         ratingText = findViewById(R.id.ratingTxt);
         priceText = findViewById(R.id.priceTxt);
         btnBack = (ImageView) findViewById(R.id.backBtn);
         addToCartBtn = findViewById(R.id.addToCartBtn);
         picture = findViewById(R.id.picture);
         viewPager = findViewById(R.id.viewpager);
         tabLayout = findViewById(R.id.tabLayout);
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
                if (ManagementCart.instance.insertItems(item)) {
                    Toast.makeText(DetailActivity.this, "Add To Card Successfully!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(DetailActivity.this, "Add To Card Fail!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        DescriptionFragment tab1 = new DescriptionFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("description", item.getDescription());
        tab1.setArguments(bundle1);

        ReviewFragment tab2 = new ReviewFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("itemId", item.getItemId());
        tab2.setArguments(bundle2);

        adapter.addFragment(tab1, "Description");
        adapter.addFragment(tab2, "Reviews");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}