package com.example.temiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class InformationActivity extends ActivityController {

    count count;
    Timer timer;
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
        speak("將帶領您至拍照地點");
        timer = new Timer();
        count = new count();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cancelTimer();
        count.cancel();
        timer = new Timer();
        count = new count();
        timer.schedule(count, 3000);
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}