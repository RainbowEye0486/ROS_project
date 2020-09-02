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

import com.robotemi.sdk.Robot;

import java.util.TimerTask;

public class ThanksLeadingActivity extends ActivityController {
    private static final String TAG = "ThankLeadingActivity";
    private Robot robot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks_leading);
        robot = Robot.getInstance();
        Log.d(TAG, "onCreate: Back to station");


        final Button home_btn = (Button)findViewById(R.id.home_btn);
        final Button back_home_btn = (Button)findViewById(R.id.back_home_btn);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation(ThanksLeadingActivity.this, R.anim.bounce_animation);
                MediaPlayer click = MediaPlayer.create(ThanksLeadingActivity.this, R.raw.click);
                click.start();
                home_btn.startAnimation(bounce);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                Log.d(TAG, "onClick: Home button");
                toNextActivity();
            }
        });
        back_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation(ThanksLeadingActivity.this, R.anim.bounce_animation);
                MediaPlayer click = MediaPlayer.create(ThanksLeadingActivity.this, R.raw.click);
                click.start();
                back_home_btn.startAnimation(bounce);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                Log.d(TAG, "onClick: Home button");
                toNextActivity();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        robot.addOnDetectionStateChangedListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        robot.removeOnDetectionStateChangedListener(this);
    }

    // go to MovingActivity
    private void toNextActivity(){
        Intent intent = new Intent(ThanksLeadingActivity.this, MovingActivity.class);
        intent.putExtra("task", "back");
        startActivity(intent);
    }

    @Override
    public void onDetectionStateChanged(int state) {
        Log.d(TAG, "onDetectionStateChanged: state ="+ state);
        switch (state){
            case DETECTED:
                break;
            case IDLE:
                toNextActivity();
                break;
        }

    }


}