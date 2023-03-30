package com.example.ecart.Authentication;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecart.ModelClass.UserModel;
import com.example.ecart.R;
import com.example.ecart.databinding.FragmentSignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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

    FragmentSignupBinding binding;
    String Name, Email, Phone, Password;
    FirebaseAuth auth;
    DatabaseReference reference;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("USERS");
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.loadinglayout);
        dialog.setCancelable(false);

        binding.ToLoginF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.Auth_fremelayout_id, new LoginFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = binding.nameSignup.getEditText().getText().toString();
                Email = binding.emailSignup.getEditText().getText().toString();
                Password = binding.passwordSignup.getEditText().getText().toString();
                Phone = binding.phoneSignup.getEditText().getText().toString();
                if (Email.isEmpty() || Password.isEmpty() || Phone.isEmpty() || Phone.isEmpty()) {
                    Toast.makeText(getContext(), "Fill the filds", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    Toast.makeText(getContext(), "Enter correct Email ", Toast.LENGTH_SHORT).show();
                } else if (Password.length() < 8) {
                    Toast.makeText(getContext(), "User 8 character or more ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "fill", Toast.LENGTH_SHORT).show();

                    CheckEmail(Email, Password, Phone, Name);
                    dialog.show();
                }


            }
        });


        return binding.getRoot();
    }

    private void CheckEmail(String email, String password, String phone, String name) {
        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                boolean c = task.getResult().getSignInMethods().isEmpty();
                if (c) {

                    CreateUserithFirebaser(email, password,phone,name);
                    dialog.dismiss();

                } else {
                    Toast.makeText(getContext(), "This Email is already use try another", Toast.LENGTH_SHORT).show();
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

    private void CreateUserithFirebaser(String email, String password, String phone, String name) {

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(getContext(), "signup succeed ", Toast.LENGTH_SHORT).show();
                    String UID=task.getResult().getUser().getUid();
                    UserModel userModel=new UserModel(email,password,name,phone,UID);
                    reference.child(UID).setValue(userModel);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.Auth_fremelayout_id, new LoginFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else
                {
                    Toast.makeText(getContext(), "Signup failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "error::"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}