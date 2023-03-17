package com.example.ecartadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ecartadmin.databinding.ActivityAuthBinding;
import com.example.ecartadmin.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity {


    FirebaseAuth auth;
    ActivityAuthBinding binding;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();

         sharedPreferences = getSharedPreferences("Auth", MODE_PRIVATE);
        boolean ad = sharedPreferences.getBoolean("flag", false);
        if (ad) {
            startActivity(new Intent(AuthActivity.this, MainActivity.class));
            finish();
        } else adminlogin();

//


    }

    private void adminlogin() {
    binding.adminLoginBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            auth.signInWithEmailAndPassword(binding.emailAdmin.getEditText().getText().toString(), binding.passwordAdming.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(AuthActivity.this, "login done", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putBoolean("flag",true);
                        editor.apply();
                        startActivity(new Intent(AuthActivity.this, MainActivity.class));

                        finish();

                    } else {
                        Toast.makeText(AuthActivity.this, "error::" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    });

    }
}