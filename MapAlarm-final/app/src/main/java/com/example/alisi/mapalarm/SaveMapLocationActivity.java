package com.example.alisi.mapalarm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class SaveMapLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private  int progressChanged ;
    private  int vibrationTime = 500;
    private SeekBar seekbarDistance;//en son
    private TextView tvDistance;
    private SeekBar seekbarVibrationTime;
    private TextView tvVibrationTime;
    private Button btnSaveThisLocation;
    private Toast toast = null;
    public static LatLng favHome;
    public static LatLng favWork;
    private Circle myCircle;
    private LatLng myTarget;
    private LatLng myTarget2;
    private Marker myTargetMarker;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();
        value = extras.getString("HOME");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_map_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tvDistance = (TextView) findViewById(R.id.tvDistance);
        seekbarDistance = (SeekBar) findViewById(R.id.seekbarDistance);
        seekbarDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = progress + 1000;
                tvDistance.setText(progressChanged + " mt");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        tvVibrationTime = (TextView) findViewById(R.id.tvVibrationTime);
        seekbarVibrationTime = (SeekBar) findViewById(R.id.seekbarVibration);
        seekbarVibrationTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                vibrationTime = progress;
                tvVibrationTime.setText(vibrationTime + " sn        ");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("Deneme", "NO PERMISSION");
            return;
        }
        mMap.setMyLocationEnabled(true);

        LatLng IEU = new LatLng(38.388131230829714,27.045403160154816);
        CameraUpdate center = CameraUpdateFactory.newLatLng(IEU);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d("Deneme","onMapClick ilk" +latLng.toString());
                myTarget = latLng;

                if(myTargetMarker != null)
                {
                    myTargetMarker.remove();
                    myTargetMarker = null;
                    myCircle.remove();
                }

                myTargetMarker = mMap.addMarker(new MarkerOptions().position(myTarget).title("Target"));
                myCircle = mMap.addCircle(new CircleOptions().center(myTarget).radius(progressChanged).strokeColor(Color.RED).fillColor(Color.argb(4,125,58,65)));

            }
        });

        btnSaveThisLocation = (Button) findViewById(R.id.save_button);
        btnSaveThisLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Deneme", vibrationTime + " " + progressChanged);
                Log.d("Deneme", myTarget.latitude + " " + myTarget.longitude);
                Log.d("Deneme","match value"+value);
                if(value.matches("ev")) {
                    favHome=myTarget;
                    Log.d("Deneme","if statemet ev");
                    Intent intent = new Intent(SaveMapLocationActivity.this, StartingActivity.class);
                    //Intent intent1 = new Intent(SaveMapLocationActivity.this, SavedFavorities.class);

                   // intent1.putExtra("ev",favHome.toString();
                    //intent1.putExtra("ev_long",favHome.longitude);
                    startActivity(intent);

                    if(toast != null)
                    {
                        toast.cancel();
                    }
                    toast = Toast.makeText(SaveMapLocationActivity.this,"Saved Location", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(value.matches("is")) {
                    favWork=myTarget;
                    Log.d("Deneme","if statemet is");
                    Intent intent = new Intent(SaveMapLocationActivity.this, StartingActivity.class);
                   // Intent intent1 = new Intent(SaveMapLocationActivity.this, SavedFavorities.class);


                    //intent1.putExtra("is_lat",favHome.latitude);
                   // intent1.putExtra("is_long",favHome.longitude);
                    startActivity(intent);

                    if(toast != null)
                    {
                        toast.cancel();
                    }
                    toast = Toast.makeText(SaveMapLocationActivity.this,"Saved Location", Toast.LENGTH_SHORT);
                    toast.show();
                }




            }
        });
    }

    public void onMapSearch(View view) {
        EditText locationSearch = (EditText) findViewById(R.id.tvSearch);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

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

            myTarget = latLng;
            if(myTargetMarker != null)
            {
                myTargetMarker.remove();
                myTargetMarker = null;
                myCircle.remove();
            }

            myTargetMarker = mMap.addMarker(new MarkerOptions().position(myTarget).title("Target"));
            myCircle = mMap.addCircle(new CircleOptions().center(myTarget).radius(progressChanged).strokeColor(Color.RED).fillColor(Color.argb(4,125,58,65)));

            CameraUpdate center = CameraUpdateFactory.newLatLng(myTarget);
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);

            mMap.moveCamera(center);
            mMap.animateCamera(zoom);

            //mMap.animateCamera(CameraUpdateFactory.newLatLng(target));
            //CameraUpdate center = CameraUpdateFactory.newLatLng(target);
            // CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);
//            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
//            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }
}
