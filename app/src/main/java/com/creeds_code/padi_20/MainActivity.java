package com.creeds_code.padi_20;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.creeds_code.padi_20.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        binding.signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }

//    public void setNavigationDrawer(){}
//    //add drawer toggle
//    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar,R.string.openDrawer,R.string.closeDrawer){
//        @Override
//        public void onDrawerOpened(View drawerView) {
//            super.onDrawerOpened(drawerView);
//        }
//
//        @Override
//        public void onDrawerClosed(View drawerView) {
//            super.onDrawerClosed(drawerView);
//        }
//    };
//    binding.drawerLayout.setDrawerListener(toggle);
//    toggle.syncState();
//    //nav view listener
//    binding.navView.setNavigationItemSelectedListener()


}