package com.example.ecommerce.Activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.ecommerce.Domain.ItemsDomain;
import com.example.ecommerce.Domain.ReviewDomain;
import com.example.ecommerce.Domain.UserDomain;
import com.example.ecommerce.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class RateActivity extends BaseActivity {
    RatingBar ratingBar;
    ImageView btnBack;
    AppCompatButton btnSend;
    EditText descriptionEditText;
    float rate;
    String name;
    FirebaseAuth fAuth;
    ItemsDomain item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        fAuth = FirebaseAuth.getInstance();
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btnBack = (ImageView) findViewById(R.id.backBtn);
        btnSend =findViewById(R.id.sendBtn);
        descriptionEditText = findViewById(R.id.editTextDescription);


        
        initUser();

        item = (ItemsDomain) getIntent().getSerializableExtra("object");

        LayerDrawable stars=(LayerDrawable)ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Hiển thị thông báo khi rating thay đổi
                rate = rating;
                // Bạn có thể sử dụng biến 'rating' (kiểu float) để lưu trữ hoặc xử lý giá trị đánh giá.
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = database.getReference("Review");
                FirebaseUser user = fAuth.getCurrentUser();
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int autoincrementid = 0;
                        do {
                            autoincrementid++;
                        } while (snapshot.hasChild("" + autoincrementid));
                        String picUrl = "https://firebasestorage.googleapis.com/v0/b/techconnect-cd40d.appspot.com/o/profile.png?alt=media&token=c884b154-1787-4890-82e2-4a30d82f45cf";
                        String description = descriptionEditText.getText().toString();
                        ReviewDomain review = new ReviewDomain(name, description, item.getItemId(), picUrl, rate);

                        myRef.child("" + autoincrementid).setValue(review);
                        Toast.makeText(RateActivity.this, "Send review successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
                    name = userProfile.getUserName();
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
