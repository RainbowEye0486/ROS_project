package com.example.temiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends ActivityController {

    private static final String TAG = "MapActivity";
    private String[] order= {"edwin", "lanew", "miamia", "poya", null};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Button goLead = (Button)findViewById(R.id.map_go);
        ImageView mapImage = (ImageView)findViewById(R.id.bigMap);
        goLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: goLead button");
                Intent intent = new Intent(MapActivity.this, MovingActivity.class);
                intent.putExtra("task", "lead");
            }
        });

        List<mapView> brandIdem = new ArrayList<>();
        for(int i = 0; i < order.length; i++){

        }


    }
}