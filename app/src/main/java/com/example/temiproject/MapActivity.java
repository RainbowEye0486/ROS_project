package com.example.temiproject;

import androidx.annotation.RequiresApi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

//import com.robotemi.sdk.Robot;

import com.robotemi.sdk.Robot;
import com.robotemi.sdk.listeners.OnDetectionStateChangedListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapActivity extends ActivityController {

    private int idle_count = 0;
    private Robot robot;
    private static final String TAG = "MapActivity";
    private String[] order;
    private String task;//from last activity
    private String target;//past destination to moving activity
    List<mapView> brandIdem = new ArrayList<>();
    private Button goLead;
    private Drawable finalB2lb;
    private Drawable finalB2b1;
    private Drawable finalA_b1;
    private Drawable finalB1b1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        robot = Robot.getInstance();
        openDB();
        receiveIntent();

        final Button home_btn = (Button)findViewById(R.id.home_btn);
        final Button return_btn = (Button)findViewById(R.id.return_btn);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation(MapActivity.this, R.anim.bounce_animation);
                MediaPlayer click = MediaPlayer.create(MapActivity.this, R.raw.click);
                click.start();
                home_btn.startAnimation(bounce);
                Intent intent = new Intent(MapActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                Log.d(TAG, "onClick: Home button");
            }
        });
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                assert task != null;
                if (task.equals("brand")){
                    intent = new Intent(MapActivity.this, BrandSearchActivity.class);
                }
                else if (task.equals("toilet") || (task.equals("elevator"))){
                    intent = new Intent(MapActivity.this, GuildPointActivity.class);
                }

                MediaPlayer click = MediaPlayer.create(MapActivity.this, R.raw.click);
                click.start();
                Animation bounce = AnimationUtils.loadAnimation(MapActivity.this, R.anim.bounce_animation);
                return_btn.startAnimation(bounce);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                Log.d(TAG, "onClick: Return button");
            }
        });


        //右邊的排序結果
        flush();


        
    }

    public Drawable combineGraph(Drawable drawableFore, Drawable drawableBack){
        Bitmap bitmapFore = ((BitmapDrawable) drawableFore).getBitmap();
        Bitmap bitmapBack = ((BitmapDrawable) drawableBack).getBitmap();
        Bitmap scaledBitmapFore = Bitmap.createScaledBitmap(bitmapFore, 1000 , 714 , true);
        Bitmap scaledBitmapBack = Bitmap.createScaledBitmap(bitmapBack, 1000 , 714 , true);
        Bitmap combineImages = overlay(scaledBitmapBack, scaledBitmapFore);
        return new BitmapDrawable(this.getResources(), combineImages);
    }

    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2)//疊圖用
    {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, 0, 0, null);
        return bmOverlay;

    }


    public void flush() {
        int length = order.length;
        ImageView beacon1 = (ImageView) findViewById(R.id.beacon1);
        ImageView beacon2 = (ImageView) findViewById(R.id.beacon2);
        ImageView beacon3 = (ImageView) findViewById(R.id.beacon3);
        ImageView beacon4 = (ImageView) findViewById(R.id.beacon4);
        ImageView beacon5 = (ImageView) findViewById(R.id.beacon5);
        TextView beacon1_txt = (TextView) findViewById(R.id.beacon1_txt);
        TextView beacon2_txt = (TextView) findViewById(R.id.beacon2_txt);
        TextView beacon3_txt = (TextView) findViewById(R.id.beacon3_txt);
        TextView beacon4_txt = (TextView) findViewById(R.id.beacon4_txt);
        TextView beacon5_txt = (TextView) findViewById(R.id.beacon5_txt);
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
        String[] reorder = {"", "", "", "", ""};
        int j = 0;
        for (String s : order) {
            if (j>5)break;
            if (s.equals("B1電梯") || s.equals("B1手扶梯") ||
                    s.equals("LB電梯") || s.equals("LB手扶梯")) {
                continue;
            }
            else{
                reorder[j] = s;
                j++;
            }
        }

        if (task.equals("elevator")) {
            beacon1.setVisibility(View.VISIBLE);
            beacon1_txt.setVisibility(View.VISIBLE);
            beacon1_txt.setText("電梯");
            return;
        }
        else if ( task.equals("toilet")){
            beacon1.setVisibility(View.VISIBLE);
            beacon1_txt.setVisibility(View.VISIBLE);
            beacon1_txt.setText("廁所");
            return;
        }


        Log.d(TAG, "flush: " + length);

        if (length == 0) return;
        beacon1.setVisibility(View.VISIBLE);
        beacon1_txt.setVisibility(View.VISIBLE);
        beacon1_txt.setText(reorder[0]);
        if (length == 1) return;
        beacon2.setVisibility(View.VISIBLE);
        beacon2_txt.setVisibility(View.VISIBLE);
        beacon2_txt.setText(reorder[1]);
        if (length == 2) return;
        beacon3.setVisibility(View.VISIBLE);
        beacon3_txt.setVisibility(View.VISIBLE);
        beacon3_txt.setText(reorder[2]);
        if (length == 3) return;
        beacon4.setVisibility(View.VISIBLE);
        beacon4_txt.setVisibility(View.VISIBLE);
        beacon4_txt.setText(reorder[3]);
        if (length == 4) return;
        beacon5.setVisibility(View.VISIBLE);
        beacon5_txt.setVisibility(View.VISIBLE);
        beacon5_txt.setText(reorder[4]);

    }

    private void receiveIntent(){
        Intent intent = getIntent();
        task = intent.getStringExtra("task");
        Log.d(TAG, "receiveIntent: task:"+task);
        if(task.equals("brand")){
            Log.d(TAG, "receiveIntent: in brand");
            ArrayList<Position> route = intent.getParcelableArrayListExtra("route");
            order = posToStoreName(route);
        }else if(task.equals("elevator")){
            Log.d(TAG, "receiveIntent: elevator");
            String[] location = {"B1電梯"};
            order = location;
            target = "bb12l";
        }else if(task.equals("toilet")){
            Log.d(TAG, "receiveIntent: toilet");
            String[] location = {"廁所"};
            order = location;
            target = "toilet";
        }

    }

    private String[] posToStoreName(ArrayList<Position> route){
        ArrayList<String> sequence = new ArrayList();
        ArrayList<String> names = new ArrayList<>();
        for(Position pos: route){
            List<String> stores = pos.stores;
            for(String store: stores){
                sequence.add(store);
                names.add(storeIDToName(store));
                Log.d(TAG, "posToStoreName: "+storeIDToName(store));
            }
        }
        if(sequence.size()>0){
            target = sequence.get(0);
            Log.d(TAG, "posToStoreName: target:"+target);
        }
        return names.toArray(new String[0]);
    }

    private String storeIDToName(String ID){
        String result = null;
        String[] IDs = new String[]{ID};
        SQLiteDatabase db = DH.getWritableDatabase();
        String query = "SELECT cn_name FROM Store"
                + " WHERE id = ?";
        Cursor cursor = db.rawQuery(query, IDs);
        while (cursor.moveToNext()){
            result = cursor.getString(0);
            Log.d(TAG, "storeIDToName: "+cursor.getString(0));
        }
        return result;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // temi listener
        idle_count = 0;
        robot.addOnDetectionStateChangedListener(this);

        Log.d(TAG, "toNextActivity: task-"+task+"taget-"+target);
        Log.d(TAG, "onStart: order:");
        for(String ele :order){
            Log.d(TAG, "onStart: "+ele);
        }
        Intent intent = getIntent();
        task = intent.getStringExtra("task");
        receiveIntent();



        //new element
        brandIdem = new ArrayList<>();



        goLead = (Button)findViewById(R.id.map_go);
        ImageView mapImage = (ImageView)findViewById(R.id.bigMap);
        goLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: goLead button");
                MediaPlayer click = MediaPlayer.create(MapActivity.this, R.raw.click);
                click.start();
                Animation bounce = AnimationUtils.loadAnimation(MapActivity.this, R.anim.bounce_animation);
                goLead.startAnimation(bounce);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                // send intent
                toNextActivity();
            }
        });

        flush();
        assert task != null;
        if (task.equals("toilet")){
            Drawable pic = getDrawable(R.drawable.map_toilet);
            mapImage.setImageDrawable(pic);
            brandIdem.add(new mapView("b2b1", "廁所", pic, 0));
            Button up = (Button) findViewById(R.id.up);
            Button down = (Button) findViewById(R.id.down);
            Button left = (Button) findViewById(R.id.left);
            Button right = (Button) findViewById(R.id.right);
            up.setVisibility(View.INVISIBLE);
            down.setVisibility(View.INVISIBLE);
            left.setVisibility(View.INVISIBLE);
            right.setVisibility(View.INVISIBLE);
            Log.d(TAG, "onCreate: branditem" + brandIdem.get(0).brandName);
            flush();
        }
        else if (task.equals("elevator")){
            Drawable pic = getDrawable(R.drawable.map_elevator);
            mapImage.setImageDrawable(pic);
            brandIdem.add(new mapView("b2b1", "電梯", pic, 0));
            Button up = (Button) findViewById(R.id.up);
            Button down = (Button) findViewById(R.id.down);
            Button left = (Button) findViewById(R.id.left);
            Button right = (Button) findViewById(R.id.right);
            up.setVisibility(View.INVISIBLE);
            down.setVisibility(View.INVISIBLE);
            left.setVisibility(View.INVISIBLE);
            right.setVisibility(View.INVISIBLE);
            Log.d(TAG, "onCreate: branditem" + brandIdem.size());
            flush();
        }
        else if (task.equals("brand")) {
            //建立排序物件


            Drawable b2b1 = getDrawable(R.drawable.map_b2b1);
            Drawable b1b1 = getDrawable(R.drawable.map_b1b1);
            Drawable b2lb = getDrawable(R.drawable.map_b2lb);
            Drawable a_b1 = getDrawable(R.drawable.map_a_b1);


            for (int i = 0; i < order.length; i++) {
                String brandName = order[i];
                Log.d(TAG, "onCreate: brandName " + brandName);

                switch (brandName) {
                    case "康是美": {
                        Drawable drawableFore = getDrawable(R.drawable.map_cosmed);
                        brandIdem.add(new mapView("b2b1", "cosmed", drawableFore, i));
                        break;
                    }
                    case "mia mia": {
                        Drawable drawableFore = getDrawable(R.drawable.map_miamia);
                        brandIdem.add(new mapView("b2b1", "miamia", drawableFore, i));
                        break;
                    }
                    case "Wolsey": {
                        Drawable drawableFore = getDrawable(R.drawable.map_wolsey);
                        brandIdem.add(new mapView("b2b1", "wolsey", drawableFore, i));
                        break;
                    }
                    case "Perng Yuh芃諭名品": {
                        Drawable drawableFore = getDrawable(R.drawable.map_perngyuh);
                        brandIdem.add(new mapView("b2b1", "perngyuh", drawableFore, i));
                        break;
                    }
                    case "COACH FACTORY": {
                        Drawable drawableFore = getDrawable(R.drawable.map_coach);
                        brandIdem.add(new mapView("b2lb", "coach", drawableFore, i));
                        break;
                    }
                    case "Roots": {
                        Drawable drawableFore = getDrawable(R.drawable.map_roots);
                        brandIdem.add(new mapView("b2lb", "roots", drawableFore, i));
                        break;
                    }
                    case "POLO RALPH LAUREN ": {
                        Drawable drawableFore = getDrawable(R.drawable.map_polo);
                        brandIdem.add(new mapView("b2lb", "poloRalphLauren", drawableFore, i));
                        break;
                    }
                    case "LACOSTE": {
                        Drawable drawableFore = getDrawable(R.drawable.map_lacoste);
                        brandIdem.add(new mapView("b2lb", "lacoste", drawableFore, i));
                        break;
                    }
                    case "EDWIN": {
                        Log.d(TAG, "onCreate: into edwin");
                        Drawable drawableFore = getDrawable(R.drawable.map_edwin);
                        brandIdem.add(new mapView("b1b1", "edwin", drawableFore, i));
                        break;
                    }
                    case "La new": {
                        Drawable drawableFore = getDrawable(R.drawable.map_lanew);
                        brandIdem.add(new mapView("b1b1", "lanew", drawableFore, i));
                        break;
                    }
                    case "BLUE WAY": {
                        Drawable drawableFore = getDrawable(R.drawable.map_blueway);
                        brandIdem.add(new mapView("b1b1", "blueway", drawableFore, i));
                        break;
                    }
                    case "寶雅生活館": {
                        Drawable drawableFore = getDrawable(R.drawable.map_poya);
                        brandIdem.add(new mapView("ab1", "poya", drawableFore, i));
                        break;
                    }
                    case "B1電梯":{
                        Log.d(TAG, "onStart: brand ignore B1電梯");
                        Drawable b1_ele = getDrawable(R.drawable.map_b2b1_elevator);
                        b2b1 = combineGraph(b1_ele, b2b1);
                    }
                    case "B1手扶梯": {
                        Log.d(TAG, "onStart: brand ignore B1手扶梯");
                        Drawable b1_hand = getDrawable(R.drawable.map_b2b1_hand);
                        b2b1 = combineGraph(b1_hand, b2b1);
                    }
                    case "LB電梯": {
                        Log.d(TAG, "onStart: brand ignore LB電梯");
                        Drawable b2_ele = getDrawable(R.drawable.map_b2lb_elevator);
                        b2lb = combineGraph(b2_ele, b2lb);
                    }
                    case "LB手扶梯":{
                        Log.d(TAG, "onStart: brand ignore LB手扶梯");
                        Drawable b2_hand = getDrawable(R.drawable.map_b2lb_hand);
                        b2lb = combineGraph(b2_hand, b2lb);
                    }
                    default: {
                        Log.d(TAG, "onStart: brand can't found");
                    }
                }
            }//construct brand item


            Log.d(TAG, "onCreate: brandItem" + brandIdem.size());
            for (int i =0; i<brandIdem.size();i++){
                Log.d(TAG, "brandItem contain: " + brandIdem.get(i).brandName);
            }


            //開始疊圖

            TextView floorText = (TextView) findViewById(R.id.floor_text);
            for (int i = 0; i < brandIdem.size(); i++) {
                String floor = brandIdem.get(i).getFloor();
                Log.d(TAG, "onCreate: foldgraph item " + i + " floor " + floor);
                switch (floor) {
                    case "b2b1":
                        b2b1 = combineGraph(brandIdem.get(i).drawableFore, b2b1);
                        if (i == 0) {
                            mapImage.setImageDrawable(b2b1);
                            floorText.setText("B2-B1");
                        }
                        break;
                    case "b2lb":
                        b2lb = combineGraph(brandIdem.get(i).drawableFore, b2lb);
                        if (i == 0) {
                            mapImage.setImageDrawable(b2lb);
                            floorText.setText("B2-LB");
                        }
                        break;
                    case "b1b1":
                        b1b1 = combineGraph(brandIdem.get(i).drawableFore, b1b1);
                        if (i == 0) {
                            mapImage.setImageDrawable(b1b1);
                            floorText.setText("B1-B1");
                        }
                        break;
                    case "ab1":
                        a_b1 = combineGraph(brandIdem.get(i).drawableFore, a_b1);
                        if (i == 0) {
                            mapImage.setImageDrawable(a_b1);
                            floorText.setText("A-B1");
                        }
                        break;
                }
            }


            //設定上下左右及縮圖
            Button up = (Button) findViewById(R.id.up);
            Button down = (Button) findViewById(R.id.down);
            Button left = (Button) findViewById(R.id.left);
            Button right = (Button) findViewById(R.id.right);

            finalB2lb = b2lb;
            finalB2b1 = b2b1;
            finalA_b1 = a_b1;
            finalB1b1 = b1b1;
            ImageView minimap = (ImageView) findViewById(R.id.minimap);


            if (floorText.getText().equals("B1-B1")) {
                up.setVisibility(View.GONE);
                down.setVisibility(View.GONE);
                minimap.setImageResource(R.drawable.minimap_b11);
            } else if (floorText.getText().equals("A-B1")) {
                up.setVisibility(View.GONE);
                down.setVisibility(View.GONE);
                left.setVisibility(View.GONE);
                minimap.setImageResource(R.drawable.minimap_ab1);
            } else if (floorText.getText().equals("B2-B1")) {
                right.setVisibility(View.GONE);
                down.setVisibility(View.GONE);
                minimap.setImageResource(R.drawable.minimap_b12);
            } else if (floorText.getText().equals("B1-LB")) {
                up.setVisibility(View.GONE);
                right.setVisibility(View.GONE);
                left.setVisibility(View.GONE);
                minimap.setImageResource(R.drawable.minimap_lb2);
            }

            up.setOnClickListener(new View.OnClickListener() {
                Button up = (Button) findViewById(R.id.up);
                Button down = (Button) findViewById(R.id.down);
                Button left = (Button) findViewById(R.id.left);
                Button right = (Button) findViewById(R.id.right);
                ImageView mapImage = (ImageView) findViewById(R.id.bigMap);
                TextView floorText = (TextView) findViewById(R.id.floor_text);
                ImageView minimap = (ImageView) findViewById(R.id.minimap);

                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: up button");
                    MediaPlayer click = MediaPlayer.create(MapActivity.this, R.raw.click);
                    click.start();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    if (floorText.getText().equals("B2-B1")) {
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

            down.setOnClickListener(new View.OnClickListener() {
                Button up = (Button) findViewById(R.id.up);
                Button down = (Button) findViewById(R.id.down);
                Button left = (Button) findViewById(R.id.left);
                Button right = (Button) findViewById(R.id.right);
                ImageView mapImage = findViewById(R.id.bigMap);
                TextView floorText = (TextView) findViewById(R.id.floor_text);
                ImageView minimap = (ImageView) findViewById(R.id.minimap);

                @Override
                public void onClick(View view) {
                    MediaPlayer click = MediaPlayer.create(MapActivity.this, R.raw.click);
                    click.start();
                    Log.d(TAG, "onClick: down button");

                    if (floorText.getText().equals("B2-LB")) {
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

            left.setOnClickListener(new View.OnClickListener() {
                Button up = (Button) findViewById(R.id.up);
                Button down = (Button) findViewById(R.id.down);
                Button left = (Button) findViewById(R.id.left);
                Button right = (Button) findViewById(R.id.right);
                ImageView mapImage = findViewById(R.id.bigMap);
                TextView floorText = (TextView) findViewById(R.id.floor_text);
                ImageView minimap = (ImageView) findViewById(R.id.minimap);

                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: left button");
                    MediaPlayer click = MediaPlayer.create(MapActivity.this, R.raw.click);
                    click.start();
                    if (floorText.getText().equals("B1-B1")) {
                        floorText.setText("A-B1");
                        mapImage.setImageDrawable(finalA_b1);
                        up.setVisibility(View.GONE);
                        left.setVisibility(View.GONE);
                        down.setVisibility(View.GONE);
                        right.setVisibility(View.VISIBLE);
                        minimap.setImageResource(R.drawable.minimap_ab1);
                    } else if (floorText.getText().equals("B2-B1")) {
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
            right.setOnClickListener(new View.OnClickListener() {
                Button up = (Button) findViewById(R.id.up);
                Button down = (Button) findViewById(R.id.down);
                Button left = (Button) findViewById(R.id.left);
                Button right = (Button) findViewById(R.id.right);
                ImageView mapImage = findViewById(R.id.bigMap);
                TextView floorText = (TextView) findViewById(R.id.floor_text);
                ImageView minimap = (ImageView) findViewById(R.id.minimap);

                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: right button");
                    MediaPlayer click = MediaPlayer.create(MapActivity.this, R.raw.click);
                    click.start();
                    if (floorText.getText().equals("A-B1")) {
                        floorText.setText("B1-B1");
                        mapImage.setImageDrawable(finalB1b1);
                        up.setVisibility(View.GONE);
                        left.setVisibility(View.VISIBLE);
                        right.setVisibility(View.VISIBLE);
                        down.setVisibility(View.GONE);
                        minimap.setImageResource(R.drawable.minimap_b11);
                    } else if (floorText.getText().equals("B1-B1")) {
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
        
    }

    /*
    check if the target is in temi's location list
    @param: (String) target(ID)
    @return: true(in the list)/false
    * */
    private boolean checkInLocations(String des){
     //   for (String location : Robot.getInstance().getLocations()) {
     //       if (location.equals(des)) {
     //           return true;
     //       }
     //   }
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        robot.removeOnDetectionStateChangedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//must store the new intent unless getIntent() will return the old one
        Log.d(TAG, "onNewIntent: ");
    }

    private void toNextActivity(){
        Intent intent = new Intent(MapActivity.this, MovingActivity.class);
        intent.putExtra("task", "lead");
        intent.putExtra("target", target);
        Log.d(TAG, "toNextActivity: task-"+task+"taget-"+target);
        startActivity(intent);
    }

    private void findView(){
        goLead = (Button)findViewById(R.id.map_go);
    }
    private void addListener(){
        goLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: goLead button");
                MediaPlayer click = MediaPlayer.create(MapActivity.this, R.raw.click);
                click.start();
                Animation bounce = AnimationUtils.loadAnimation(MapActivity.this, R.anim.bounce_animation);
                goLead.startAnimation(bounce);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                // send intent
                toNextActivity();
            }
        });
    }

    @Override
    public void onDetectionStateChanged(int state) {
        Log.d(TAG, "onDetectionStateChanged: state =" + state);
        switch (state){
            case DETECTED:
                idle_count = 0;
                break;
            case IDLE:
                idle_count++;
                if(idle_count>2){
                    Intent intent = new Intent(MapActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
}