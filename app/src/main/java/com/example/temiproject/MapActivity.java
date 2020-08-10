package com.example.temiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MapActivity extends ActivityController {

    private static final String TAG = "MapActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Button goLead = (Button)findViewById(R.id.map_go);
        goLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: goLead button");
                Intent intent = new Intent(MapActivity.this, MovingActivity.class);
                intent.putExtra("task", "lead");
            }
        });
    }
}