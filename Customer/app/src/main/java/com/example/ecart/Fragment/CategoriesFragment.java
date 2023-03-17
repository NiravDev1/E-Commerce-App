package com.example.ecart.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecart.Adapter.CategoriesAdapter;
import com.example.ecart.ModelClass.CategoriesModel;
import com.example.ecart.R;
import com.example.ecart.databinding.FragmentCategoriesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesFragment newInstance(String param1, String param2) {
        CategoriesFragment fragment = new CategoriesFragment();
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

    CategoriesAdapter categoriesAdapter;
    FragmentCategoriesBinding binding;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        reference= FirebaseDatabase.getInstance().getReference("CATEGORIES");
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        categoriesAdapter=new CategoriesAdapter(getContext());
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoriesAdapter.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    CategoriesModel categoriesModel=dataSnapshot.getValue(CategoriesModel.class);
                    categoriesAdapter.add(categoriesModel);
                    categoriesAdapter.notifyDataSetChanged();
                    binding.shimmerViewContainerForCate.stopShimmer();
                    binding.shimerLayoutCateHideId.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "error::"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.categoriesInCategoriesRecyclerviewId.setLayoutManager(new GridLayoutManager(getContext(),3));
        binding.categoriesInCategoriesRecyclerviewId.setAdapter(categoriesAdapter);
        categoriesAdapter.notifyDataSetChanged();



        return binding.getRoot();
    }
}