package com.example.ecart.Authentication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.ecart.Activity.HomeActivity;
import com.example.ecart.R;
import com.example.ecart.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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

    FragmentLoginBinding binding;
    FirebaseAuth auth;
    String Email, Password;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        auth = FirebaseAuth.getInstance();
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.loadinglayout);
        dialog.setCancelable(false);

        binding.ToSignupF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.Auth_fremelayout_id, new SignupFragment());
                fragmentTransaction.addToBackStack(String.valueOf(new LoginFragment()));
                fragmentTransaction.commit();
            }
        });
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Email = binding.emailLogin.getEditText().getText().toString();
                Password = binding.passwordLogin.getEditText().getText().toString();
                if (Email.isEmpty() || Password.isEmpty()) {
                    Toast.makeText(getContext(), "Fill the filds", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    Toast.makeText(getContext(), "Enter correct Email ", Toast.LENGTH_SHORT).show();
                } else if (Password.length() < 8) {
                    Toast.makeText(getContext(), "User 8 character or more ", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(getContext(), "fill", Toast.LENGTH_SHORT).show();

                    CheckEmail(Email, Password);
                    dialog.show();
                }

            }
        });

        return binding.getRoot();
    }

    private void CheckEmail(String email, String password) {
        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                boolean c = task.getResult().getSignInMethods().isEmpty();
                if (c) {
                    Toast.makeText(getContext(), "please create account for this Email", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.Auth_fremelayout_id, new SignupFragment());
                    fragmentTransaction.addToBackStack(String.valueOf(new LoginFragment()));
                    fragmentTransaction.commit();

                    dialog.dismiss();

                } else {
                    Toast.makeText(getContext(), "old user", Toast.LENGTH_SHORT).show();
                    SignwithFirebaser(email, password);
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

    private void SignwithFirebaser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    SharedPreferences pre = getActivity().getSharedPreferences("auth", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pre.edit();
                    editor.putBoolean("flag", true);
                    editor.commit();
                    startActivity(new Intent(getContext(), HomeActivity.class));

                    Toast.makeText(getContext(), "Login success", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Login failed", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
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
}