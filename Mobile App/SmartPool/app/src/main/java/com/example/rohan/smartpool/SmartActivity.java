package com.example.rohan.smartpool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import static com.example.rohan.smartpool.R.id.btn_test;
import static com.example.rohan.smartpool.R.id.start;

public class SmartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String MyPREFERENCES = "Preferences";
    public static final String logIn = "loggedInKey";
    SharedPreferences sharedPreferences;
    Button joinRide,offerRide,test;
    RelativeLayout mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        joinRide = (Button) findViewById(R.id.btn_join);
        mainLayout = (RelativeLayout) findViewById(R.id.main);
        offerRide = (Button) findViewById(R.id.btn_offer);
        test = (Button) findViewById(btn_test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SmartActivity.this,ListRoutesActivity.class));
            }
        });
        //mainLayout.setBackground();

        sharedPreferences =  getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        boolean loggedIn = sharedPreferences.getBoolean(logIn,false);
        Log.e("LoggedIn",String.valueOf(loggedIn));
        if(!loggedIn) {
            Intent intent = new Intent(SmartActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        joinRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SmartActivity.this,JoinActivity.class));
            }
        });
        offerRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SmartActivity.this,OfferActivity.class));
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.smart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
        } else if (id == R.id.nav_offer) {
            startActivity(new Intent(SmartActivity.this,OfferActivity.class));

        } else if (id == R.id.nav_join) {
            startActivity(new Intent(SmartActivity.this,JoinActivity.class));

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
