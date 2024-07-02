package com.example.ecommerce.Activity;

import android.icu.util.ULocale;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Adapter.CategoryAdapter;
import com.example.ecommerce.Adapter.PopularAdapter;
import com.example.ecommerce.Domain.CategoryDomain;
import com.example.ecommerce.Domain.ItemsDomain;
import com.example.ecommerce.R;
import com.example.ecommerce.databinding.ActivityMainBinding;
import com.example.ecommerce.databinding.ActivitySearchBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchActivity extends BaseActivity  {
    private EditText editTextSearch;
    String brandSearch, typeSearch;
    private Spinner spinnerType, spinnerBrand;
    private Button filterBtn;
    ActivitySearchBinding binding;
    private ArrayList<ItemsDomain> itemsSearch, itemsFull;
    private ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        editTextSearch = findViewById(R.id.editTextSearch1);
        editTextSearch.clearFocus();
        spinnerType = findViewById(R.id.spinner_type);
        spinnerBrand = findViewById(R.id.spinner_brand);
        filterBtn = findViewById(R.id.filterBtn);
        btnBack = (ImageView) findViewById(R.id.backBtn);

        itemsSearch = new ArrayList<>();
        itemsFull = initItem();
        Log.d("bbc", "items: " + itemsFull);

        ArrayList<String> brandArray = new ArrayList<String>();
        brandArray.add("");
        brandArray.add("Asus");
        brandArray.add("Acer");
        brandArray.add("Apple");
        brandArray.add("MSI");

        ArrayAdapter brandArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,brandArray);
        spinnerBrand.setAdapter(brandArrayAdapter);

        ArrayList<String> typeArray = new ArrayList<String>();
        typeArray.add("");
        typeArray.add("Office");
        typeArray.add("Gaming");

        ArrayAdapter typeArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,typeArray);
        spinnerType.setAdapter(typeArrayAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = editTextSearch.getText().toString().toLowerCase();
                itemsSearch.clear();
                if (searchText.isEmpty()){
                    if(brandSearch == ""){
                        if (typeSearch == "") {
                            itemsSearch.addAll(itemsFull);
                        }
                        else {
                            for (ItemsDomain item : itemsFull) {
                                if (item.getType().toLowerCase().contains(typeSearch)) {
                                    itemsSearch.add(item);
                                }
                            }
                        }
                    } else {
                        for (ItemsDomain item : itemsFull) {
                            if (item.getType().toLowerCase().contains(typeSearch)
                                    && item.getBrand().toLowerCase().contains(brandSearch)) {
                                itemsSearch.add(item);
                            }
                        }
                    }
                }
                else {
                    for (ItemsDomain item : itemsFull) {
                        if (item.getType().toLowerCase().contains(typeSearch)
                                && item.getBrand().toLowerCase().contains(brandSearch)
                                && item.getTitle().toLowerCase().contains(searchText)) {
                            itemsSearch.add(item);
                        }
                    }
                }
                binding.recycleviewSearch.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
                binding.recycleviewSearch.setAdapter(new PopularAdapter(itemsSearch));
            }
        });

        spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brandSearch = parent.getItemAtPosition(position).toString().toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                brandSearch = "";
            }
        });

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeSearch = parent.getItemAtPosition(position).toString().toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                typeSearch = "";
            }
        });
    }
    private ArrayList<ItemsDomain> initItem() {
        DatabaseReference myRef = database.getReference("Items");
        ArrayList<ItemsDomain> items = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(ItemsDomain.class));
                    }
                    if(!items.isEmpty()){
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return items;
    }
}
