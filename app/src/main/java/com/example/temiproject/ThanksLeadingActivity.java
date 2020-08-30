package com.example.temiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.TimerTask;

public class ThanksLeadingActivity extends AppCompatActivity {
    private static final String TAG = "ThankLeadingActivity";
    java.util.Timer timer = new java.util.Timer(true);

    TimerTask count = new TimerTask() {
        public void run() {
            Intent intent2 = new Intent(ThanksLeadingActivity.this, MovingActivity.class);
            intent2.putExtra("task", "back");
            startActivity(intent2);


        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks_leading);
        Log.d(TAG, "onCreate: Back to station");
        timer.schedule(count, 3000);


        final Button home_btn = (Button)findViewById(R.id.home_btn);
        final Button back_home_btn = (Button)findViewById(R.id.back_home_btn);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation(ThanksLeadingActivity.this, R.anim.bounce_animation);
                MediaPlayer click = MediaPlayer.create(ThanksLeadingActivity.this, R.raw.click);
                click.start();
                home_btn.startAnimation(bounce);
                Intent intent = new Intent(ThanksLeadingActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                Log.d(TAG, "onClick: Home button");
            }
        });
        back_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation(ThanksLeadingActivity.this, R.anim.bounce_animation);
                MediaPlayer click = MediaPlayer.create(ThanksLeadingActivity.this, R.raw.click);
                click.start();
                back_home_btn.startAnimation(bounce);
                Intent intent = new Intent(ThanksLeadingActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                Log.d(TAG, "onClick: Home button");
            }
        });

    }


}