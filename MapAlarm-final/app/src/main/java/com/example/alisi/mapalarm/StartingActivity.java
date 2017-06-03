package com.example.alisi.mapalarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class StartingActivity extends AppCompatActivity {

    private Button selectNewLocation;
    private Button saveNewLocation;
    private Button SavedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        selectNewLocation = (Button) findViewById(R.id.SelectNewLocation);
        selectNewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Deneme","selectnewlocation");
                Intent intent = new Intent(StartingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        saveNewLocation = (Button) findViewById(R.id.SaveNewLocation);
        saveNewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Deneme","savenewlocation");
                //Intent intent = new Intent(StartingActivity.this, SaveMapLocationActivity.class);
                Intent intent1 = new Intent(StartingActivity.this, FavActivty.class);
                startActivity(intent1);
            }
        });

        SavedLocation = (Button) findViewById(R.id.SavedLocation);
        SavedLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Deneme","SavedLocation");

                Intent intent2 = new Intent(StartingActivity.this, SavedFavorities.class);
                startActivity(intent2);
            }
        });

    }
}
