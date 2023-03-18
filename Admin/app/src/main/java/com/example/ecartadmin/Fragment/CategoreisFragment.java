package com.example.ecartadmin.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecartadmin.Adapter.CategoryAdapter;
import com.example.ecartadmin.Model.CategoriesModel;
import com.example.ecartadmin.R;
import com.example.ecartadmin.databinding.FragmentCategoreisBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoreisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoreisFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoreisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoreisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoreisFragment newInstance(String param1, String param2) {
        CategoreisFragment fragment = new CategoreisFragment();
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

    FragmentCategoreisBinding binding;
    CategoryAdapter adapter;
    ArrayList<CategoriesModel> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoreisBinding.inflate(inflater, container, false);
        binding.catRecyclerViewId.setLayoutManager(new GridLayoutManager(getContext(), 3));
        arrayList = new ArrayList<>();
        adapter = new CategoryAdapter(getContext(), arrayList);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CATEGORIES");
        reference.keepSynced(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CategoriesModel categoriesModel = dataSnapshot.getValue(CategoriesModel.class);
                    arrayList.add(categoriesModel);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "error::" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.catRecyclerViewId.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return binding.getRoot();
    }
}