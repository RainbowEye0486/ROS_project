package com.example.temiproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SelectPointActivity extends ActivityController {

    private static final String TAG = "SelectPoint";
    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_point);
        models = new ArrayList<>();
        models.add(new Model(R.drawable.photo, R.drawable.frame));
        models.add(new Model(R.drawable.photo2, R.drawable.frame2));
        models.add(new Model(R.drawable.photo3, R.drawable.frame3));
        int pos = -1;

        adapter = new Adapter(models, this);

        viewPager = findViewById(R.id.gallery);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(70, 0, 70, 0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("tag", "onPageScrolled: " + position);

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        Button button = (Button)findViewById(R.id.select_point_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectPointActivity.this, MovingActivity.class );
                int currentItem = viewPager.getCurrentItem();
                String index = String.valueOf(currentItem);
                intent.putExtra("task", index);
                Log.d(TAG, "onClick: send_btn , index" + currentItem);
                startActivity(intent);
            }
        });
    }
}