package com.example.alisi.mapalarm;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
private int i=1;
    private GoogleMap mMap;
    private LatLng latLng;
    private Location mLocation = null;
    public LatLng target;
    private Marker targetMarker;
    private LocationManager locationManager;
    private LocationListener locationListener;
    public Circle circle;
    private int distance = 0;
    private int vibratetime = 500;
    private boolean vibration = true;
    private boolean ringoption = true;
    private String alarmname;
    private String alarmmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle extra = getIntent().getExtras();
        distance = extra.getInt("DISTANCE");
        vibratetime = extra.getInt("VIBRATETIME");
        vibration = extra.getBoolean("VIBRATION");
        alarmname = extra.getString("ALARMNAME");
        alarmmessage = extra.getString("ALARMMESSAGE");
        ringoption = extra.getBoolean("RINGOPTION");



        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mLocation = location;
                //Vibrator vibrator = (Vibrator) MapsActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                Log.d("Deneme", mLocation.toString());

                if (distance(mLocation.getLatitude(), mLocation.getLongitude(), target.latitude, target.longitude) < distance) {

                    //vibrator.vibrate(vibratetime);
                    if(i<2){
                        Log.d("Deneme", "tırrrr");
                        Intent intent = new Intent(MapsActivity.this, AlarmActivity.class);
                        intent.putExtra("VIBRATIONTIME", vibratetime);
                        intent.putExtra("ALARMNAMEE", alarmname);
                        intent.putExtra("ALARMMESSAGEE", alarmmessage);
                        intent.putExtra("VIBRATIONOPTION", vibration);
                        intent.putExtra("RINGOPTIONN", ringoption);
                        startActivity(intent);
                        i++;
                    }

                }
                else
                {
                    Log.d("Deneme" , "TIRRIRIRIRIRI");
                }
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
        };

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, distance, 0.0f, locationListener);
        } else {
            Log.d("Deneme", "NO PERMISSION");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Deneme", "NO PERMISSION");
            return;
        }
        mMap.setMyLocationEnabled(true);
        LatLng IEU = new LatLng(38.388131230829714,27.045403160154816);
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(38.388131230829714,27.045403160154816));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);



        mMap.moveCamera(center);
        mMap.animateCamera(zoom);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d("Deneme", latLng.toString());
                target = latLng;
                if(targetMarker != null)
                {
                    targetMarker.remove();
                    targetMarker = null;
                    circle.remove();
                }

                targetMarker = mMap.addMarker(new MarkerOptions().position(target).title("You are here"));
                circle = mMap.addCircle(new CircleOptions().center(target).radius(distance).strokeColor(Color.RED).fillColor(Color.argb(4,125,58,65)));
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
    }

    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist; // output distance, in MILES
    }
}


