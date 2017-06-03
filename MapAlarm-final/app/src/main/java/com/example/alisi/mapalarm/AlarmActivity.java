package com.example.alisi.mapalarm;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AlarmActivity extends AppCompatActivity {
    private int vibrationTime;
    private Button btnStop;
    private Vibrator vibrator;
    public boolean novibrate;
    public boolean noring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Log.d("Deneme","Activity");

        Bundle extra = getIntent().getExtras();
        vibrationTime = extra.getInt("VIBRATIONTIME");
        noring = extra.getBoolean("RINGOPTIONN");
        novibrate = extra.getBoolean("VIBRATIONOPTION");




        vibrator = (Vibrator) AlarmActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
        final MediaPlayer mediaPlayer = MediaPlayer.create(AlarmActivity.this, R.raw.elegant_ringtone);

        if(noring == true && novibrate == true)
        {
            vibrator.vibrate(vibrationTime);
            mediaPlayer.start();
        }
         if(noring == true && novibrate == false)
        {
            mediaPlayer.start();
        }
        if(noring == false && novibrate == true)
        {
            vibrator.vibrate(vibrationTime);
        }
        else
        {

        }

        btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vibrator.cancel();
                mediaPlayer.stop();
                Log.d("Deneme","Titreme dugmesi");

            }
        });


    }



}
