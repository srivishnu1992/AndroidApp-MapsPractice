package com.example.srivi.mapspractice;


import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapLoadedCallback {

    private GoogleMap mMap;
    ArrayList<Path.points> points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_maps );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );
        Gson gson = new Gson();
        InputStream raw = getResources().openRawResource( R.raw.trip );
        Reader reader = new BufferedReader( new InputStreamReader( raw  ));
        Path path = gson.fromJson( reader, Path.class );
        Log.d("tag", path.toString());
        points = path.getPoints();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        int n = points.size();
        // Add a marker in Sydney and move the camera
        LatLng start = new LatLng( points.get( 0 ).latitude, points.get( 0 ).longitude);
        mMap.addMarker( new MarkerOptions().position( start).title( "Starting " ) );
        LatLng end= new LatLng( points.get( n-1 ).latitude, points.get( n-1 ).longitude);
        mMap.addMarker( new MarkerOptions().position( end).title( "Ending " ) );

        PolylineOptions polylineOptions = new PolylineOptions();
        for(int i=0;i<points.size();i++) {
            polylineOptions.add( new LatLng( points.get( i ).latitude, points.get( i ).longitude ) );
        }
        Polyline polyline = mMap.addPolyline( polylineOptions );
        polyline.setColor( Color.BLUE );
        mMap.setOnMapLoadedCallback( this );

    }

    @Override
    public void onMapLoaded() {
        LatLngBounds.Builder builder=new LatLngBounds.Builder();
        for(int i=0;i<points.size();i++)
            builder.include( (new LatLng( points.get( i ).latitude, points.get( i ).longitude ) ));
        LatLngBounds latLngBounds = builder.build();
        CameraUpdate cobj = CameraUpdateFactory.newLatLngBounds( latLngBounds, 11 );
        mMap.animateCamera( cobj );
    }
}
