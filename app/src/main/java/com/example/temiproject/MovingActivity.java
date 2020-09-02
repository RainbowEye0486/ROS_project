package com.example.temiproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;

public class MovingActivity extends ActivityController implements
        OnGoToLocationStatusChangedListener,
        Robot.TtsListener{



    private static final String TAG = "MovingActivity";
    // fot temi sdk
    private boolean canSpeak = true;
    String destination;
    String voice;
    char next_job = ' ';
    private Robot robot;
    private int abort_count;
    private int click_num = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moving);
        openDB();
        robot = Robot.getInstance();
//        keepTemiSafe(robot);
        Button develop_btn = (Button)findViewById(R.id.topbar_btn);
        develop_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                click_num ++;
                if (click_num>=10){
                    Log.d(TAG, "develop mode on ! ");
                    click_num = 0;
                    //top bar open
                    if(turnDevelopMode(robot)){
                        Toast.makeText(MovingActivity.this, "工作人員模式", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        setTemiListener();
        abort_count = 0;
        Intent intent = getIntent();
        String task	= intent.getStringExtra("task");
        Log.d(TAG, "onStart: task"+task);
        assert task != null;
        if (task.equals("picture")){
            next_job = 'p';
            destination = GUEST;
            voice = "拍照站立觸";
        }else if (task.equals("takePhoto")){
            next_job = 's';
            destination = CAMERA;
            voice = "拍攝位置";
        }else if (task.equals("lead")){
            next_job = 'l';
            destination = intent.getStringExtra("target");
            voice = storeIDToName(destination);
        }else if (task.equals("back")){
            next_job = 'b';
            destination = HOME;
            voice = "家";
        }
        Log.d(TAG, "onStart: des:"+destination);
        if(checkInLocations(destination)){
            robot.goTo(destination);
        }else{
            Toast.makeText(MovingActivity.this, "地點不存在", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        removeTemiListener();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//must store the new intent unless getIntent() will return the old one
    }

    protected void setTemiListener(){
        robot.addOnGoToLocationStatusChangedListener(this);
        robot.addTtsListener(this);
    }

    protected void removeTemiListener(){
        robot.removeOnGoToLocationStatusChangedListener(this);
        robot.removeTtsListener(this);
    }

    @Override
    public void onGoToLocationStatusChanged(@NotNull String location, @NotNull String status, int descriptionId, @NotNull String description) {
        Log.d("GoToStatusChanged", "status=" + status + ", descriptionId=" + descriptionId + ", description=" + description);
        switch (status) {
            case "start":
                Log.d(TAG, "onGoToLocationStatusChanged: voice"+voice);
                robot.speak(TtsRequest.create("現在前往"+ voice, false));
                break;

            case "calculating":
                if(canSpeak){
                    robot.speak(TtsRequest.create("等等喔，我思考一下", false));
                }
                break;
            case "going":
                abort_count = 0;
                try {
                    if((!destination.equals(CAMERA))&&(!destination.equals(HOME))){
                        robot.speak(TtsRequest.create("請跟著我", false));
                    }else{
                        robot.speak(TtsRequest.create("行進中，請借過一下喔", false));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case "complete":
                robot.speak(TtsRequest.create("到達目的地", false));
                robot.tiltAngle(45);
                toNextActivity();
                break;

            case "abort":
                Log.d(TAG, "onGoToLocationStatusChanged: abort:" + abort_count);
                String display = "前進失敗 descriptionId=" + descriptionId + "description=" + description;
                Log.d(TAG, "onGoToLocationStatusChanged: abort:"+display);
                Toast.makeText(MovingActivity.this, "前進失敗", Toast.LENGTH_SHORT).show();
                robot.speak(TtsRequest.create("前進失敗", false));
                if(abort_count>2){
                    abort_count++;
                    robot.speak(TtsRequest.create("重新嘗試", false));
                    robot.goTo(destination);
                }else{
                    // ??
                    Intent intent = new Intent(MovingActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                break;
        }
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

    private void toNextActivity(){
        if (next_job == ' ') { //default go home
            Intent intent2 = new Intent(MovingActivity.this, HomeActivity.class);
            startActivity(intent2);
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        }else if (next_job == 's') { //指定地點完，準備去拍照點
            Intent intent2 = new Intent(MovingActivity.this, CameraActivity.class);
            startActivity(intent2);
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        }else if (next_job == 'p'){ //帶領到合適拍照位置
            Intent intent2 = new Intent(MovingActivity.this, ArrivalActivity.class);
            startActivity(intent2);
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        }else if (next_job == 'l'){ //品牌設施導覽 前往中
            Intent intent2 = new Intent(MovingActivity.this, ThanksLeadingActivity.class);
            startActivity(intent2);
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        }else if (next_job == 'b'){ //導覽完畢，回去原位
            Intent intent2 = new Intent(MovingActivity.this, HomeActivity.class);
            startActivity(intent2);
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private boolean checkInLocations(String des){
        for (String location : Robot.getInstance().getLocations()) {
            if (location.equals(des)) {
                return true;
            }
        }
        return false;
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