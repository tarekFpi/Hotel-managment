package com.example.hotel_managment;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.location.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class Maps_restaurant extends FragmentActivity {
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location currenet_Location;
    private SupportMapFragment supportMapFragment;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static int request_cord=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_restaurant);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_id);
        //mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrent_location();
        } else {
          //  ActivityCompat.requestPermissions(Maps_restaurant.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                            !=PackageManager.PERMISSION_GRANTED){
                return;
            }
        }
    }

    public void getCurrent_location() {


        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
          @Override
          public void onSuccess(final Location location) {
         if(location !=null){
             supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                 @Override
                 public void onMapReady(GoogleMap googleMap) {
                 LatLng myLocation=new LatLng(location.getLatitude(),location.getLongitude());

                 MarkerOptions markerOptions =new MarkerOptions().position(myLocation).title("Marker");
              // googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                 googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,18));
                 googleMap.addMarker(markerOptions);
                 }
             });
          }
          }
      });

  }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
          super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==request_cord){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getCurrent_location();
            }
        }

    }

    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    locationManager= (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    locationListener=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

          // Add a marker in Sydney and move the camera
            LatLng log = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(log).title("Marker in Currnet"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(log));
            mMap.setMinZoomPreference(18);
        }
    };

    if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
          !=PackageManager.PERMISSION_GRANTED){
        return;
        }else{
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                // Add a marker in Sydney and move the camera
                LatLng log = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(log).title("Marker in Currnet"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(log));
            }
        };
    }

    }*/
}