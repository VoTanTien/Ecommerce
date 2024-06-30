package com.example.ecommerce.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextView btnRegister, btnForgot;
    EditText userEmail,userPassword;
    Button loginBtn;
    FirebaseAuth fAuth;
//    AppCompatButton main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userEmail = findViewById(R.id.editTextEmailLogin);
        userPassword = findViewById(R.id.editTextTextPassword);
        fAuth = FirebaseAuth.getInstance();
        loginBtn= findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener((v)->{
            String email = userEmail.getText().toString().trim();
            String password = userPassword.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
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
            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        btnRegister = (TextView) findViewById(R.id.registerBtn);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnForgot = findViewById(R.id.forgotBtn);
        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });


    }
}

