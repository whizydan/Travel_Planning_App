package com.kerberos.travel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.kerberos.travel.fragments.DealsFragment;
import com.kerberos.travel.fragments.HomeFragment;
import com.kerberos.travel.fragments.PlansFragment;
import com.kerberos.travel.fragments.ProfileFragment;
import com.kerberos.travel.pages.LoginActivity;
import com.kerberos.travel.pages.RegisterActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Fragment> allFragments = new ArrayList<>();
    Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allFragments.add(new HomeFragment());
        allFragments.add(new DealsFragment());
        allFragments.add(new PlansFragment());
        allFragments.add(new ProfileFragment());
        activeFragment = allFragments.get(0);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        BottomNavigationView navBar = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder, activeFragment).commit();

        navBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment current = activeFragment;
                menuItem.setChecked(true);

                if(menuItem.getItemId() == R.id.home){
                    activeFragment = new HomeFragment();
                }else if(menuItem.getItemId() == R.id.deals){
                    activeFragment = new DealsFragment();
                }else if(menuItem.getItemId() == R.id.plans){
                    activeFragment = new PlansFragment();
                }else if(menuItem.getItemId() == R.id.profile){
                    activeFragment = new ProfileFragment();
                }

                if(activeFragment != current){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder, activeFragment).commit();
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}