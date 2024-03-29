package com.example.temiproject;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
//import com.robotemi.sdk.Robot;
//import com.robotemi.sdk.TtsRequest;

public class ActivityController extends AppCompatActivity {
    final String TAG = "ActivityController";
    final String GUEST = "guest";
    final String CAMERA = "camera1";
    final String HOME = "home";
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

   // protected void keepTemiSafe(Robot robot){
   //     // disable the popup screen when moving
   //     robot.toggleNavigationBillboard(true);
   //     // hardware button disable
   //     if(!robot.isHardButtonsDisabled()){
   //         robot.setHardButtonsDisabled(true);
   //     }
   //     // hide topbar
   //     robot.hideTopBar();
    //}

 //   protected void turnDevelopMode(Robot robot){
 //       robot.showTopBar();
 //       if(robot.isHardButtonsDisabled()) {
 //           robot.setHardButtonsDisabled(false);
 //       }
 //   }

    public void speak(String sentence){
 //       Robot.getInstance().speak(TtsRequest.create(sentence, false));
    }



}
