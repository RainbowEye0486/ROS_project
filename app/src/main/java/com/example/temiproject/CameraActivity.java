package com.example.temiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;
import java.util.TimerTask;

public class CameraActivity extends ActivityController {
    private static final String TAG = "CameraActivity";
    java.util.Timer timer = new java.util.Timer(true);
    int count_num = 5;
    boolean countOver = false;


    TimerTask shot = new TimerTask() {
        public void run() {
            Log.d(TAG, "run: shot");
            //相機拍攝
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        final TextView textView = (TextView)findViewById(R.id.count_down_txt);

        CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                if (l<=6000){
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(String.format(Locale.getDefault(), "%d", l / 1000L));
                }

            }

            @Override
            public void onFinish() {
                textView.setVisibility(View.INVISIBLE);
            }
        }.start();


        timer.schedule(shot, 500);



    }
}