package com.example.ecart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;


import com.example.ecart.Fragment.CategoriesFragment;
import com.example.ecart.Fragment.HomeFragment;
import com.example.ecart.Fragment.LikeFragment;
import com.example.ecart.Fragment.ProfileFragment;
import com.example.ecart.R;
import com.example.ecart.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.home_framelayout_id,new HomeFragment()).commit();
        binding.homeBottomNavigationId.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.home_botton_m_id:
                        fragment = new HomeFragment();
                        break;
                    case R.id.categories_bottom_m_id:
                        fragment = new CategoriesFragment();
                        break;
                    case R.id.like_bottom_m_id:
                        fragment = new LikeFragment();
                        break;
                    case R.id.profile_bottom_m_id:
                        fragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.home_framelayout_id,fragment).commit();
                return true;
            }
        });


    }
}