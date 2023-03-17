package com.example.ecart.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.ecart.Activity.HomeActivity;
import com.example.ecart.R;

public class AuthAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        SharedPreferences sharedPreferences=getSharedPreferences("auth",MODE_PRIVATE);

        boolean check=sharedPreferences.getBoolean("flag",false);

        if (check)
        {
            startActivity(new Intent(AuthAcitivity.this, HomeActivity.class));
            finish();
        }
        else
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.Auth_fremelayout_id ,new LoginFragment()).commit();
        }


    }
}