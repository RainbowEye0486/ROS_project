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
import com.robotemi.sdk.TtsRequest;

import org.jetbrains.annotations.NotNull;

public class ArrivalActivity extends ActivityController implements
        Robot.TtsListener{
    private static final String TAG = "ArrivalActivity";
    private Robot robot;
    private boolean canSpeak = true;
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
        canSpeak = false;
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
                if(canSpeak){
                    toNextActivity();
                }
                break;
        }
    }

    private void toNextActivity(){
        Intent intent = new Intent(ArrivalActivity.this, MovingActivity.class);
        intent.putExtra("task", "takePhoto");//takePhoto
        startActivity(intent);
    }

    @Override
    public void onTtsStatusChanged(@NotNull TtsRequest ttsRequest) {
        // Do whatever you like upon the status changing. after the robot finishes speaking
        Log.d(TAG, "onTtsStatusChanged: ");
        switch (ttsRequest.getStatus()) {
            case STARTED:
                canSpeak = false;
                break;

            case COMPLETED:
                canSpeak = true;
                break;

            case ERROR:
                canSpeak = true;
                break;

        }
    }
}