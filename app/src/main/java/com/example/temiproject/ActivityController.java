package com.example.temiproject;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityController extends AppCompatActivity {
    Handler handler = new Handler();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionbar	= getSupportActionBar();
        if	(actionbar	!=	null)	{
            actionbar.hide();
        }


    }




        //public void delay(int delayTime){
    //    handler.postDelayed(new Runnable(){
//
    //        @Override
    //        public void run() {
    //        }}, delayTime);
    //}
    //控制聲音 動畫 如果沒有機器人測試時的function都放在這裡

}
