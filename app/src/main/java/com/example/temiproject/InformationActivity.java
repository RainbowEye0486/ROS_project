package com.example.temiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class InformationActivity extends ActivityController {
    Timer timer;
    count count;
    char next_job = ' ';

    class count extends TimerTask {
        public void run() {
            Intent intent2 = new Intent(InformationActivity.this, MovingActivity.class);
            intent2.putExtra("task", "picture");
            startActivity(intent2);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        timer = new Timer();
        count = new count();
    }

    @Override
    protected void onStart() {

        super.onStart();
        Log.d(TAG, "onStart: onstart");
        Log.d(TAG, "onStart: " + timer);
        count.cancel();
        cancelTimer();
        Log.d(TAG, "onStart: " + timer);
        timer = new Timer();
        count = new count();
        Log.d(TAG, "onStart: " + timer);
        timer.schedule(count, 3000);
    }



    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}