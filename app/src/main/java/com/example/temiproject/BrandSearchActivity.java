package com.example.temiproject;

import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class BrandSearchActivity extends ActivityController {

    private static final String TAG = "BrandSearchActivity";

    List<Branditem> lstBrand;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_search);
        Button brandSearch = (Button)findViewById(R.id.brand_search_btn);
        brandSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: brand search button");
                Intent intent = new Intent(BrandSearchActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        lstBrand = new ArrayList<>();
        lstBrand.add(new Branditem("Perngyuh", "brabrabra",R.drawable.thumbnail_perngyuh));
        lstBrand.add(new Branditem("cosmed", "brabrabra",R.drawable.thumbnail_cosmed));
        lstBrand.add(new Branditem("wolsley", "brabrabra",R.drawable.thumbnail_wolsey));
        lstBrand.add(new Branditem("miamia", "brabrabra",R.drawable.thumbnail_miamia));
        lstBrand.add(new Branditem("coach", "brabrabra",R.drawable.thumbnail_coach));
        lstBrand.add(new Branditem("poloRalphLauren", "brabrabra",R.drawable.thumbnail_poloRalphLauren));
        lstBrand.add(new Branditem("roots", "brabrabra",R.drawable.thumbnail_roots));
        lstBrand.add(new Branditem("lacoste", "brabrabra",R.drawable.thumbnail_lacoste));
        lstBrand.add(new Branditem("lanew", "brabrabra",R.drawable.thumbnail_lanew));
        //lstBrand.add(new Branditem("blueway", "brabrabra", R.drawable.Th));
        lstBrand.add(new Branditem("edwin", "brabrabra",R.drawable.thumbnail_edwin));
        lstBrand.add(new Branditem("poya", "brabrabra",R.drawable.thumbnail_poya));

        RecyclerView myrycle = (RecyclerView) findViewById(R.id.brand_recycleView);
        BranditemAdapter myAdapter = new BranditemAdapter(this, lstBrand);
        myrycle.setLayoutManager(new GridLayoutManager(this, 4));
        myrycle.setAdapter(myAdapter);


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