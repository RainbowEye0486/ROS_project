package com.example.temiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.listeners.OnDetectionStateChangedListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GuildPointActivity extends ActivityController implements
        Robot.TtsListener{

    private static final String TAG = "GuildPointActivity";
    Robot robot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce_animation);
        setContentView(R.layout.activity_guild_point);
        robot = Robot.getInstance();
        final Button guildBrand = (Button) findViewById(R.id.select_brand_btn);
        final Button guildToilet = (Button)findViewById(R.id.select_toilet_btn);
        final Button guildElevator = (Button)findViewById(R.id.select_elevator_btn);
        final Button home_btn = (Button)findViewById(R.id.home_btn);
        final Button return_btn = (Button)findViewById(R.id.return_btn);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation(GuildPointActivity.this, R.anim.bounce_animation);
                MediaPlayer click = MediaPlayer.create(GuildPointActivity.this, R.raw.click);
                click.start();
                home_btn.startAnimation(bounce);
                Intent intent = new Intent(GuildPointActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                Log.d(TAG, "onClick: Home button");
            }
        });
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuildPointActivity.this, HomeActivity.class);

                MediaPlayer click = MediaPlayer.create(GuildPointActivity.this, R.raw.click);
                click.start();
                Animation bounce = AnimationUtils.loadAnimation(GuildPointActivity.this, R.anim.bounce_animation);
                return_btn.startAnimation(bounce);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                Log.d(TAG, "onClick: Return button");
            }
        });

        guildBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: guildBrand");
                Intent intent = new Intent(GuildPointActivity.this, BrandSearchActivity.class);
                Animation bounce = AnimationUtils.loadAnimation(GuildPointActivity.this, R.anim.bounce_animation);
                guildBrand.startAnimation(bounce);
                MediaPlayer click = MediaPlayer.create(GuildPointActivity.this, R.raw.click);
                click.start();
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            }
        });
        guildToilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: guildToilet");
                Animation bounce = AnimationUtils.loadAnimation(GuildPointActivity.this, R.anim.bounce_animation);
                guildToilet.startAnimation(bounce);
                MediaPlayer click = MediaPlayer.create(GuildPointActivity.this, R.raw.click);
                click.start();
                Intent intent = new Intent(GuildPointActivity.this, MapActivity.class);
                intent.putExtra("task", "toilet");
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            }
        });
        guildElevator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: guildElevator");
                Animation bounce = AnimationUtils.loadAnimation(GuildPointActivity.this, R.anim.bounce_animation);
                guildElevator.startAnimation(bounce);
                MediaPlayer click = MediaPlayer.create(GuildPointActivity.this, R.raw.click);
                click.start();
                Intent intent = new Intent(GuildPointActivity.this, MapActivity.class);
                intent.putExtra("task", "elevator");
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        // temi listener
        robot.addOnDetectionStateChangedListener(this);
        robot.addTtsListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // temi listener
        robot.removeOnDetectionStateChangedListener(this);
        robot.removeTtsListener(this);
    }

    @Override
    public void onDetectionStateChanged(int state) {
        Log.d(TAG, "onDetectionStateChanged: state ="+ state);
        switch (state){
            case DETECTED:
                break;
            case IDLE:
                Intent intent = new Intent(GuildPointActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onTtsStatusChanged(@NotNull TtsRequest ttsRequest) {

    }
}