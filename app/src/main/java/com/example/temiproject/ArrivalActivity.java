package com.example.temiproject;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.robotemi.sdk.Robot;

public class ArrivalActivity extends ActivityController {
    private static final String TAG = "ArrivalActivity";
    private Robot robot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival);
        robot = Robot.getInstance();
        final Button arrival_btn = (Button)findViewById(R.id.arrival_btn);
        speak("temi即將未您拍照，站好後請點擊螢幕上的按鈕");

        arrival_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer click = MediaPlayer.create(ArrivalActivity.this, R.raw.click);
                click.start();
                toNextActivity();
                Animation bounce = AnimationUtils.loadAnimation(ArrivalActivity.this, R.anim.bounce_animation);
                arrival_btn.startAnimation(bounce);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                Log.d(TAG, "onClick: arrival button");
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

    private void toNextActivity(){
        Intent intent = new Intent(ArrivalActivity.this, MovingActivity.class);
        intent.putExtra("task", "takePhoto");//takePhoto
        startActivity(intent);
    }
}