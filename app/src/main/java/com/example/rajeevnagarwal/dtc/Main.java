package com.example.rajeevnagarwal.dtc;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.provider.Settings.Secure;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Main extends AppCompatActivity
    
        implements Payment.OnFragmentInteractionListener, Destination.OnFragmentInteractionListener, Map.OnFragmentInteractionListener, Recommendation.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener , inter_Dest_map{

    static String mobileNumber;
    static String androidId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        mobileNumber = tm.getLine1Number();

        androidId = Secure.getString(getContentResolver(),Secure.ANDROID_ID);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Main.setDefaultFont(this,"MONOSPACE","fonts/IndieFlower.ttf");

    }

    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }
    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);

            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_payment) {
            Payment obj = new Payment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content,obj).commit();
            // Handle the camera action
        } else if (id == R.id.nav_destination) {
            Destination obj = new Destination();
            getSupportFragmentManager().beginTransaction().replace(R.id.content,obj).commit();

        } else if (id == R.id.nav_recommendation) {
            Recommendation obj = new Recommendation();
            getSupportFragmentManager().beginTransaction().replace(R.id.content,obj).commit();

        } else if (id == R.id.nav_map) {
            Map obj = new Map();
            getSupportFragmentManager().beginTransaction().replace(R.id.content,obj).commit();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {



    }

    @Override
    public void fragmentInteraction(HashMap<String, LatLng> pos) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("destination",pos.get("destination"));
        bundle.putString("temp","fuck you");

        Map obj = new Map();
        obj.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.content,obj).commit();

    }
}
