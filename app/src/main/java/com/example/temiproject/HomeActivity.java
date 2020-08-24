package com.example.temiproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "Home_page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar actionbar	= getSupportActionBar();
        if	(actionbar	!=	null)	{
            actionbar.hide();
        }
        final Button photo_button = (Button)findViewById(R.id.home_photo_btn);
        final Button lead_button = (Button)findViewById(R.id.home_lead_btn);
        photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.bounce_animation);
                photo_button.startAnimation(bounce);
                Intent intent = new Intent(HomeActivity.this, InformationActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                Log.d(TAG, "onClick: photo button");
            }
        });
        lead_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, GuildPointActivity.class);
                startActivity(intent);
                Animation bounce = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.bounce_animation);
                lead_button.startAnimation(bounce);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                Log.d(TAG, "onClick: search button");
            }
        });

        GifImageView ImageView = findViewById(R.id.imageView);
        try{
            GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.test1);
            ImageView.setImageDrawable(gifDrawable);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}