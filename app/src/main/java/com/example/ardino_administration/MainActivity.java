package com.example.ardino_administration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    HomeFragment homeFragment = new HomeFragment();
    AgenceFragment agenceFragment = new AgenceFragment();
    SettingsFragment settingsFragment = new SettingsFragment();
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().attach(agenceFragment).commit();
        getSupportFragmentManager().beginTransaction().attach(settingsFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();

        bottomNavigationView.setSelectedItemId(R.id.item_home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.item_home:
                        fragment = homeFragment;
                        break;
                    case R.id.item_agence:
                        fragment = agenceFragment;
                        break;
                    case R.id.item_settings:
                        fragment = settingsFragment;
                        break;

                }
                if (item.isChecked()){return false;}
                else {
                    if (fragment != null)
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , fragment).commit();
                }
                return true;
            }
        });


    }
}