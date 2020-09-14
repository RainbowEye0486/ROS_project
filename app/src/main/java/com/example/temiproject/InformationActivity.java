package com.example.temiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class InformationActivity extends ActivityController {

    count count;
    Timer timer;
    char next_job = ' ';
    int click_num = 0;

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

        Button develop_btn = (Button)findViewById(R.id.topbar_btn);
        develop_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                click_num ++;
                if (click_num>=10){
                    Log.d(TAG, "develop mode on ! ");
                    click_num = 0;
                    //top bar open
                    Toast.makeText(InformationActivity.this, "工作人員模式", Toast.LENGTH_LONG).show();
                }
            }
        });
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