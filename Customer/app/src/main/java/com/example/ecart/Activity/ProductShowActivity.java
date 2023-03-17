package com.example.ecart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecart.Adapter.ProductAdapter;
import com.example.ecart.ModelClass.ProductsModel;
import com.example.ecart.R;
import com.example.ecart.databinding.ActivityProductShowBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ProductShowActivity extends AppCompatActivity {

    String PId, PCate;
    DatabaseReference reference;
    ActivityProductShowBinding binding;

    ProductAdapter productAdapter;

    int qunty = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        PId = getIntent().getStringExtra("PID");
        PCate = getIntent().getStringExtra("PCate");

        AllProduct();
        SimilierProduct();

        binding.productShowPriceId.setPaintFlags(binding.productShowPriceId.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        reference = FirebaseDatabase.getInstance().getReference("PRODUCTS");
        reference.keepSynced(true); //product add
        reference.child(PId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProductsModel productsModel = snapshot.getValue(ProductsModel.class);
                binding.productShowNameId.setText(productsModel.getProductName());
                binding.productShowDiscoutId.setText(productsModel.getProductDiscount() + "%");
                binding.productShowDescriptionId.setText(productsModel.getProductDescription());
                binding.productShowPriceId.setText("\u20B9" + productsModel.getProductPrice());
                binding.productShowDiscoutPriceId.setText("\u20B9" + productsModel.getProductDiscountPrice());
                Glide.with(binding.showProductImgeId).load(productsModel.getProductImage()).into(binding.showProductImgeId);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ProductShowActivity.this, "error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qunty++;
                binding.quantityTextview.setText(String.valueOf(qunty));
            }
        });
        binding.decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qunty != 1) {
                    qunty--;
                    binding.quantityTextview.setText(String.valueOf(qunty));

                }
            }
        });


    }

    private void SimilierProduct() {
        reference = FirebaseDatabase.getInstance().getReference("PRODUCTS");
        reference.keepSynced(true);
        ArrayList<ProductsModel> slist = new ArrayList<>();
        productAdapter = new ProductAdapter(ProductShowActivity.this, slist);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProductsModel productsModel = dataSnapshot.getValue(ProductsModel.class);
                    if (PCate.equals(productsModel.getProductCategories())) {
                        slist.add(productsModel);
                        Collections.shuffle(slist);
                        productAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProductShowActivity.this, "error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(ProductShowActivity.this, RecyclerView.HORIZONTAL, false);
        binding.productShowSimiliiarproRecyclerViewId.setLayoutManager(llm);
        binding.productShowSimiliiarproRecyclerViewId.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }

    private void AllProduct() {
        reference = FirebaseDatabase.getInstance().getReference("PRODUCTS");
        reference.keepSynced(true);

        ArrayList<ProductsModel> alist = new ArrayList<>();
        productAdapter = new ProductAdapter(ProductShowActivity.this, alist);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProductsModel productsModel = dataSnapshot.getValue(ProductsModel.class);
                    alist.add(productsModel);
                    Collections.shuffle(alist);
                    productAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProductShowActivity.this, "error:" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        binding.productShowAllproductRecyclerViewId.setLayoutManager(new GridLayoutManager(ProductShowActivity.this,2));
        binding.productShowAllproductRecyclerViewId.setAdapter(productAdapter);

    }
}