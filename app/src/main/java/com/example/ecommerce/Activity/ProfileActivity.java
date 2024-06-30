package com.example.ecommerce.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.ecommerce.Domain.UserDomain;
import com.example.ecommerce.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends BaseActivity {
    ImageView btnBack;
    LinearLayout btnEdit, btnHistory, btnChange;
    AppCompatButton btnLogOut;
    TextView name,email,phone,address,nation;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fAuth = FirebaseAuth.getInstance();
        btnBack = (ImageView) findViewById(R.id.backBtn);
        btnEdit = (LinearLayout) findViewById(R.id.editProfileBtn);
        btnHistory = (LinearLayout) findViewById(R.id.historyBtn);
        btnChange = (LinearLayout) findViewById(R.id.changePassBtn);
        btnLogOut = (AppCompatButton) findViewById(R.id.logOutBtn);
        name = findViewById(R.id.nameTxt);
        email = findViewById(R.id.emailTxt);
        phone = findViewById(R.id.phoneTxt);
        address = findViewById(R.id.addressTxt);
        nation = findViewById(R.id.nationTxt);

        initUser();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, HistoryActivity.class));
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ForgotPasswordActivity.class));
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });

    }

    private void initUser() {
        FirebaseUser user = fAuth.getCurrentUser();
        DatabaseReference myRef = database.getReference("Users").child(user.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserDomain userProfile = snapshot.getValue(UserDomain.class);
                    name.setText(userProfile.userName);
                    email.setText(userProfile.email);
                    phone.setText(userProfile.phone);
                    address.setText(userProfile.address);
                    nation.setText(userProfile.nation);
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi khi đọc dữ liệu
            }
        });
    }
}
