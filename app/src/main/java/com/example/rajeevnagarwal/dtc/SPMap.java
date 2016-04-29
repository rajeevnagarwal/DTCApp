package com.example.rajeevnagarwal.dtc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class SPMap extends AppCompatActivity {

    public TextView txt;
    MapFragment googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spmap);
        Intent i = getIntent();
        String from = (i.getExtras().getString("From").toString());
        String to = (i.getExtras().getString("To").toString());
        Network Z = new Network();
        ArrayList<Node> busstops=new ArrayList<Node>();
        Node a = new Node("a Govind Puri",28.545108,77.263953);
        busstops.add(a);
        Node b = new Node("a Kalkaji Depot",28.539430,77.266187);
        busstops.add(b);
        Node c = new Node("a C Lal Chowk",28.534574,77.268152);
        busstops.add(c);
        Node d = new Node("b Okhla Industrial Area",28.533135,77.269067);
        busstops.add(d);
        Node f = new Node("b C Lal Chowk",28.534001,77.268680);
        busstops.add(f);
        Node g = new Node("b Kalkaji Depot",28.537976,77.267588);
        busstops.add(g);
        Node h = new Node("b Govind Puri",28.545756,77.263979);
        busstops.add(h);
        Distance atoh = new Distance(89.74,"m");
        Z.insertlink(a,h,atoh);
        Distance btoa = new Distance(656.63,"m");
        Z.insertlink(b,a,btoa);
        Distance htog = new Distance(935.18,"m");
        Z.insertlink(h,g,htog);
        Distance gtof = new Distance(454.27,"m");
        Z.insertlink(g,f,gtof);
        Distance dtoc=new Distance(192.14,"m");
        Z.insertlink(d,c,dtoc);
        Distance ftod=new Distance(92.97,"m");
        Z.insertlink(f,d,ftod);
        int count=0;
        Node x,y;
        x = new Node();
        y = new Node();
        for(int k = 0;k<Z.getStation().size();k++) {
            if (from.equals(Z.getStation().get(k).getName()) && to.equals(Z.getStation().get(k).getName())) {
                x = Z.getStation().get(k);
                y = Z.getStation().get(k);
            }
            if (from.equals(Z.getStation().get(k).getName())) {
                x = Z.getStation().get(k);

            } else if (to.equals(Z.getStation().get(k).getName())) {
                y = Z.getStation().get(k);
            }
        }

        final ArrayList<Node> S = Z.sp(x, y);

        for(int j=0;j<S.size();j++)
            System.out.println(S.get(j).getName());
        try
        {
            if(googleMap==null)
            {
                googleMap=(MapFragment)getFragmentManager().findFragmentById(R.id.map);
                googleMap.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                       for(int j=0;j<S.size();j++)
                       {


                          googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)).position(new LatLng(S.get(j).getLocation().getLatitude(), S.get(j).getLocation().getLongitude())).title(S.get(j).getName()));
                          googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(S.get(j).getLocation().getLatitude(), S.get(j).getLocation().getLongitude()), 20));

                          if(j<S.size()-1)
                           googleMap.addPolyline(new PolylineOptions().add(new LatLng(S.get(j).getLocation().getLatitude(), S.get(j).getLocation().getLongitude()),new LatLng(S.get(j+1).getLocation().getLatitude(), S.get(j+1).getLocation().getLongitude())).width(10).color(Color.RED).geodesic(true));
                       }




                    }
                });

            }
        }
        catch(SecurityException e)
        {
            System.out.println("hello1");
        }




    }

}
