package com.example.temiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
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

import java.util.ArrayList;
import java.util.List;

public class GuildPointActivity extends ActivityController {

    private static final String TAG = "GuildPointActivity";
    private SoundPool soundPool;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce_animation);
        setContentView(R.layout.activity_guild_point);
        final Button guildBrand = (Button) findViewById(R.id.select_brand_btn);
        final Button guildToilet = (Button)findViewById(R.id.select_toilet_btn);
        final Button guildElevator = (Button)findViewById(R.id.select_elevator_btn);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .build();

        guildBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: guildBrand");
                Intent intent = new Intent(GuildPointActivity.this, BrandSearchActivity.class);
                Animation bounce = AnimationUtils.loadAnimation(GuildPointActivity.this, R.anim.bounce_animation);
                guildBrand.startAnimation(bounce);
                startActivity(intent);
            }
        });
        guildToilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: guildToilet");
                Animation bounce = AnimationUtils.loadAnimation(GuildPointActivity.this, R.anim.bounce_animation);
                guildToilet.startAnimation(bounce);
                int click = soundPool.load(GuildPointActivity.this, R.raw.click, 1);
                soundPool.play(click, 100, 100, 0, 0, 1);
            }
        });
        guildElevator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: guildElevator");
                Animation bounce = AnimationUtils.loadAnimation(GuildPointActivity.this, R.anim.bounce_animation);
                guildElevator.startAnimation(bounce);
                int click = soundPool.load(GuildPointActivity.this, R.raw.click, 1);
                soundPool.play(click, 1, 1, 0, 0, 1);
            }
        });


    }

}