package com.example.ecommerce.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Adapter.CartAdapter;
import com.example.ecommerce.Adapter.PopularAdapter;
import com.example.ecommerce.Domain.ItemsDomain;
import com.example.ecommerce.Domain.OrderDomain;
import com.example.ecommerce.Domain.UserDomain;
import com.example.ecommerce.R;
import com.example.ecommerce.databinding.ActivityCartBinding;
import com.example.ecommerce.helper.ChangeNumberItemsListener;
import com.example.ecommerce.helper.ManagementCart;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

public class CartActivity extends BaseActivity {
    ImageView btnBack;

    TextView userNameText, userPhoneText, userAddressText,
                totalFeeText, deliveryText, totalBillText;

    RadioGroup paymentTypeGroup;
    EditText voucherEditText;

    AppCompatButton btnCheckout, applyButton;

    RecyclerView cartRecyclerView;

    double shippingRatio = 0.1;

    int total = 0;

    FirebaseAuth fAuth;
    FirebaseUser user;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        myRef = database.getReference("Users").child(user.getUid());

        initView();
        //initView();

        initListener();

        initUser();

        initInformation();

        calculateCart();

        String itemsId = "";
        for(ItemsDomain item : ManagementCart.instance.getItems()) {
            itemsId += item.getItemId() + ",";
        }
        Log.d("product quantity: ", itemsId);
    }



    private void initView() {
        userNameText = findViewById(R.id.nameTxt);
        userPhoneText = findViewById(R.id.phoneTxt);
        userAddressText = findViewById(R.id.addressTxt);
        totalFeeText = findViewById(R.id.totalFee);
        deliveryText = findViewById(R.id.deliveryFee);
        totalBillText = findViewById(R.id.totalBill);

        paymentTypeGroup = findViewById(R.id.paymentType);

        voucherEditText = findViewById(R.id.voucher_editText);

        applyButton = findViewById(R.id.applyButton);
        btnBack = (ImageView) findViewById(R.id.backBtn);
        btnCheckout = (AppCompatButton) findViewById(R.id.checkOutBtn);

        cartRecyclerView = findViewById(R.id.cartView);
    }

    private void initUser() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserDomain userProfile = snapshot.getValue(UserDomain.class);
                    userNameText.setText(userProfile.userName);
                    userPhoneText.setText(userProfile.phone);
                    userAddressText.setText(userProfile.address);
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi khi đọc dữ liệu
            }
        });

    }

    private void initInformation() {
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        cartRecyclerView.setAdapter(new CartAdapter(ManagementCart.instance.getItems(), new ChangeNumberItemsListener() {
            @Override
            public void change() {
                calculateCart();
            }
        }));

        Log.d("init information", "complete");
    }

    private void initListener() {

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
                checkout();
                startActivity(new Intent(CartActivity.this, SuccessActivity.class));
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void checkout() {
        DatabaseReference myRef = database.getReference("Order");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int autoincrementid = 0;
                do {
                    autoincrementid++;
                } while (snapshot.hasChild("" + autoincrementid));

                SimpleDateFormat ft
                        = new SimpleDateFormat("dd-MM-yyyy");

                String str = ft.format(new Date());

                String itemsId = "";
                for(ItemsDomain item : ManagementCart.instance.getItems()) {
                    itemsId += item.getItemId() + ",";
                }

                String userId = user.getUid();
                OrderDomain order = new OrderDomain(str, removeLastCharacter(itemsId), total, userId);

                Log.d("abc", "itemsId: " + itemsId);
                myRef.child("" + autoincrementid).setValue(order);
                ManagementCart.instance.clear();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException(); // never ignore errors
            }
        });

    }

    public String removeLastCharacter(String str) {
        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    private void calculateCart() {
        double productsPrice = ManagementCart.instance.getTotalFee();
        double shipPrice = productsPrice * shippingRatio;

        totalFeeText.setText("$" + Math.round(productsPrice));
        deliveryText.setText("$" + Math.round(shipPrice));

        total = (int) Math.round(productsPrice + shipPrice);
        totalBillText.setText("$" + total);

        Log.d("products price", "" + productsPrice);
    }
}
