package com.example.temiproject;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


public class AfterPhotoActivity extends ActivityController {

    Bitmap photo;
    ImageView ivQRcode;
    final String TAG = "AfterPhotoActivity";
    Button btQRcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_photo);
        findView();
        addListener();
        String path = getIntent().getStringExtra("picpath");//通過值"picpath"得到照片路徑
        final ImageView imageview = findViewById(R.id.preview_img);
        try{
            FileInputStream fis=new FileInputStream(path);//通過path把照片讀到文件輸入流中
            Bitmap bitmap= BitmapFactory.decodeStream(fis);//將輸入流解碼為bitmap
            Matrix matrix=new Matrix();//新建一個矩陣對象
            matrix.setRotate(360);//矩陣旋轉操作讓照片可以正對你。但仍存在左右對稱的問題
            //新建位圖，第2個参數至第5個参數表示位圖的大小，matrix中是旋轉後的位圖信息，並使bitmap變量指向新的位圖對象
            bitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            //將位圖展示在imageview上
            imageview.setImageBitmap(bitmap);}
        catch (FileNotFoundException e){e.printStackTrace();}
        final Bitmap bitmap = spin(BitmapFactory.decodeFile(path));



        //photo folded

        //rotate

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
                Drawable photoplus = combineGraph(frame1, frame1);
                photo = ((BitmapDrawable)photoplus).getBitmap();
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
                Drawable photoplus = combineGraph(frame2, frame1);
                photo = ((BitmapDrawable)photoplus).getBitmap();
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
                Drawable photoplus = combineGraph(frame3, frame1);
                photo = ((BitmapDrawable)photoplus).getBitmap();
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
                Drawable photoplus = combineGraph(frame4, frame1);
                photo = ((BitmapDrawable)photoplus).getBitmap();
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
                Drawable photoplus = combineGraph(frame5, frame1);
                photo = ((BitmapDrawable)photoplus).getBitmap();
            }
        });

    }


    public Drawable combineGraph(Drawable drawableFore, Drawable drawableBack){
        Bitmap bitmapFore = ((BitmapDrawable) drawableFore).getBitmap();
        Bitmap bitmapBack = ((BitmapDrawable) drawableBack).getBitmap();
        Bitmap scaledBitmapFore = Bitmap.createScaledBitmap(bitmapFore, 1600 , 1200 , true);
        Bitmap scaledBitmapBack = Bitmap.createScaledBitmap(bitmapBack, 1600 , 1200 , true);
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

    public static Bitmap spin(Bitmap bmp1){
        Bitmap bmp = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(bmp, 0, 0, null);
        Matrix matrix = new Matrix();
        matrix.postRotate(180);
        Bitmap desbmp = Bitmap.createBitmap(bmp1, 0,0,bmp.getWidth(), bmp.getHeight(), matrix, true);
        canvas.drawBitmap(desbmp, 0, 0, null);
        return desbmp;
    }

    private void findView(){
        btQRcode = findViewById(R.id.qrcode_btn);
        ivQRcode = findViewById(R.id.qrcode_imv);
    }

    private void addListener(){
        btQRcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: btQRcode");
                Toast.makeText(AfterPhotoActivity.this, "sent", Toast.LENGTH_SHORT).show();
                String imageBase64 = encodeImage(photo);

                //loading-------------------------
                ImageView mask = (ImageView)findViewById(R.id.mask);
                mask.setVisibility(View.VISIBLE);
                GifImageView ImageView = findViewById(R.id.loading);
                try{
                    GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.loading);
                    ImageView.setImageDrawable(gifDrawable);
                    ImageView.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    e.printStackTrace();
                }
                //-----------------------------

                sendImage(imageBase64);
            }
        });
    }

    public String encodeImage(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //讀取圖片到ByteArrayOutputStream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //引數如果為100那麼就不壓縮
        byte[] bytes = baos.toByteArray();
        int image_size = (int)bytes.length/1048576*4/3;
        Log.d(TAG, "encodeImage: size: "+String.valueOf(image_size));
        final String base64_str = Base64.encodeToString(bytes,Base64.NO_WRAP);
//        //test
//        Bitmap converted = base64ToBitmap(base64_str);
//        imageView.setImageBitmap(converted);
//        Log.d(TAG, "encodeImage: "+base64_str);

        return base64_str;
    }

    private void sendImage(final String data){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.d(TAG, "run: OKHttp starts");
                    OkHttpClient client	= new OkHttpClient();
                    RequestBody requestBody	= new FormBody.Builder()
                            .add("image",	data)
                            .add("test",	"success")
                            .build();
                    Request request	= new Request.Builder()
                            .url("https://chendin.ml/temi/upload")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    showQRcode(responseData);
                    Log.d(TAG, "run: base64: " + data);
                }catch (Exception e){
                    showUploadFail();
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 顯示QRcode
    private void showQRcode(final String rt_url){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: showQRcode:"+rt_url);
                // generate QRcode
                BarcodeEncoder encoder = new BarcodeEncoder();
                try {
                    Bitmap bit = encoder.encodeBitmap(rt_url, BarcodeFormat.QR_CODE,
                            100, 100);
                    ivQRcode.setImageBitmap(bit);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

                //loading-------------------------
                ImageView mask = (ImageView)findViewById(R.id.mask);
                mask.setVisibility(View.GONE);
                GifImageView ImageView = findViewById(R.id.loading);
                try{
                    GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.loading);
                    ImageView.setImageDrawable(gifDrawable);
                    ImageView.setVisibility(View.GONE);
                }catch (Exception e){
                    e.printStackTrace();
                }
                //-----------------------------


            }
        });
    }

    // 顯示上傳失敗
    private void showUploadFail(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AfterPhotoActivity.this, "上傳失敗", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "run: showUploadFail");
            }
        });
    }


}