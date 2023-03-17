package com.example.ecart.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecart.Adapter.CategoriesAdapter;
import com.example.ecart.Adapter.ProductAdapter;
import com.example.ecart.ModelClass.CategoriesModel;
import com.example.ecart.ModelClass.ProductsModel;
import com.example.ecart.ModelClass.UserModel;
import com.example.ecart.R;
import com.example.ecart.databinding.FragmentHomeBinding;
import com.facebook.shimmer.Shimmer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentHomeBinding binding;
    CategoriesAdapter categoriesAdapter;
    DatabaseReference reference;
    ProductAdapter productAdapter;

    ArrayList<ProductsModel> productslist;
    ProductsModel productsModel;
    String UID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        reference = FirebaseDatabase.getInstance().getReference();
        reference.keepSynced(true);
        UID = FirebaseAuth.getInstance().getUid();
//        binding.shimmerViewContainerForCate.setShimmer(this);
        reference.child("USERS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    if (UID.equals(userModel.getUid())) {
                        binding.USerNameHomefId.setText("Hi!  "+userModel.getName());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        LinearLayoutManager llm = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.categoriesHomeRecyclerViewId.setLayoutManager(llm);
        categoriesAdapter = new CategoriesAdapter(getContext());
        reference.child("CATEGORIES").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoriesAdapter.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CategoriesModel categoriesModel = dataSnapshot.getValue(CategoriesModel.class);
                    categoriesAdapter.add(categoriesModel);
                    binding.shimmerViewContainerForCate.stopShimmer();
                    binding.shimmerViewContainerForCate.hideShimmer();
                    binding.shimerLayoutCateHideId.setVisibility(View.GONE);
                    categoriesAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "error::" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.categoriesHomeRecyclerViewId.setAdapter(categoriesAdapter);
        categoriesAdapter.notifyDataSetChanged();


        //product chekck
        productslist = new ArrayList<>();
        productAdapter = new ProductAdapter(getContext(), productslist);
        reference.child("PRODUCTS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productslist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    productsModel = dataSnapshot.getValue(ProductsModel.class);
                    productslist.add(productsModel);
                    binding.shimmerViewContainerForProduct.stopShimmer();
                    binding.shimerViewLayoutForProduct.setVisibility(View.GONE);
                    productAdapter.notifyDataSetChanged();
                    Collections.shuffle(productslist);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "error::" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.productHomeRecyclerViewId.setLayoutManager(new GridLayoutManager(getContext(), 2));


        binding.productHomeRecyclerViewId.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();


        return binding.getRoot();
    }
}