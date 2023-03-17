package com.example.ecart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.ecart.Adapter.ProductAdapter;
import com.example.ecart.ModelClass.CategoriesModel;
import com.example.ecart.ModelClass.ProductsModel;
import com.example.ecart.R;
import com.example.ecart.databinding.ActivityCategoriesToProductsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class CategoriesToProductsActivity extends AppCompatActivity {

    ActivityCategoriesToProductsBinding binding;
    DatabaseReference reference;
    ProductAdapter productAdapter;
    ArrayList<ProductsModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoriesToProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String Catname = getIntent().getStringExtra("CatName");
        binding.cateToProToolbarId.setTitle(Catname);
        list = new ArrayList<>();
        productAdapter = new ProductAdapter(this, list);
        reference = FirebaseDatabase.getInstance().getReference("PRODUCTS");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProductsModel productsModel = dataSnapshot.getValue(ProductsModel.class);
                    if (Catname.equals(productsModel.getProductCategories())) {
                        list.add(productsModel);
                        Collections.shuffle(list);
                        System.out.println("m"+productsModel);
                        System.out.println("l"+list);
                    } else {
//                        System.out.println("a"+productsModel);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CategoriesToProductsActivity.this, "error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.cateToProRecyclerViewId.setLayoutManager(new GridLayoutManager(this,2));
        binding.cateToProRecyclerViewId.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();



    }
}