package com.example.ecartadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ecartadmin.AddFragment.AddCategoriesFragment;
import com.example.ecartadmin.Fragment.CategoreisFragment;
import com.example.ecartadmin.Fragment.ProductFragment;
import com.example.ecartadmin.Fragment.HomeFragment;
import com.example.ecartadmin.Fragment.SellProductFragment;
import com.example.ecartadmin.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;
    Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());




        getSupportFragmentManager().beginTransaction().replace(R.id.fremelayout_id, new HomeFragment()).commit();

        mainBinding.bottomNavId.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.home_botton_m_id:
                        fragment = new HomeFragment();
                        break;
                    case R.id.categories_bottom_m_id:
                        fragment = new CategoreisFragment();
                        break;
                    case R.id.product_bottom_m_id:
                        fragment = new ProductFragment();
                        break;
                    case R.id.sell_product_bottom_m_id:
                        fragment = new SellProductFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fremelayout_id, fragment).commit();

                return true;
            }
        });
        mainBinding.bottomNavId.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_botton_m_id:
                        fragment = new HomeFragment();
                        break;
                    case R.id.categories_bottom_m_id:
                        fragment = new CategoreisFragment();
                        break;
                    case R.id.product_bottom_m_id:
                        fragment = new ProductFragment();
                        break;
                    case R.id.sell_product_bottom_m_id:
                        fragment = new SellProductFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fremelayout_id, fragment).commit();

            }
        });


    }
}