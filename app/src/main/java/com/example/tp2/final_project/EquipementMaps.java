package com.example.tp2.final_project;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.tp2.final_project.databinding.ActivityEquipementMapsBinding;

import java.util.ArrayList;

public class EquipementMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityEquipementMapsBinding binding;

    protected ArrayList<Object> markers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEquipementMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        markers = (ArrayList<Object>) getIntent().getSerializableExtra("marker");
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


        for(int i = 0;i<markers.size();i+=3){
            Double lat = (Double) markers.get(i);
            Double lng = (Double)  markers.get(i+1);
            LatLng equipement = new LatLng(lat,lng);
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.stadium_icon)).position(equipement).title((String)markers.get(i+2)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(equipement));
            LatLng latLng = new LatLng(lat, lng);
        }

        if(markers.size() <4) {
            LatLng point = new LatLng((double)markers.get(0),(double)markers.get(1));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(point) // Center Set
                    .zoom(11.0f)                // Zoom
                    .bearing(90)                // Orientation of the camera to east
                    .tilt(30)                   // Tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }else{
            LatLng Paris = new LatLng(48,2);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(Paris) // Center Set
                    .zoom(6f)                // Zoom
                    .bearing(0)                // Orientation of the camera to east
                    .tilt(0)                   // Tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }
}