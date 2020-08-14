package com.example.temiproject;

import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends ActivityController {

    private static final String TAG = "MapActivity";
    private String[] order= {"edwin", "lanew", "miamia", "poya", null};

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Button goLead = (Button)findViewById(R.id.map_go);
        ImageView mapImage = (ImageView)findViewById(R.id.bigMap);
        goLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: goLead button");
                Intent intent = new Intent(MapActivity.this, MovingActivity.class);
                intent.putExtra("task", "lead");
            }
        });


        Drawable b2b1 = getDrawable(R.drawable.map_b2b1);
        Drawable b1b1 = getDrawable(R.drawable.map_b1b1);
        List<mapView> brandIdem = new ArrayList<>();
        for(int i = 0; i < order.length; i++){
            String brandName = order[i];
            if (brandName.equals("cosmed")){
                Drawable drawableFore = getResources().getDrawable( R.drawable.map_cosmed );
                brandIdem.add(new mapView("b2b1", "cosmed", drawableFore, i ));
            }
            else if (brandName.equals("miamia")){
                Drawable drawableFore = getResources().getDrawable( R.drawable.map_miamia );
                brandIdem.add(new mapView("b2b1", "miamia", drawableFore, i ));
            }
            else if (brandName.equals("wolsey")){
                Drawable drawableFore = getResources().getDrawable( R.drawable.map_wolsey );
                brandIdem.add(new mapView("b2b1", "wolsey", drawableFore, i ));
            }
            else if (brandName.equals("perngyuh")){
                Drawable drawableFore = getResources().getDrawable( R.drawable.map_perngyuh );
                brandIdem.add(new mapView("b2b1", "perngyuh", drawableFore, i ));
            }
            else if (brandName.equals("coach")){
                Drawable drawableFore = getResources().getDrawable( R.drawable.map_coach );
                brandIdem.add(new mapView("b2lb", "coach", drawableFore, i ));
            }
            else if (brandName.equals("roots")){
                Drawable drawableFore = getResources().getDrawable( R.drawable.map_roots );
                brandIdem.add(new mapView("b2lb", "roots", drawableFore, i ));
            }
            else if (brandName.equals("poloRalphLauren")){
                Drawable drawableFore = getResources().getDrawable( R.drawable.map_polo );
                brandIdem.add(new mapView("b2lb", "poloRalphLauren", drawableFore, i ));
            }
            else if (brandName.equals("lacoste")){
                Drawable drawableFore = getResources().getDrawable( R.drawable.map_lacoste );
                brandIdem.add(new mapView("b2lb", "lacoste", drawableFore, i ));
            }

            else if (brandName.equals("edwin")){
                Drawable drawableFore = getResources().getDrawable( R.drawable.map_edwin );
                brandIdem.add(new mapView("b1b1", "edwin", drawableFore, i ));
            }
            else if (brandName.equals("lanew")){
                Drawable drawableFore = getResources().getDrawable( R.drawable.map_lanew);
                brandIdem.add(new mapView("b1b1", "lanew", drawableFore, i ));
            }
            else if (brandName.equals("blueway")){
                Drawable drawableFore = getResources().getDrawable( R.drawable.map_blueway);
                brandIdem.add(new mapView("b1b1", "blueway", drawableFore, i ));
            }
            else if (brandName.equals("poya")){
                Drawable drawableFore = getResources().getDrawable( R.drawable.map_poya );
                brandIdem.add(new mapView("ab1", "poya", drawableFore, i ));
            }


            String ss=brandIdem.get(i).getFloor();
        }//construct brand item

        //folded graph



    }
}