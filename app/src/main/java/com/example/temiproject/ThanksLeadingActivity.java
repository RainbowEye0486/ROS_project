package com.example.temiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
    }


}