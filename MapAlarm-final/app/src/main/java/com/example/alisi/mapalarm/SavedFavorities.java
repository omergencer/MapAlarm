package com.example.alisi.mapalarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

public class SavedFavorities extends AppCompatActivity {
    public static LatLng EvKonumu,IsKonumu;
    private Button favWork;
    private Button favHome;
    public SavedFavorities(){}
    public String ev="ev";
    public String is="is";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_favorities);
        Log.d("Deneme","SavedFavorities");
        Bundle extras =getIntent().getExtras();
        SaveMapLocationActivity saved =new SaveMapLocationActivity();
        EvKonumu=saved.favHome;
        IsKonumu=saved.favWork;
        Log.d("Deneme","ev konumu"+saved.favHome);
        Log.d("Deneme","is konumu"+saved.favWork);

        favHome = (Button) findViewById(R.id.favHome);
        favHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Deneme","Favori ev dugmesi");
                Intent intent = new Intent(getApplicationContext(), FavoriMapsActivity.class);
                intent.putExtra("HOME",ev);
                startActivity(intent);
            }
        });

        favWork = (Button) findViewById(R.id.favWork);
        favWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Deneme","Favori is dugmesi");
                Intent intent = new Intent(getApplicationContext(), FavoriMapsActivity.class);
                intent.putExtra("HOME",is);
                startActivity(intent);
            }
        });

    }
}
