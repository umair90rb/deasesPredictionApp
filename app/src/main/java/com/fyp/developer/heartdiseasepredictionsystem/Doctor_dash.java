package com.fyp.developer.heartdiseasepredictionsystem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class Doctor_dash extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mDrawerlayout;
    private FirebaseAuth mAuth;
    ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    Button u_h_l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dash);

        mToolbar = findViewById(R.id.Tool_bar_layout);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        //mToolbar.setOverflowIcon(getDrawable());
        getSupportActionBar().setTitle("Heart Pridiction App");



        u_h_l = findViewById(R.id.u_h_l);

        mAuth = getInstance();
        mDrawerlayout = findViewById(R.id.drawer_file);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.nav_open, R.string.nav_close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        u_h_l.setOnClickListener(this);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_file);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.e_p_detail) {
            Intent intent = new Intent(Doctor_dash.this, Doctor.class);
            startActivity(intent);

            return true;
        }
        if (id == R.id.signout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Doctor_dash.this, login_screen.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.location) {
            Intent intent = new Intent(Doctor_dash.this,MapsActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.help) {
            Intent intent = new Intent(Doctor_dash.this,help.class);
            startActivity(intent);
            return true;
        }


        DrawerLayout mDrawerlayout = findViewById(R.id.drawer_file);
        mDrawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {


        if(v.getId() == R.id.u_h_l){
            startActivity(new Intent(Doctor_dash.this, MapsActivity.class));
        }

    }
    }

