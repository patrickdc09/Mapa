package com.example.patrick.mapa;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private List<MarkerOptions> markerList;

    private PolylineOptions polylineOptions;

    private LocationManager mLocationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        markerList = new ArrayList<>();
        polylineOptions = new PolylineOptions();

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

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000,
                50,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        LatLng latLng
                                = new LatLng(location.getLatitude(),
                                location.getLongitude());
                        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                        markerList.add(markerOptions);
                        mMap.addMarker(markerOptions);
                        mMap.addPolyline(polylineOptions);
                        mMap.addPolyline(polylineOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                }

        );

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()

                                   {

                                       @Override
                                       public void onMapClick(LatLng latLng) {
                                           {
                                               MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                                               markerList.add(markerOptions);
                                               polylineOptions.add(latLng);
                                               mMap.addMarker(markerOptions);
                                               mMap.addPolyline(polylineOptions);
                                           }
                                       }
                                   }

        );
    }
}
