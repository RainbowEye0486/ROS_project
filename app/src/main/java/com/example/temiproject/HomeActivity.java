package com.example.temiproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "Home_page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar actionbar	= getSupportActionBar();
        if	(actionbar	!=	null)	{
            actionbar.hide();
        }
        Button photo_button = (Button)findViewById(R.id.home_photo_btn);
        Button lead_button = (Button)findViewById(R.id.home_lead_btn);
        photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SelectPointActivity.class);
                startActivity(intent);
                Log.d(TAG, "onClick: photo button");
            }
        });
        lead_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, GuildPointActivity.class);
                startActivity(intent);
                Log.d(TAG, "onClick: search button");
            }
        });
    }

}