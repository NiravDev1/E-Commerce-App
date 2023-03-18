package com.example.ecart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecart.Adapter.ProductAdapter;
import com.example.ecart.ModelClass.CartModel;
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

    String PId, PCate, Pimge;
    DatabaseReference reference;
    ActivityProductShowBinding binding;

    ProductAdapter productAdapter;

    long qunty = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        PId = getIntent().getStringExtra("PID");
        PCate = getIntent().getStringExtra("PCate");
        Pimge = getIntent().getStringExtra("PImge");
        AllProduct();


        binding.productShowAddToCartBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ProductUId,ProductName,CustomerUID,ProductDiscountPrice,ProductPrice,ProductDiscount,ProductQuantity,ProductImage;*/
                reference = FirebaseDatabase.getInstance().getReference("CART");
                String CUID = FirebaseAuth.getInstance().getUid();
                String pname = binding.productShowNameId.getText().toString();
                String pprice = binding.productShowPriceId.getText().toString();
                String pdprice = binding.productShowDiscoutPriceId.getText().toString();
                String quntity = binding.quantityTextview.getText().toString();

                long t = Long.parseLong(binding.productShowDiscoutPriceId.getText().toString());
                long stotalprice = t * Long.parseLong(quntity);
                System.out.println(stotalprice);
                String Sdpricee = String.valueOf(stotalprice);

                CartModel cartModel = new CartModel(PId, pname, CUID, pdprice, pprice, Sdpricee, quntity, Pimge);
                reference.child(CUID).child(PId).setValue(cartModel);

                startActivity(new Intent(ProductShowActivity.this, CartActivity.class));
                finish();


            }
        });


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
                binding.productShowPriceId.setText(productsModel.getProductPrice());
                binding.productShowDiscoutPriceId.setText(productsModel.getProductDiscountPrice());
                Glide.with(binding.showProductImgeId).load(productsModel.getProductImage()).into(binding.showProductImgeId);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ProductShowActivity.this, "error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.productShowSimiliarProBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PCate = getIntent().getStringExtra("PCate");
                System.out.println(PCate);
                Intent intent = new Intent(ProductShowActivity.this, CategoriesToProductsActivity.class);
                intent.putExtra("CatName", PCate);
                startActivity(intent);
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
        binding.productShowAllproductRecyclerViewId.setLayoutManager(new GridLayoutManager(ProductShowActivity.this, 2));
        binding.productShowAllproductRecyclerViewId.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();

    }
}