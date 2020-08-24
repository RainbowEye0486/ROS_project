package com.example.temiproject;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class AfterPhotoActivity extends ActivityController {

    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_photo);
        String path=getIntent().getStringExtra("picpath");//通過值"picpath"得到照片路徑
        final ImageView imageview=findViewById(R.id.preview_img);
        try{
            FileInputStream fis=new FileInputStream(path);//通過path把照片讀到文件輸入流中
            Bitmap bitmap= BitmapFactory.decodeStream(fis);//將輸入流解碼為bitmap
            Matrix matrix=new Matrix();//新建一個矩陣對象
            matrix.setRotate(180);//矩陣旋轉操作讓照片可以正對你。但仍存在左右對稱的問題
            //新建位圖，第2個参數至第5個参數表示位圖的大小，matrix中是旋轉後的位圖信息，並使bitmap變量指向新的位圖對象
            bitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            //將位圖展示在imageview上
            imageview.setImageBitmap(bitmap);}
        catch (FileNotFoundException e){e.printStackTrace();}
        final Bitmap bitmap=BitmapFactory.decodeFile(path);



        //photo folded
        Drawable frame1 = new BitmapDrawable(bitmap);
        imageview.setImageDrawable(frame1);
        final Button frame1_btn = (Button)findViewById(R.id.frame1_btn);
        final Button frame2_btn = (Button)findViewById(R.id.frame2_btn);
        final Button frame3_btn = (Button)findViewById(R.id.frame3_btn);
        final Button frame4_btn = (Button)findViewById(R.id.frame4_btn);
        final Button frame5_btn = (Button)findViewById(R.id.frame5_btn);


        frame1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation(AfterPhotoActivity.this, R.anim.bounce_animation);
                frame1_btn.startAnimation(bounce);
                Drawable frame1 = new BitmapDrawable(bitmap);
                frame1 = combineGraph(frame1, frame1);
                imageview.setImageDrawable(frame1);
                photo = overlay(bitmap, bitmap);
            }
        });

        frame2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation(AfterPhotoActivity.this, R.anim.bounce_animation);
                frame2_btn.startAnimation(bounce);
                Drawable frame1 = new BitmapDrawable(bitmap);
                Drawable frame2 = getDrawable(R.drawable.frame_1);
                frame2 = combineGraph(frame2, frame1);
                imageview.setImageDrawable(frame2);
                photo = overlay(bitmap, BitmapFactory.decodeResource(getResources(), R.drawable.frame_1));
            }
        });

        frame3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation(AfterPhotoActivity.this, R.anim.bounce_animation);
                frame3_btn.startAnimation(bounce);
                Drawable frame1 = new BitmapDrawable(bitmap);
                Drawable frame3 = getDrawable(R.drawable.frame_2);
                frame3 = combineGraph(frame3, frame1);
                imageview.setImageDrawable(frame3);
                photo = overlay(bitmap, BitmapFactory.decodeResource(getResources(), R.drawable.frame_2));
            }
        });

        frame4_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation(AfterPhotoActivity.this, R.anim.bounce_animation);
                frame4_btn.startAnimation(bounce);
                Drawable frame1 = new BitmapDrawable(bitmap);
                Drawable frame4 = getDrawable(R.drawable.frame_3);
                frame4 = combineGraph(frame4, frame1);
                imageview.setImageDrawable(frame4);
                photo = overlay(bitmap, BitmapFactory.decodeResource(getResources(), R.drawable.frame_3));
            }
        });

        frame5_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation(AfterPhotoActivity.this, R.anim.bounce_animation);
                frame5_btn.startAnimation(bounce);
                Drawable frame1 = new BitmapDrawable(bitmap);
                Drawable frame5 = getDrawable(R.drawable.frame_4);
                frame5 = combineGraph(frame5, frame1);
                imageview.setImageDrawable(frame5);
                photo = overlay(bitmap, BitmapFactory.decodeResource(getResources(), R.drawable.frame_4));
            }
        });



    }


    public Drawable combineGraph(Drawable drawableFore, Drawable drawableBack){
        Bitmap bitmapFore = ((BitmapDrawable) drawableFore).getBitmap();
        Bitmap bitmapBack = ((BitmapDrawable) drawableBack).getBitmap();
        Bitmap scaledBitmapFore = Bitmap.createScaledBitmap(bitmapFore, 900 , 550 , true);
        Bitmap scaledBitmapBack = Bitmap.createScaledBitmap(bitmapBack, 900 , 550 , true);
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


}