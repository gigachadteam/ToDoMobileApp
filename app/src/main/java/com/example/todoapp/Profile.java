package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        // Bottom nav code
        BottomNavigationView nav = findViewById(R.id.nav1);
        nav.getMenu().findItem(R.id.profile).setChecked(true);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.home){
                    Intent intent = new Intent(Profile.this, Home.class);
                    startActivity(intent);
                }
                else if (id == R.id.tasks){
                    Intent intent = new Intent(Profile.this, MainActivity.class);
                    startActivity(intent);
                }
                else if (id == R.id.profile){
                    Intent intent = new Intent(Profile.this, Profile.class);
                    startActivity(intent);
                }

                return true;
            }
        });
    }
}