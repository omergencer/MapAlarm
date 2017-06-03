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
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import android.location.Address;
import java.util.List;
import android.location.Geocoder;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class FavoriMapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private boolean control = true;
    private GoogleMap mMap;
    private Location mLocation = null;
    public LatLng target = null;
    public LatLng target2 = null;
    private Marker targetMarker;
    private Marker myMarker;
    private LocationManager locationManager;
    private LocationListener locationListener;
    public Circle circle;
    private int distance =2500;
    private int vibratetime = 500;
    private boolean vibration = true;
    private boolean ringoption = true;
    String value;

    private SeekBar seekbarDistance;
    private TextView tvDistance;
    private SeekBar seekbarVibrationTime;
    private TextView tvVibrationTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SaveMapLocationActivity as=new SaveMapLocationActivity();
        Bundle extras = getIntent().getExtras();
        value = extras.getString("HOME");
        Log.d("Deneme","MapsAcTIVTY");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

       // Bundle extra = getIntent().getExtras();
       // distance = extra.getInt("DISTANCE");
       // vibratetime = extra.getInt("VIBRATETIME");
        //vibration = extra.getBoolean("VIBRATION");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);





        Log.d("Deneme","IKINCI ASAMA");
        if(value.matches("ev")){
            Log.d("Deneme","IKINCI ASAMA  IF EV");
            //targetMarker.setPosition(as.favHome);
            LatLng tempEv=as.favHome;
            target2 = tempEv;

            Log.d("Deneme","IKINCI ASAMA  IF EV son");
        }
        else if(value.matches("is")){
            Log.d("Deneme","IKINCI ASAMA  IF IS");
            LatLng tempEv=as.favWork;
            target2 = tempEv;;
        }
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mLocation = location;
                Log.d("Deneme", mLocation.toString());

                SystemClock.sleep(1000);

                if(target2 != null)
                {
                    if (distance(mLocation.getLatitude(), mLocation.getLongitude(), target2.latitude, target2.longitude) < distance) {
                        Log.d("Deneme", "tırrrr");
                        while(control) {

                            Intent intent = new Intent(FavoriMapsActivity.this, AlarmActivity.class);
                            intent.putExtra("VIBRATIONTIME", vibratetime);
                            intent.putExtra("VIBRATIONOPTION", true);
                            intent.putExtra("RINGOPTIONN", true);
                            control = false;
                            startActivity(intent);

                        }
                    }
                    else
                    {
                        Log.d("Deneme" , "TIRRIRIRIRIRI");
                    }
                }
                else
                {
                    Log.d("Deneme", "Target Bos");
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

//        LatLng IEU = new LatLng(mLocation.getLatitude(),mLocation.getLongitude());
        LatLng IEU = new LatLng(38.388131230829714,27.045403160154816);
        CameraUpdate center = CameraUpdateFactory.newLatLng(IEU);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        Log.d("Deneme", "MARKER FAVORI");

        targetMarker = mMap.addMarker(new MarkerOptions().position(target2).title("Target"));
        circle = mMap.addCircle(new CircleOptions().center(target2).radius(distance).strokeColor(Color.RED).fillColor(Color.argb(4,125,58,65)));
        /*

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

                targetMarker = mMap.addMarker(new MarkerOptions().position(target).title("Target"));
                circle = mMap.addCircle(new CircleOptions().center(target).radius(distance).strokeColor(Color.RED).fillColor(Color.argb(4,125,58,65)));
            }
        });
*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }


    }
    public void onMapSearch(View view) {
        EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        List<Address>addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

            Log.d("Deneme", latLng.toString());
/*
            target = latLng;
            if(targetMarker != null)
            {
                targetMarker.remove();
                targetMarker = null;
              //  circle.remove();
            }

            targetMarker = mMap.addMarker(new MarkerOptions().position(target).title("Target"));
            circle = mMap.addCircle(new CircleOptions().center(target).radius(distance).strokeColor(Color.RED).fillColor(Color.argb(4,125,58,65)));

            CameraUpdate center = CameraUpdateFactory.newLatLng(target);
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);

            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
*/ //en son
            //mMap.animateCamera(CameraUpdateFactory.newLatLng(target));
            //CameraUpdate center = CameraUpdateFactory.newLatLng(target);
            // CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);
//            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
//            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }


    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 3958.75 6371 for kilometer output

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


