package com.example.ecartadmin.AddFragment;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecartadmin.Model.CategoriesModel;
import com.example.ecartadmin.R;
import com.example.ecartadmin.databinding.FragmentAddCategoriesBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCategoriesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddCategoriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCategoriesFragment newInstance(String param1, String param2) {
        AddCategoriesFragment fragment = new AddCategoriesFragment();
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

    FragmentAddCategoriesBinding binding;
    Uri uri;
    Bitmap bitmap;
    DatabaseReference reference;
    StorageReference storageReference;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddCategoriesBinding.inflate(inflater, container, false);
        reference = FirebaseDatabase.getInstance().getReference("CATEGORIES");
        storageReference = FirebaseStorage.getInstance().getReference("Image1" + new Random().nextInt(25));
        PickImageClass();
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.loadinglayout);
        dialog.setCancelable(false);
        binding.addCateBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CategoiesName = binding.addCateEdDi.getText().toString();
                if (CategoiesName.isEmpty()) {
                    Toast.makeText(getContext(), "Fill the fields", Toast.LENGTH_SHORT).show();
                } else if (uri == null) {
                    Toast.makeText(getContext(), "please select image", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "fill", Toast.LENGTH_SHORT).show();
                    ImageStoreInFirebase(CategoiesName);
                    dialog.show();

                }


            }
        });


        return binding.getRoot();
    }

    private void ImageStoreInFirebase(String categoiesName) {

        storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String uid = reference.push().getKey().toString();
                            CategoriesModel categoriesModel = new CategoriesModel(categoiesName, uri.toString(), uid);
                            reference.child(uid).setValue(categoriesModel);
                            dialog.dismiss();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "error::" + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void PickImageClass() {

        binding.addCateImgvId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "pick image", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, 99);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 @Nullable Intent data) {
        if (requestCode == 99 && resultCode == RESULT_OK) {
            uri = data.getData();
            try {
                InputStream stream = getContext().getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(stream);
                binding.addCateImgvId.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
