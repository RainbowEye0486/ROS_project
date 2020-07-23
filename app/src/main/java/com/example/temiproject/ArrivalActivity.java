package com.example.temiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ArrivalActivity extends ActivityController {
    private static final String TAG = "ArrivalActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival);
        Button arrival_btn = (Button)findViewById(R.id.arrival_btn);
        arrival_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArrivalActivity.this, MovingActivity.class);
                intent.putExtra("task", "takePhoto");
                startActivity(intent);
                Log.d(TAG, "onClick: arrival button");
            }
        });
    }
}