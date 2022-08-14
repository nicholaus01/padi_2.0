package com.creeds_code.padi_20;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.creeds_code.padi_20.databinding.ActivityMainBinding;
import com.creeds_code.padi_20.databinding.ContentMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding binding;
    FirebaseAuth mAuth;
    FirebaseUser user;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        mAuth = FirebaseAuth.getInstance();
        setNavigationDrawer();
        displayDefaultPage();
        onFabClicked();
    }

    private void onFabClicked() {
        binding.contentMain.createNewScheduleFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ScheduleActivity.class);
                startActivity(intent);
            }
        });
    }

    private void displayDefaultPage(){
        navigationView.getMenu().findItem(R.id.action_schedule).setChecked(true);
        recyclerView = binding.contentMain.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ScheduleRecyclerAdapter(this,DataManager.schedules));
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = mAuth.getCurrentUser();
//        if (user == null) {
//            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//        }
    }

    public void setNavigationDrawer() {
        navigationView = binding.navView;
        drawerLayout = binding.drawerLayout;
        //add drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //navigation view
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Handle navigation view item clicks


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        builder.setTitle(R.string.logout_dialog_title)
                .setMessage(R.string.logout_dialog_message)
                .setIcon(R.drawable.ic_action_signout)
                .setPositiveButton(R.string.action_ok_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        logoutUser();
                    }
                })
                .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //SYSTEM DISMISSES DIALOG
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void logoutUser() {
        mAuth.signOut();
        Toast.makeText(MainActivity.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}