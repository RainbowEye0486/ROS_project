package com.example.temiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.TimerTask;

public class InformationActivity extends ActivityController {


    java.util.Timer timer = new java.util.Timer(true);
    char next_job = ' ';

    TimerTask count = new TimerTask() {
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
        timer.schedule(count, 3000);
    }
}