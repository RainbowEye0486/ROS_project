package com.example.temiproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.TimerTask;

public class MovingActivity extends ActivityController {
    private static final String TAG = "MovingActivity";
    char next_job = ' ';
    java.util.Timer timer = new java.util.Timer(true);//for test usage

    TimerTask count = new TimerTask() {
        public void run() {
            if (next_job == ' ') { //default go home
                Intent intent2 = new Intent(MovingActivity.this, HomeActivity.class);
                startActivity(intent2);
            }else if (next_job == 's') { //指定地點完，準備去拍照點
                Intent intent2 = new Intent(MovingActivity.this, CameraActivity.class);
                startActivity(intent2);
            }else if (next_job == 'p'){ //帶領到合適拍照位置
                Intent intent2 = new Intent(MovingActivity.this, ArrivalActivity.class);
                startActivity(intent2);
            }else if (next_job == 'l'){ //品牌設施導覽 前往中
                Intent intent2 = new Intent(MovingActivity.this, ThanksLeadingActivity.class);
            }else if (next_job == 'b'){ //導覽完畢，回去原位
                Intent intent2 = new Intent(MovingActivity.this, HomeActivity.class);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String task	= intent.getStringExtra("task");
        assert task != null;
        Log.d(TAG, "on create" + task);
        if (task.equals("0") || task.equals("1") || task.equals("2")){
            next_job = 'p';
        }else if (task.equals("takePhoto")){
            next_job = 's';
        }else if (task.equals("lead")){
            next_job = 'l';
        }else if (task.equals("back")){
            next_job = 'b';
        }
        setContentView(R.layout.activity_moving);
        timer.schedule(count, 3000);//三秒後執行task，改成監聽器觸發
    }
}