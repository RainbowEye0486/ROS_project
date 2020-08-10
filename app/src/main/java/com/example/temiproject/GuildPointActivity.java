package com.example.temiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GuildPointActivity extends ActivityController {

    private static final String TAG = "GuildPointActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guild_point);
        ImageButton guildBrand = (ImageButton) findViewById(R.id.guild_brand);
        ImageButton guildToilet = (ImageButton)findViewById(R.id.guild_toilet);
        ImageButton guildElevator = (ImageButton)findViewById(R.id.guild_elevator);
        guildBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: guildBrand");
                Intent intent = new Intent(GuildPointActivity.this, BrandSearchActivity.class);
                startActivity(intent);
            }
        });
        guildToilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: guildToilet");
            }
        });
        guildElevator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: guildElevator");
            }
        });


    }
}