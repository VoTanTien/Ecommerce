package com.example.ecommerce.Activity;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Adapter.CategoryAdapter;
import com.example.ecommerce.Adapter.OrderAdapter;
import com.example.ecommerce.Domain.CategoryDomain;
import com.example.ecommerce.Domain.ItemsDomain;
import com.example.ecommerce.Domain.OrderDomain;
import com.example.ecommerce.R;
import com.example.ecommerce.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class HistoryActivity extends BaseActivity {
    ImageView btnBack;
    RecyclerView orderRecyclerView;
    OrderAdapter orderAdapter;
    ActivityMainBinding binding;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);

        fAuth = FirebaseAuth.getInstance();
        btnBack = (ImageView) findViewById(R.id.backBtn);
        orderRecyclerView = findViewById(R.id.orderHistoryView);

        initOrder();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initOrder() {

        FirebaseUser user = fAuth.getCurrentUser();
        DatabaseReference orderRef = database.getReference("Order");
        DatabaseReference itemRef = database.getReference("Items");

        Query query = orderRef.orderByChild("userId").equalTo(user.getUid());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot orderSnapshot) {
                ArrayList<OrderDomain> orders = new ArrayList<>();
                orderAdapter = new OrderAdapter(orders);
                setOrderAdapter();

                for(DataSnapshot eachOrder : orderSnapshot.getChildren()) {
                    OrderDomain order = eachOrder.getValue(OrderDomain.class);

                    // Tách chuỗi itemsId thành List<String>
                    String itemsIdString = order.getItemsId(); // Lấy chuỗi itemsId từ OrderDomain
                    List<String> itemsId = new ArrayList<>(Arrays.asList(itemsIdString.split(","))); // Tách chuỗi bởi ", "
                    Map<Integer, ItemsDomain> orderItems = new HashMap<>();
                    //ArrayList<ItemsDomain> orderItems = new ArrayList<>();
                    Log.d("abc", "itemsId: " + itemsId);

                    for (String itemId : itemsId) {
                        Query itemQuery = itemRef.child(itemId);
                        Log.d("bbc", "itemsQuery: " + itemQuery);

                        itemQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot itemSnapshot) {

                                if (itemSnapshot.exists()) {
//                                    for (DataSnapshot itemData : itemSnapshot.getChildren()) {
//                                        ItemsDomain item = itemData.getValue(ItemsDomain.class);
//                                        orderItems.add(item);
//                                    }
                                    ItemsDomain item = itemSnapshot.getValue(ItemsDomain.class);
                                    orderItems.put(item.getItemId(), item);
                                    order.setItems(new ArrayList<ItemsDomain>(orderItems.values()));

                                    boolean isContain = false;
                                    for(OrderDomain orderDomain : orders) {
                                        if (orderDomain.getItemsId() == order.getItemsId()) {
                                            isContain = true;
                                            break;
                                        }
                                    }

                                    if (!isContain) {
                                        orders.add(order);
                                        refreshData();
                                    }
                                    Log.d("bbc", "items: " + itemSnapshot.getValue());
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Xử lý lỗi
                            }
                        });

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi
            }

        });

    }

    private void setOrderAdapter(){
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this, LinearLayoutManager.VERTICAL, false));
        orderRecyclerView.setAdapter(orderAdapter);
    }

    private void refreshData() {
        orderAdapter.notifyDataSetChanged();
    }

    private void addOrder(OrderDomain order, ArrayList<ItemsDomain> orderItems) {
        order.setItems(orderItems); // Lưu danh sách items vào OrderDomain
    }
    private void addOrderItemToOrder(ArrayList<OrderDomain> orders, OrderDomain order){
        orders.add(order);
    }
}
