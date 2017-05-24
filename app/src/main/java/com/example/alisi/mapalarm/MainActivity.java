package com.example.alisi.mapalarm;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SeekBar distanceControl = null;
    private TextView distance = null;
    private CheckBox vibrate = null;
    private CheckBox ringg = null;
    private Toast toast = null;
    private Button btnMap = null;
    private SeekBar vibraterange = null;
    private TextView tvVibrateTime = null;
    private Button btnSave = null;

    public int progressChanged = 0;
    public boolean vibration = true;
    public boolean ring = true;
    public int vibrateTime = 500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        distanceControl = (SeekBar) findViewById(R.id.seekBar2);
        distance = (TextView) findViewById(R.id.tvDistance);
        vibrate = (CheckBox) findViewById(R.id.vibration);
        ringg = (CheckBox) findViewById(R.id.ring);
        btnMap = (Button) findViewById(R.id.button8);
        vibraterange = (SeekBar) findViewById(R.id.seekBar);
        tvVibrateTime = (TextView) findViewById(R.id.tvVibrate);
        btnSave = (Button) findViewById(R.id.button9);

        vibraterange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                vibrateTime = progress * 1000;
                tvVibrateTime.setText(progress + "sn");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        distanceControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               progressChanged = progress + 1000;
                distance.setText(progressChanged + " mt");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        vibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked())
                {
                    vibration = true;
                    if(toast != null)
                    {
                        toast.cancel();
                    }
                    toast = Toast.makeText(MainActivity.this,"Vibriatio on", Toast.LENGTH_SHORT);
                    toast.show();
                }

                else
                {
                    vibration = false;
                    if(toast != null)
                    {
                        toast.cancel();
                    }
                    toast = Toast.makeText(MainActivity.this,"Vibriatio off", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        ringg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked())
                {   ring = true;
                    if(toast != null)
                    {
                        toast.cancel();
                    }
                    toast = Toast.makeText(MainActivity.this,"Ring on", Toast.LENGTH_SHORT);
                    toast.show();
                }

                else
                {
                    ring = false;
                    if(toast != null)
                    {
                        toast.cancel();
                    }
                    toast = Toast.makeText(MainActivity.this,"Ring off", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("DISTANCE", progressChanged);
                intent.putExtra("VIBRATETIME", vibrateTime);
                intent.putExtra("VIBRATION" , vibration);
                intent.putExtra("RINGOPTION", ring);

                Log.d("Deneme" , ""+ progressChanged);
                Log.d("Deneme" , ""+ vibrateTime);
                Log.d("Deneme" , ""+ vibration);
                Log.d("Deneme" , ""+ ring);
                Log.d("Deneme" , ""+ vibration);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StarterActivity.class);
                startActivity(intent);
            }
        });
    }
}
