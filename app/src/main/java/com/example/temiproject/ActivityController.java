package com.example.temiproject;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.CheckResult;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.permission.Permission;

import java.util.Collections;

public class ActivityController extends AppCompatActivity {
    final String TAG = "ActivityController";
    final String GUEST = "guest";
    final String CAMERA = "camera1";
    final String HOME = "home";
    private static final int REQUEST_CODE_NORMAL = 0;
    Handler handler = new Handler();

    protected DBHelper DH = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionbar	= getSupportActionBar();
        if	(actionbar	!=	null)	{
            actionbar.hide();
        }


    }


    protected void openDB(){
        DH = new DBHelper(this, "StoreList.db", null, 1);
        Log.d(TAG, "openDB: success");
    }

    protected void closeDB() {
        DH.close();
    }

    protected void keepTemiSafe(Robot robot){
        // disable the popup screen when moving
        robot.toggleNavigationBillboard(true);
        // hardware button disable
        if(!robot.isHardButtonsDisabled()){
            robot.setHardButtonsDisabled(true);
        }
        // hide topbar
        robot.hideTopBar();
    }

    protected void turnDevelopMode(Robot robot){
        // topbar
        robot.showTopBar();
        // hard btn
        if(robot.isHardButtonsDisabled()) {
            robot.setHardButtonsDisabled(false);
        }
        // nav billboard
        robot.toggleNavigationBillboard(false);
    }

    public void speak(String sentence){
        Robot.getInstance().speak(TtsRequest.create(sentence, false));
    }

    public void turnDetectionModeOn() {
        if(!Robot.getInstance().isDetectionModeOn()) {
            if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
                return;
            }
            Robot.getInstance().setDetectionModeOn(true);
        }
    }

    @CheckResult
    private boolean requestPermissionIfNeeded(Permission permission, int requestCode) {
        if (Robot.getInstance().checkSelfPermission(permission) == Permission.GRANTED) {
            return false;
        }
        Robot.getInstance().requestPermissions(Collections.singletonList(permission), requestCode);
        return true;
    }



}
