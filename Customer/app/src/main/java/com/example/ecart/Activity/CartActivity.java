package com.example.ecart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ecart.Adapter.CartAdapter;
import com.example.ecart.ModelClass.CartModel;
import com.example.ecart.R;
import com.example.ecart.databinding.ActivityCartBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Format;
import java.util.ArrayList;
import java.util.Collections;

public class CartActivity extends AppCompatActivity {

    ActivityCartBinding binding;
    CartAdapter cartAdapter;
    ArrayList<CartModel> list;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String uid = FirebaseAuth.getInstance().getUid();
        reference = FirebaseDatabase.getInstance().getReference("CART").child(uid);

        list = new ArrayList<>();
        cartAdapter = new CartAdapter(this, list);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long cout = 0;
                long gtotle;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CartModel cartModel = dataSnapshot.getValue(CartModel.class);
                    list.add(cartModel);
                    gtotle = Long.parseLong(cartModel.getProductSprice());
                    System.out.println(cout = cout + gtotle);
                    cartAdapter.notifyDataSetChanged();
                    binding.cartItemCountId.setText(String.valueOf(list.size()) + "  items");
                }
                System.out.println("total" + cout);
                binding.cartItemTotalPricePrice.setText(String.valueOf(cout));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CartActivity.this, "error::" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.cartRecyclerViewId.setLayoutManager(new LinearLayoutManager(this));
        binding.cartRecyclerViewId.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();


        binding.ToHomeAcitivityBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, HomeActivity.class));
                finish();
            }
        });


    }
}