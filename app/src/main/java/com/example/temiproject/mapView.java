package com.example.temiproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;


public class mapView {
    String floor;
    String brandName;
    Drawable drawableFore;
    int order;


    public mapView(String floor, String brandName,  Drawable drawableFore, int order) {
        this.floor = floor;
        this.brandName = brandName;
        this.drawableFore = drawableFore;
        this.order = order;
    }

    public String getFloor() {
        return floor;
    }

    public Drawable combineGraph(Drawable drawableBack){
        Bitmap bitmapFore = ((BitmapDrawable) drawableFore).getBitmap();
        Bitmap bitmapBack = ((BitmapDrawable) drawableBack).getBitmap();
        Bitmap scaledBitmapFore = Bitmap.createScaledBitmap(bitmapFore, 900 , 550 , true);
        Bitmap scaledBitmapBack = Bitmap.createScaledBitmap(bitmapBack, 900 , 550 , true);
        Bitmap combineImages = overlay(scaledBitmapBack, scaledBitmapFore);
        return new BitmapDrawable(combineImages);
    }

    public int getOrder() {
        return order;
    }

    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2)//疊圖用
    {
        try
        {
            Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
            Canvas canvas = new Canvas(bmOverlay);
            canvas.drawBitmap(bmp1, new Matrix(), null);
            canvas.drawBitmap(bmp2, 0, 0, null);
            return bmOverlay;
        } catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }
}
