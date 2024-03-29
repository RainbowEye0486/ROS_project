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

public class ArrivalActivity extends ActivityController {
    private static final String TAG = "ArrivalActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival);
        final Button arrival_btn = (Button)findViewById(R.id.arrival_btn);
        arrival_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer click = MediaPlayer.create(ArrivalActivity.this, R.raw.click);
                click.start();
                Intent intent = new Intent(ArrivalActivity.this, MovingActivity.class);

                intent.putExtra("task", "takePhoto");//takePhoto
                startActivity(intent);
                Animation bounce = AnimationUtils.loadAnimation(ArrivalActivity.this, R.anim.bounce_animation);
                arrival_btn.startAnimation(bounce);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                Log.d(TAG, "onClick: arrival button");
            }
        });
    }
}