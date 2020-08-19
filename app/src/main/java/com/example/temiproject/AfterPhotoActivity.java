package com.example.temiproject;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class AfterPhotoActivity extends ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_photo);
        String path=getIntent().getStringExtra("picpath");//通過值"picpath"得到照片路徑
        ImageView imageview=findViewById(R.id.preview_img);
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
        //Bitmap bitmap=BitmapFactory.decodeFile(path);
    }
}