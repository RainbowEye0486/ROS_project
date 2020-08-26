package com.example.temiproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class HomeActivity extends ActivityController {

    private static final String TAG = "Home_page";
    private int click_num = 0;
    private int click_update = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        final Button photo_button = (Button)findViewById(R.id.home_photo_btn);
        final Button lead_button = (Button)findViewById(R.id.home_lead_btn);
        Button develop_btn = (Button)findViewById(R.id.develop_btn);
        Button update_btn = (Button)findViewById(R.id.update_btn);
        photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.bounce_animation);
                MediaPlayer click = MediaPlayer.create(HomeActivity.this, R.raw.click);
                click.start();
                photo_button.startAnimation(bounce);
                Intent intent = new Intent(HomeActivity.this, InformationActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                Log.d(TAG, "onClick: photo button");
            }
        });
        lead_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, GuildPointActivity.class);

                MediaPlayer click = MediaPlayer.create(HomeActivity.this, R.raw.click);
                click.start();
                Animation bounce = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.bounce_animation);
                lead_button.startAnimation(bounce);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                Log.d(TAG, "onClick: search button");
            }
        });

        develop_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                click_num ++;
                if (click_num>=10){
                    Log.d(TAG, "develop mode on ! ");
                    click_num = 0;
                    //top bar open
                }
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                click_update ++;
                if (click_update>=10){
                    Log.d(TAG, "update database! ");
                    click_update = 0;
                    //update
                }
            }
        });

        //ImageView mask = (ImageView)findViewById(R.id.mask);
        //mask.setVisibility(View.VISIBLE);
        //GifImageView ImageView = findViewById(R.id.loading);
        //try{
        //    GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.loading);
        //    ImageView.setImageDrawable(gifDrawable);
        //    ImageView.setVisibility(View.VISIBLE);
        //}catch (Exception e){
        //    e.printStackTrace();
        //}

    }

}