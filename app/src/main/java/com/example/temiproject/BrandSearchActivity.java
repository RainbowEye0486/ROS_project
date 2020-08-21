package com.example.temiproject;

import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BrandSearchActivity extends ActivityController {

    private static final String TAG = "BrandSearchActivity";

    List<Branditem> lstBrand;
    List<Beacon> lstbeacon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_search);
        Button brandSearch = (Button)findViewById(R.id.brand_search_btn);
        Button goMap = (Button)findViewById(R.id.brandtomap_btn);
        goMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: brand search button");
                Intent intent = new Intent(BrandSearchActivity.this, MapActivity.class);
                String[] order = new String[5];
                for(int m=0;m<lstbeacon.size();m++){
                    if (lstbeacon.size()>5){
                        break;
                    }
                    else {
                        order[m] = lstbeacon.get(m).title;
                    }
                }
                for (int b =0; b<5; b++){
                    Log.d(TAG, "onClick: " + order[b]);
                }
                intent.putExtra("order", order);
                startActivity(intent);
            }
        });

        lstBrand = new ArrayList<>();
        lstbeacon = new ArrayList<>();
        Branditem perngyuh = new Branditem("perngyuh", R.string.perngyuh,R.drawable.thumbnail_perngyuh, R.drawable.card_perngyuh);
        Branditem cosmed = new Branditem("cosmed",R.string.cosmed ,R.drawable.thumbnail_cosmed, R.drawable.card_cosmed);
        Branditem wolsey = new Branditem("wolsey", R.string.wolsey,R.drawable.thumbnail_wolsey, R.drawable.card_wolsey);
        Branditem miamia = new Branditem("miamia", R.string.miamia,R.drawable.thumbnail_miamia, R.drawable.card_miamia);
        Branditem coach = new Branditem("coach", R.string.coach,R.drawable.thumbnail_coach, R.drawable.card_coach);
        Branditem polo = new Branditem("poloRalphLauren", R.string.poloraphlaren,R.drawable.thumbnail_poloralphlauren, R.drawable.card_poloralphlauren);
        Branditem roots = new Branditem("roots", R.string.roots,R.drawable.thumbnail_roots, R.drawable.card_roots);
        Branditem lacoste = new Branditem("lacoste",R.string.lacoste ,R.drawable.thumbnail_lacoste, R.drawable.card_lacoste);
        Branditem lanew = new Branditem("lanew", R.string.lanew,R.drawable.thumbnail_lanew, R.drawable.card_lanew);
        Branditem blueway = new Branditem("blueway", R.string.blueway, R.drawable.thumbnail_blueway, R.drawable.card_blueway);
        Branditem edwin = new Branditem("edwin", R.string.edwin,R.drawable.thumbnail_edwin, R.drawable.card_edwin);
        Branditem poya = new Branditem("poya", R.string.poya,R.drawable.thumbnail_poya, R.drawable.card_poya);

        lstBrand.add(perngyuh);
        lstBrand.add(cosmed);
        lstBrand.add(wolsey);
        lstBrand.add(miamia);
        lstBrand.add(coach);
        lstBrand.add(polo);
        lstBrand.add(roots);
        lstBrand.add(lacoste);
        lstBrand.add(lanew);
        lstBrand.add(blueway);
        lstBrand.add(edwin);
        lstBrand.add(poya);

        RecyclerView myrycle = (RecyclerView) findViewById(R.id.brand_recycleView);
        BranditemAdapter myAdapter = new BranditemAdapter(this, BrandSearchActivity.this, lstBrand, lstbeacon);
        Log.d(TAG, "onCreate: lstbeacon" + lstbeacon);
        myrycle.setLayoutManager(new GridLayoutManager(this, 5));
        myrycle.setAdapter(myAdapter);

        
        //處理詳細的顯示


        Button beacon1_btn = (Button)findViewById(R.id.beacon1_cancel);
        Button beacon2_btn = (Button)findViewById(R.id.beacon2_cancel);
        Button beacon3_btn = (Button)findViewById(R.id.beacon3_cancel);
        Button beacon4_btn = (Button)findViewById(R.id.beacon4_cancel);
        Button beacon5_btn = (Button)findViewById(R.id.beacon5_cancel);

        beacon1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: delete beacon1");
                lstbeacon.remove(lstbeacon.get(0));
                flushBeacon();
                Log.d(TAG, "onClick: "+lstbeacon);
            }
        });
        beacon2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: delete beacon2");
                lstbeacon.remove(lstbeacon.get(1));
                flushBeacon();
                Log.d(TAG, "onClick: "+lstbeacon);
            }
        });
        beacon3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: delete beacon3");
                lstbeacon.remove(lstbeacon.get(2));
                flushBeacon();
                Log.d(TAG, "onClick: "+lstbeacon);
            }
        });
        beacon4_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: delete beacon4");
                lstbeacon.remove(lstbeacon.get(3));
                flushBeacon();
                Log.d(TAG, "onClick: "+lstbeacon);
            }
        });
        beacon5_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: delete beacon5");
                lstbeacon.remove(lstbeacon.get(4));
                flushBeacon();
                Log.d(TAG, "onClick: "+lstbeacon);
            }
        });
    }

    public void flushBeacon(){ //重新整理預排清單
        int length = lstbeacon.size();
        Log.d(TAG, "flushBeacon: length" + length);
        Button beacon1_btn = (Button)findViewById(R.id.beacon1_cancel);
        Button beacon2_btn = (Button)findViewById(R.id.beacon2_cancel);
        Button beacon3_btn = (Button)findViewById(R.id.beacon3_cancel);
        Button beacon4_btn = (Button)findViewById(R.id.beacon4_cancel);
        Button beacon5_btn = (Button)findViewById(R.id.beacon5_cancel);
        Button schedule_btn = (Button)findViewById(R.id.brandtomap_btn);
        ImageView beacon1 = (ImageView)findViewById(R.id.beacon1);
        ImageView beacon2 = (ImageView)findViewById(R.id.beacon2);
        ImageView beacon3 = (ImageView)findViewById(R.id.beacon3);
        ImageView beacon4 = (ImageView)findViewById(R.id.beacon4);
        ImageView beacon5 = (ImageView)findViewById(R.id.beacon5);
        TextView beacon1_txt = (TextView)findViewById(R.id.beacon1_txt);
        TextView beacon2_txt = (TextView)findViewById(R.id.beacon2_txt);
        TextView beacon3_txt = (TextView)findViewById(R.id.beacon3_txt);
        TextView beacon4_txt = (TextView)findViewById(R.id.beacon4_txt);
        TextView beacon5_txt = (TextView)findViewById(R.id.beacon5_txt);
        if (length == 0){
            schedule_btn.setEnabled(false);
        }
        else{
            schedule_btn.setEnabled(true);
        }
        beacon1_btn.setVisibility(View.INVISIBLE);
        beacon2_btn.setVisibility(View.INVISIBLE);
        beacon3_btn.setVisibility(View.INVISIBLE);
        beacon4_btn.setVisibility(View.INVISIBLE);
        beacon5_btn.setVisibility(View.INVISIBLE);
        beacon1.setVisibility(View.INVISIBLE);
        beacon2.setVisibility(View.INVISIBLE);
        beacon3.setVisibility(View.INVISIBLE);
        beacon4.setVisibility(View.INVISIBLE);
        beacon5.setVisibility(View.INVISIBLE);
        beacon1_txt.setVisibility(View.INVISIBLE);
        beacon2_txt.setVisibility(View.INVISIBLE);
        beacon3_txt.setVisibility(View.INVISIBLE);
        beacon4_txt.setVisibility(View.INVISIBLE);
        beacon5_txt.setVisibility(View.INVISIBLE);
        if(length == 0)return;

        beacon1.setVisibility(View.VISIBLE);
        beacon1_txt.setVisibility(View.VISIBLE);
        beacon1_txt.setText(lstbeacon.get(0).title);
        beacon1_btn.setVisibility(View.VISIBLE);
        if(length == 1)return;
        beacon2.setVisibility(View.VISIBLE);
        beacon2_txt.setVisibility(View.VISIBLE);
        beacon2_txt.setText(lstbeacon.get(1).title);
        beacon2_btn.setVisibility(View.VISIBLE);
        if(length == 2)return;
        beacon3.setVisibility(View.VISIBLE);
        beacon3_txt.setVisibility(View.VISIBLE);
        beacon3_txt.setText(lstbeacon.get(2).title);
        beacon3_btn.setVisibility(View.VISIBLE);
        if(length == 3)return;
        beacon4.setVisibility(View.VISIBLE);
        beacon4_txt.setVisibility(View.VISIBLE);
        beacon4_txt.setText(lstbeacon.get(3).title);
        beacon4_btn.setVisibility(View.VISIBLE);
        if(length == 4)return;
        beacon5.setVisibility(View.VISIBLE);
        beacon5_txt.setVisibility(View.VISIBLE);
        beacon5_txt.setText(lstbeacon.get(4).title);
        beacon5_btn.setVisibility(View.VISIBLE);
    }


    @CallSuper
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() ==  MotionEvent.ACTION_DOWN ) {
            View view = getCurrentFocus();
            if (isShouldHideKeyBord(view, ev)) {
                hideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判定当前是否需要隐藏
     */
    protected boolean isShouldHideKeyBord(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom);
            //return !(ev.getY() > top && ev.getY() < bottom);
        }
        return false;
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}

class Beacon{
    String title;
    int textSize;
    public Beacon (String title, int textSize){
        this.title = title;
        this.textSize = textSize;
    }

}