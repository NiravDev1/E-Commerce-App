package com.example.ecartadmin.AddFragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.ecartadmin.Model.CategoriesModel;
import com.example.ecartadmin.Model.ProductsModel;
import com.example.ecartadmin.R;
import com.example.ecartadmin.databinding.FragmentAddProductsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProductsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProductsFragment newInstance(String param1, String param2) {
        AddProductsFragment fragment = new AddProductsFragment();
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

    FragmentAddProductsBinding binding;
    DatabaseReference reference;
    List list;
    CategoriesModel categoriesModel;
    StorageReference storageReference;
    Uri uri;
    Bitmap bitmap;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddProductsBinding.inflate(inflater, container, false);

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.loadinglayout);
        dialog.setCancelable(false);
        reference = FirebaseDatabase.getInstance().getReference();
        reference.keepSynced(true);
//        binding.categoryP.setPrompt("select Categories");
        list = new ArrayList();
        list.add(0, "Select Categories");
        //categories data retrieve
        reference.child("CATEGORIES").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    categoriesModel = dataSnapshot.getValue(CategoriesModel.class);
                    try {

                        list.add(String.valueOf(categoriesModel.getCategoryName()));

                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
                try {
                    ArrayAdapter catspineradapter = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
                    binding.categoryP.setAdapter(catspineradapter);
                } catch (Exception e) {
                    e.getMessage();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "error::" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.addProductImgeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, 11);
            }
        });

        binding.addProductBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ProductName, ProductPrice, ProductDiscount, ProductDescription, ProductQuantity, ProductCategories;
                ProductName = binding.nameP.getEditText().getText().toString();
                ProductPrice = binding.priceP.getEditText().getText().toString();
                ProductDiscount = binding.discoutP.getEditText().getText().toString();
                ProductDescription = binding.descriptionP.getEditText().getText().toString();
                ProductQuantity = binding.quantityP.getEditText().getText().toString();
                ProductCategories = binding.categoryP.getSelectedItem().toString();

                if (ProductName.isEmpty() || ProductPrice.isEmpty() || ProductDiscount.isEmpty() || ProductQuantity.isEmpty() || ProductDescription.isEmpty()) {
                    Toast.makeText(getContext(), "Fill the fields", Toast.LENGTH_SHORT).show();
                } else if (ProductCategories.equals("Select Categories")) {
                    Toast.makeText(getContext(), "Please select categoies  ", Toast.LENGTH_SHORT).show();
                } else {

                    AddtoFirebase(ProductName, ProductPrice, ProductCategories, ProductDescription, ProductQuantity, ProductCategories, ProductDiscount);
                    dialog.show();
                }


            }
        });

        return binding.getRoot();
    }

    private void AddtoFirebase(String productName, String productPrice, String productCategories, String productDescription, String productQuantity, String productCategories1, String productDiscount) {
        storageReference = FirebaseStorage.getInstance().getReference("Product12" + new Random().nextInt(500));
        storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {


                    long price = Long.parseLong(productPrice);
                    long discout = Long.parseLong(productDiscount);
                    long total = 100 - discout;
                    long Dp = (total * price) / 100;
                    String ProductDiscoutPrice = String.valueOf(Dp);

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String PUid = reference.push().getKey();
                            String imageuri=uri.toString();
                            ProductsModel productsModel = new ProductsModel(PUid, productName, ProductDiscoutPrice, productPrice, productDiscount, productCategories, productQuantity, productDescription,imageuri);
                            reference.child("PRODUCTS").child(PUid).setValue(productsModel);
                            dialog.dismiss();
                        }
                    });
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "error::" + e.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 @Nullable Intent data) {
        if (requestCode == 11 && resultCode == RESULT_OK) {
            uri = data.getData();
            try {
                InputStream stream = getContext().getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(stream);
                binding.addProductImgeId.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}