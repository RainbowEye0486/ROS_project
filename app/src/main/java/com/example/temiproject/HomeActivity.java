package com.example.temiproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

import android.widget.ImageView;

//import com.robotemi.sdk.Robot;

import androidx.annotation.CheckResult;

import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.listeners.OnDetectionStateChangedListener;
import com.robotemi.sdk.permission.Permission;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class HomeActivity extends ActivityController implements
        Robot.TtsListener{

    // fot temi sdk
    private boolean canSpeak = true;

    private static final String TAG = "Home_page";
    private int click_num = 0;
    private int click_update = 0;

    // Database
    private Button btUpdateDB;
    private Robot robot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        robot = Robot.getInstance();
        findView();
        openDB();
        addListener();

        final Button photo_button = (Button)findViewById(R.id.home_photo_btn);
        final Button lead_button = (Button)findViewById(R.id.home_lead_btn);
        Button develop_btn = (Button)findViewById(R.id.develop_btn);

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
                    if(turnDevelopMode(robot)){
                        Toast.makeText(HomeActivity.this, "工作人員模式", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        keepTemiSafe(robot);
        turnDetectionModeOn();
        robot.addOnDetectionStateChangedListener(this);
        robot.addTtsListener(this);
        robot.tiltAngle(45);//相機傾角-25~55

    }

    @Override
    protected void onStop() {
        super.onStop();
        robot.removeOnDetectionStateChangedListener(this);
        robot.removeTtsListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
        turnDevelopMode(robot);
    }

    private void findView(){
        btUpdateDB = findViewById(R.id.update_btn);
    }

    private void addListener(){
        btUpdateDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click_update++;
                if (click_update>=10){
                    Log.d(TAG, "update database ! ");

                    click_update = 0;
                    Toast.makeText(HomeActivity.this, "send", Toast.LENGTH_SHORT).show();
                    loadEDAapi();
                }


            }
        });
    }

    private void loadEDAapi(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try{
                    URL url = new URL("http://www.edamall.com.tw/ashx/Brand/GetBrandInfo_Ayuda.ashx");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    updateToDB(response.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(reader!=null){
                        try {
                            reader.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void updateToDB(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(HomeActivity.this, "received", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "run: show response");
                initDB(response);
            }
        });
    }

    private void initDB(String response){
        try {
            Toast.makeText(HomeActivity.this, "initDB", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "initDB: start");
            SQLiteDatabase db = DH.getWritableDatabase();
            db.delete("Store", null, null);
            JSONObject json = new JSONObject(response);
            JSONArray allStore = (JSONArray) json.get("brandlist");
            for (int i = 0; i < allStore.length(); i++) {
                JSONObject store = allStore.getJSONObject(i);
                String store_id = store.getString("store_id");
                String cn_name = store.getString("bd_name");
                String en_name = store.getString("bd_name_en");
                Log.d(TAG, "run: " + store_id);
                Log.d(TAG, "run: "+ cn_name);
                JSONArray pics = store.getJSONArray("brand_pics");
                String big_pic=null, small_pic=null;
                for(int j=0; j<pics.length(); j++){
                    String file_type = pics.getJSONObject(j).getString("file_type");
                    if(file_type.equals("BIG")){
                        big_pic = pics.getJSONObject(j).getString("file_path");
                    }else{
                        small_pic = pics.getJSONObject(j).getString("file_path");
                    }
                    Log.d(TAG, "run: "+ big_pic);
                    Log.d(TAG, "run: "+ small_pic);
                }
                addToDB(store_id, cn_name, en_name, big_pic, small_pic);
            }

            // add toilet ,lift and escalator manually
            addToDB("toilet", "廁所" , "toilet", "0", "0");
            addToDB("bb12l", "B1電梯" , "lift", "0", "0");
            addToDB("bb12e", "B1手扶梯" , "escalator", "0", "0");
            addToDB("blb2l", "LB電梯" , "lift", "0", "0");
            addToDB("blb2e", "LB手扶梯" , "escalator", "0", "0");
            Log.d(TAG, "initDB: start update");
            String sqlstr = "update Store set id='00166' where id='**166'";
            db.execSQL(sqlstr);
            Toast.makeText(HomeActivity.this, "資料庫更新完成", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addToDB(String storeId, String cn_name, String en_name,String big_pic, String small_pic){
        SQLiteDatabase db = DH.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", storeId);
        values.put("cn_name", cn_name);
        values.put("en_name", en_name);
        values.put("big_pic", big_pic);
        values.put("small_pic", small_pic);
        db.insert("Store", null, values);
    }


    @Override
    public void onDetectionStateChanged(int state) {
        Log.d(TAG, "onDetectionStateChanged: state ="+ state);
        if (state == OnDetectionStateChangedListener.DETECTED && canSpeak) {
            speak("hello,我是Temi,需要幫忙拍照請按合照紀念，需要櫃位引導請按查詢及帶位");
        }
    }


    @Override
    public void onTtsStatusChanged(@NotNull TtsRequest ttsRequest) {
        // Do whatever you like upon the status changing. after the robot finishes speaking
        Log.d(TAG, "onTtsStatusChanged: ");
        switch (ttsRequest.getStatus()) {
            case STARTED:
                canSpeak = false;
                break;

            case COMPLETED:
                canSpeak = true;
                break;

            case ERROR:
                canSpeak = true;
                break;

        }
    }
}