package com.example.ecommerce.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.ecommerce.Domain.UserDomain;
import com.example.ecommerce.R;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {
    TextView login;
    EditText userName,userEmail,userPassword,userAddress,userPhone;
    Button signUpBtn;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signUpBtn=findViewById(R.id.signUpBtn);
        userName = findViewById(R.id.editTextName);
        userEmail = findViewById(R.id.editTextEmail);
        userPassword = findViewById(R.id.editTextTextPassword1);
        userAddress = findViewById(R.id.editTextAddress);
        userPhone = findViewById(R.id.editTextPhone);
        login = (TextView) findViewById(R.id.loginBtn);
        fAuth = FirebaseAuth.getInstance();
       database = FirebaseDatabase.getInstance();
       DatabaseReference usersRef = database.getReference("Users");


        signUpBtn.setOnClickListener(view -> {
            String email = userEmail.getText().toString().trim();
            String password = userPassword.getText().toString().trim();
            String fullName = userName.getText().toString().trim();
            String phone = userPhone.getText().toString().trim();
            String address = userAddress.getText().toString().trim();

            if(TextUtils.isEmpty(email)) {
                userEmail.setError("Email is require.");
                return;
            }
            if(TextUtils.isEmpty(password)){
                userPassword.setError("Password is require.");
                return;
            }
            if(password.length() < 6){
                userPassword.setError("Password must be >= 6 Characters.");
                return;
            }
            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = fAuth.getCurrentUser();
                        UserDomain userProfile = new UserDomain(user.getUid(), user.getEmail(), fullName, phone, address, "Viet Nam");
                        usersRef.child(user.getUid()).setValue(userProfile)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Lưu thông tin người dùng thành công
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Lưu thông tin người dùng thất bại, hiển thị lỗi cho người dùng
                                    }
                                });
                    }else{
                        Toast.makeText(RegisterActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
