package com.example.temiproject;

import androidx.annotation.RequiresApi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends ActivityController {

    private static final String TAG = "MapActivity";
    private String[] order= {"edwin", "lanew", "miamia", "poya"};

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //右邊的排序結果
        flush();




        Button goLead = (Button)findViewById(R.id.map_go);
        ImageView mapImage = (ImageView)findViewById(R.id.bigMap);
        String current_map = "";
        goLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: goLead button");
                Intent intent = new Intent(MapActivity.this, MovingActivity.class);
                intent.putExtra("task", "lead");
            }
        });

        //建立排序物件
        List<mapView> brandIdem = new ArrayList<>();
        for(int i = 0; i < order.length; i++){
            String brandName = order[i];
            Log.d(TAG, "onCreate: brandName " + brandName);
            //if (brandName.length() == 0){
            //    Log.d(TAG, "onCreate: is null");
            //    continue;
            //}
            switch (brandName) {
                case "cosmed": {
                    Drawable drawableFore = getResources().getDrawable(R.drawable.map_cosmed);
                    brandIdem.add(new mapView("b2b1", "cosmed", drawableFore, i));
                    break;
                }
                case "miamia": {
                    Drawable drawableFore = getResources().getDrawable(R.drawable.map_miamia);
                    brandIdem.add(new mapView("b2b1", "miamia", drawableFore, i));
                    break;
                }
                case "wolsey": {
                    Drawable drawableFore = getResources().getDrawable(R.drawable.map_wolsey);
                    brandIdem.add(new mapView("b2b1", "wolsey", drawableFore, i));
                    break;
                }
                case "perngyuh": {
                    Drawable drawableFore = getResources().getDrawable(R.drawable.map_perngyuh);
                    brandIdem.add(new mapView("b2b1", "perngyuh", drawableFore, i));
                    break;
                }
                case "coach": {
                    Drawable drawableFore = getResources().getDrawable(R.drawable.map_coach);
                    brandIdem.add(new mapView("b2lb", "coach", drawableFore, i));
                    break;
                }
                case "roots": {
                    Drawable drawableFore = getResources().getDrawable(R.drawable.map_roots);
                    brandIdem.add(new mapView("b2lb", "roots", drawableFore, i));
                    break;
                }
                case "poloRalphLauren": {
                    Drawable drawableFore = getResources().getDrawable(R.drawable.map_polo);
                    brandIdem.add(new mapView("b2lb", "poloRalphLauren", drawableFore, i));
                    break;
                }
                case "lacoste": {
                    Drawable drawableFore = getResources().getDrawable(R.drawable.map_lacoste);
                    brandIdem.add(new mapView("b2lb", "lacoste", drawableFore, i));
                    break;
                }
                case "edwin": {
                    Log.d(TAG, "onCreate: into edwin");
                    Drawable drawableFore = getResources().getDrawable(R.drawable.map_edwin);
                    brandIdem.add(new mapView("b1b1", "edwin", drawableFore, i));
                    break;
                }
                case "lanew": {
                    Drawable drawableFore = getResources().getDrawable(R.drawable.map_lanew);
                    brandIdem.add(new mapView("b1b1", "lanew", drawableFore, i));
                    break;
                }
                case "blueway": {
                    Drawable drawableFore = getResources().getDrawable(R.drawable.map_blueway);
                    brandIdem.add(new mapView("b1b1", "blueway", drawableFore, i));
                    break;
                }
                case "poya": {
                    Drawable drawableFore = getResources().getDrawable(R.drawable.map_poya);
                    brandIdem.add(new mapView("ab1", "poya", drawableFore, i));
                    break;
                }
            }
        }//construct brand item
        Log.d(TAG, "onCreate: branditem" + brandIdem.size());

        //開始疊圖
        Drawable b2b1 = getDrawable(R.drawable.map_b2b1);
        Drawable b1b1 = getDrawable(R.drawable.map_b1b1);
        Drawable b2lb = getDrawable(R.drawable.map_b2lb);
        Drawable a_b1 = getDrawable(R.drawable.map_a_b1);
        TextView floorText = (TextView)findViewById(R.id.floor_text);
        for(int i = 0; i < brandIdem.size(); i++){
            String floor=brandIdem.get(i).getFloor();
            Log.d(TAG, "onCreate: foldgraph item " + i + " floor " + floor);
            switch (floor) {
                case "b2b1":
                    b2b1 = brandIdem.get(i).combineGraph(b2b1);
                    if (i == 1) {
                        current_map = floor;
                        mapImage.setImageDrawable(b2b1);
                        floorText.setText("B2-B1");
                    }
                    break;
                case "b2lb":
                    b2lb = brandIdem.get(i).combineGraph(b2lb);
                    if (i == 1) {
                        current_map = floor;
                        mapImage.setImageDrawable(b2lb);
                        floorText.setText("B2-LB");
                    }
                    break;
                case "b1b1":
                    b1b1 = brandIdem.get(i).combineGraph(b1b1);
                    if (i == 1) {
                        current_map = floor;
                        mapImage.setImageDrawable(b1b1);
                        floorText.setText("B1-B1");
                    }
                    break;
                case "ab1":
                    a_b1 = brandIdem.get(i).combineGraph(a_b1);
                    if (i == 1) {
                        current_map = floor;
                        mapImage.setImageDrawable(a_b1);
                        floorText.setText("A-B1");
                    }
                    break;
            }
        }

        ImageButton up = (ImageButton)findViewById(R.id.up);
        ImageButton down = (ImageButton)findViewById(R.id.down);
        ImageButton left = (ImageButton)findViewById(R.id.left);
        ImageButton right = (ImageButton)findViewById(R.id.right);


        final Drawable finalB2lb = b2lb;
        final Drawable finalB2b1 = b2b1;
        final Drawable finalA_b1 = a_b1;
        final Drawable finalB1b1 = b1b1;
        ImageView minimap = (ImageView)findViewById(R.id.minimap);


        if (floorText.getText().equals("B1-B1")){
            up.setVisibility(View.GONE);
            down.setVisibility(View.GONE);
            minimap.setImageResource(R.drawable.minimap_b11);
        }
        else if (floorText.getText().equals("A-B1")){
            up.setVisibility(View.GONE);
            down.setVisibility(View.GONE);
            left.setVisibility(View.GONE);
            minimap.setImageResource(R.drawable.minimap_ab1);
        }
        else if (floorText.getText().equals("B2-B1")){
            right.setVisibility(View.GONE);
            down.setVisibility(View.GONE);
            minimap.setImageResource(R.drawable.minimap_b12);
        }
        else if (floorText.getText().equals("B1-LB")){
            up.setVisibility(View.GONE);
            right.setVisibility(View.GONE);
            left.setVisibility(View.GONE);
            minimap.setImageResource(R.drawable.minimap_lb2);
        }


        up.setOnClickListener(new View.OnClickListener() {
            ImageButton up = (ImageButton)findViewById(R.id.up);
            ImageButton down = (ImageButton)findViewById(R.id.down);
            ImageButton left = (ImageButton)findViewById(R.id.left);
            ImageButton right = (ImageButton)findViewById(R.id.right);
            ImageView mapImage = findViewById(R.id.bigMap);
            TextView floorText = (TextView)findViewById(R.id.floor_text);
            ImageView minimap = (ImageView)findViewById(R.id.minimap);
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: up button");
                if (floorText.getText().equals("B2-B1")){
                    floorText.setText("B2-LB");
                    mapImage.setImageDrawable(finalB2lb);
                    up.setVisibility(View.GONE);
                    left.setVisibility(View.GONE);
                    right.setVisibility(View.GONE);
                    down.setVisibility(View.VISIBLE);
                    minimap.setImageResource(R.drawable.minimap_lb2);
                }
            }
        });

        down.setOnClickListener(new View.OnClickListener(){
            ImageButton up = (ImageButton)findViewById(R.id.up);
            ImageButton down = (ImageButton)findViewById(R.id.down);
            ImageButton left = (ImageButton)findViewById(R.id.left);
            ImageButton right = (ImageButton)findViewById(R.id.right);
            ImageView mapImage = findViewById(R.id.bigMap);
            TextView floorText = (TextView)findViewById(R.id.floor_text);
            ImageView minimap = (ImageView)findViewById(R.id.minimap);
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: down button");
                if (floorText.getText().equals("B2-LB")){
                    floorText.setText("B2-B1");
                    mapImage.setImageDrawable(finalB2b1);
                    up.setVisibility(View.VISIBLE);
                    left.setVisibility(View.VISIBLE);
                    down.setVisibility(View.GONE);
                    right.setVisibility(View.GONE);
                    minimap.setImageResource(R.drawable.minimap_b12);
                }
            }
        });

        left.setOnClickListener(new View.OnClickListener(){
            ImageButton up = (ImageButton)findViewById(R.id.up);
            ImageButton down = (ImageButton)findViewById(R.id.down);
            ImageButton left = (ImageButton)findViewById(R.id.left);
            ImageButton right = (ImageButton)findViewById(R.id.right);
            ImageView mapImage = findViewById(R.id.bigMap);
            TextView floorText = (TextView)findViewById(R.id.floor_text);
            ImageView minimap = (ImageView)findViewById(R.id.minimap);
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: left button");
                if (floorText.getText().equals("B1-B1")){
                    floorText.setText("A-B1");
                    mapImage.setImageDrawable(finalA_b1);
                    up.setVisibility(View.GONE);
                    left.setVisibility(View.GONE);
                    down.setVisibility(View.GONE);
                    right.setVisibility(View.VISIBLE);
                    minimap.setImageResource(R.drawable.minimap_ab1);
                }
                else if (floorText.getText().equals("B2-B1")){
                    floorText.setText("B1-B1");
                    mapImage.setImageDrawable(finalB1b1);
                    up.setVisibility(View.GONE);
                    left.setVisibility(View.VISIBLE);
                    down.setVisibility(View.GONE);
                    right.setVisibility(View.VISIBLE);
                    minimap.setImageResource(R.drawable.minimap_b11);
                }
            }
        });
        right.setOnClickListener(new View.OnClickListener(){
            ImageButton up = (ImageButton)findViewById(R.id.up);
            ImageButton down = (ImageButton)findViewById(R.id.down);
            ImageButton left = (ImageButton)findViewById(R.id.left);
            ImageButton right = (ImageButton)findViewById(R.id.right);
            ImageView mapImage = findViewById(R.id.bigMap);
            TextView floorText = (TextView)findViewById(R.id.floor_text);
            ImageView minimap = (ImageView)findViewById(R.id.minimap);
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: right button");
                if (floorText.getText().equals("A-B1")){
                    floorText.setText("B1-B1");
                    mapImage.setImageDrawable(finalB1b1);
                    up.setVisibility(View.GONE);
                    left.setVisibility(View.VISIBLE);
                    right.setVisibility(View.VISIBLE);
                    down.setVisibility(View.GONE);
                    minimap.setImageResource(R.drawable.minimap_b11);
                }
                else if (floorText.getText().equals("B1-B1")){
                    floorText.setText("B2-B1");
                    mapImage.setImageDrawable(finalB2b1);
                    up.setVisibility(View.VISIBLE);
                    left.setVisibility(View.VISIBLE);
                    right.setVisibility(View.GONE);
                    down.setVisibility(View.GONE);
                    minimap.setImageResource(R.drawable.minimap_b12);
                }
            }
        });
    }

    public void flush(){
        int length = order.length;
        Log.d(TAG, "flush: " + length);
        ImageView beacon1 = (ImageView)findViewById(R.id.beacon1);
        ImageView beacon2 = (ImageView)findViewById(R.id.beacon2);
        ImageView beacon3 = (ImageView)findViewById(R.id.beacon3);
        ImageView beacon4 = (ImageView)findViewById(R.id.beacon4);
        ImageView beacon5 = (ImageView)findViewById(R.id.beacon5);
        TextView beacon1_txt = (TextView)findViewById(R.id.beacon1_txt);
        TextView beacon2_txt = (TextView)findViewById(R.id.beacon2_txt);
        TextView beacon3_txt = (TextView)findViewById(R.id.beacon3_txt);
        TextView beacon4_txt = (TextView)findViewById(R.id.beacon4_txt);
        TextView beacon5_txt = (TextView)findViewById(R.id.beacon5_txt);
        beacon1.setVisibility(View.INVISIBLE);
        beacon2.setVisibility(View.INVISIBLE);
        beacon3.setVisibility(View.INVISIBLE);
        beacon4.setVisibility(View.INVISIBLE);
        beacon5.setVisibility(View.INVISIBLE);
        beacon1_txt.setVisibility(View.INVISIBLE);
        beacon2_txt.setVisibility(View.INVISIBLE);
        beacon3_txt.setVisibility(View.INVISIBLE);
        beacon4_txt.setVisibility(View.INVISIBLE);
        beacon5_txt.setVisibility(View.INVISIBLE);
        if(length == 0)return;

        beacon1.setVisibility(View.VISIBLE);
        beacon1_txt.setVisibility(View.VISIBLE);
        beacon1_txt.setText(order[0]);
        if(length == 1)return;
        beacon2.setVisibility(View.VISIBLE);
        beacon2_txt.setVisibility(View.VISIBLE);
        beacon2_txt.setText(order[1]);
        if(length == 2)return;
        beacon3.setVisibility(View.VISIBLE);
        beacon3_txt.setVisibility(View.VISIBLE);
        beacon3_txt.setText(order[2]);
        if(length == 3)return;
        beacon4.setVisibility(View.VISIBLE);
        beacon4_txt.setVisibility(View.VISIBLE);
        beacon4_txt.setText(order[3]);
        if(length == 4)return;
        beacon5.setVisibility(View.VISIBLE);
        beacon5_txt.setVisibility(View.VISIBLE);
        beacon5_txt.setText(order[4]);
    }
}