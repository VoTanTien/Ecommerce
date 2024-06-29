package com.example.ecommerce;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
<<<<<<< Updated upstream:app/src/main/java/com/example/ecommerce/ProfileActivity.java
import android.widget.ImageView;
=======
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
>>>>>>> Stashed changes:app/src/main/java/com/example/ecommerce/Activity/RegisterActivity.java

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

<<<<<<< Updated upstream:app/src/main/java/com/example/ecommerce/ProfileActivity.java
public class ProfileActivity extends AppCompatActivity {
    ImageView back;
=======
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {
    TextView login;
    EditText userName,userEmail,userPassword,userAddress,userPhone;
    Button signUpBtn;
    FirebaseAuth fAuth;

>>>>>>> Stashed changes:app/src/main/java/com/example/ecommerce/Activity/RegisterActivity.java
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< Updated upstream:app/src/main/java/com/example/ecommerce/ProfileActivity.java
        setContentView(R.layout.activity_profile);

        back = (ImageView) findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
=======
        setContentView(R.layout.activity_signup);
        signUpBtn=findViewById(R.id.signUpBtn);
        userName = findViewById(R.id.editTextName);
        userEmail = findViewById(R.id.editTextEmail);
        userPassword = findViewById(R.id.editTextTextPassword1);
        userAddress = findViewById(R.id.editTextAddress);
        userPhone = findViewById(R.id.editTextPhone);
        login = (TextView) findViewById(R.id.loginBtn);
        fAuth = FirebaseAuth.getInstance();


        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        signUpBtn.setOnClickListener(view -> {
            String email = userEmail.getText().toString().trim();
            String password = userPassword.getText().toString().trim();
            String fullName = userName.getText().toString().trim();
            String phone = userPhone.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
                userEmail.setError("Email is require.");
                return;
>>>>>>> Stashed changes:app/src/main/java/com/example/ecommerce/Activity/RegisterActivity.java
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
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }else{
                        Toast.makeText(RegisterActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
<<<<<<< Updated upstream:app/src/main/java/com/example/ecommerce/ProfileActivity.java
=======
        login.setOnClickListener(v -> finish());

>>>>>>> Stashed changes:app/src/main/java/com/example/ecommerce/Activity/RegisterActivity.java
    }
}
