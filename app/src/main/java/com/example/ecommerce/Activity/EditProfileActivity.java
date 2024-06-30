package com.example.ecommerce.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends BaseActivity {

    ImageView btnBack;
    AppCompatButton btnComplete;
    EditText name, nation, address, phone;

    FirebaseAuth fAuth;
    FirebaseUser user;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        myRef = database.getReference("Users").child(user.getUid());

        btnBack = (ImageView) findViewById(R.id.backBtn);
        btnComplete = findViewById(R.id.completeBtn);
        name = findViewById(R.id.editTextName);
        nation = findViewById(R.id.editTextNation);
        address = findViewById(R.id.editTextAddress);
        phone = findViewById(R.id.editTextPhone);

        initUser();

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInformation();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initUser() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserDomain userProfile = snapshot.getValue(UserDomain.class);
                    name.setText(userProfile.userName);
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
    private void updateUserInformation(){
        String updatedName = name.getText().toString();
        String updatedNation = nation.getText().toString();
        String updatedAddress = address.getText().toString();
        String updatedPhone = phone.getText().toString();

        Map<String, Object> updates = new HashMap<>();
        updates.put("userName", updatedName);
        updates.put("nation", updatedNation);
        updates.put("address", updatedAddress);
        updates.put("phone", updatedPhone);

        myRef.updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EditProfileActivity.this, "User information updated", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(EditProfileActivity.this, "Error updating information: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }
}
