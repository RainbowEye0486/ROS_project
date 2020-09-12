package com.example.temiproject;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.CheckResult;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.listeners.OnDetectionStateChangedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;
import com.robotemi.sdk.permission.Permission;

import java.util.Collections;

public class ActivityController extends AppCompatActivity implements
        OnDetectionStateChangedListener,
        OnRobotReadyListener {
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
        if(!robot.isSelectedKioskApp()){
            robot.requestToBeKioskApp();
        }
        // disable the popup screen when moving
        robot.toggleNavigationBillboard(true);
        // hardware button disable
        if(!robot.isHardButtonsDisabled()){
            robot.setHardButtonsDisabled(true);
        }
        // hide topbar
        // use refreshTemiUi instead
//        robot.hideTopBar();
        // top badge
        turnTopBadgeOff(robot);


    }

    protected void turnTopBadgeOff(Robot robot) {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return;
        }
        robot.setTopBadgeEnabled(false);
        Log.d(TAG, "turnTopBadgeOff: enable:" + robot.isTopBadgeEnabled());
    }

    protected boolean turnDevelopMode(Robot robot){
        // topbar
        robot.showTopBar();
        // hard btn
        if(robot.isHardButtonsDisabled()) {
            robot.setHardButtonsDisabled(false);
        }
        Log.d(TAG, "turnDevelopMode: isHardButtonsDisabled"+robot.isHardButtonsDisabled());
        // nav billboard
        robot.toggleNavigationBillboard(false);

        // top badge
        robot.setTopBadgeEnabled(true);
        return true;
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

    @Override
    protected void onStart() {
        super.onStart();
        Robot.getInstance().addOnRobotReadyListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Robot.getInstance().removeOnRobotReadyListener(this);
    }

    @Override
    public void onDetectionStateChanged(int i) {
        Log.d(TAG, "onDetectionStateChanged: state" + i);
    }

    @Override
    public void onRobotReady(boolean isReady ) {
        if(isReady){
            Log.d(TAG, "onRobotReady: ");
            refreshTemiUi();
        }
    }

    private void refreshTemiUi() {
        try {
            ActivityInfo activityInfo = getPackageManager()
                    .getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            Robot.getInstance().onStart(activityInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
