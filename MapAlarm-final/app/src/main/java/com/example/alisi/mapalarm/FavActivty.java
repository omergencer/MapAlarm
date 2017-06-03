package com.example.alisi.mapalarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FavActivty extends AppCompatActivity {
    private Button favHome;
    private Button favWork;
    private  String ev="ev";
    private  String is="is";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_activty);


        favHome = (Button) findViewById(R.id.favHome);
        favHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Deneme","favorite home");
                Intent intent = new Intent(getApplicationContext(), SaveMapLocationActivity.class);
                intent.putExtra("HOME",ev);
                startActivity(intent);
            }
        });


        favWork = (Button) findViewById(R.id.favWork);
        favWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Deneme","favorite work");
                Intent intent = new Intent(FavActivty.this, SaveMapLocationActivity.class);
                intent.putExtra("HOME",is);
                startActivity(intent);
            }
        });
    }

}
