package com.example.temiproject;

import androidx.annotation.CallSuper;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BrandSearchActivity extends ActivityController {

    private static final String TAG = "BrandSearchActivity";

    List<Branditem> lstBrand;
    List<Beacon> lstbeacon;

    private SearchView svBrand;
    private AutoCompleteTextView actvSearch;
    CursorAdapter suggAdapter;
    Cursor mCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_search);
        openDB();
        initAutoComplete();
//        initSearchView();
        Button brandSearch = (Button)findViewById(R.id.brand_search_btn);
        final Button goMap = (Button)findViewById(R.id.brandtomap_btn);
        goMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: brand search button");
                MediaPlayer click = MediaPlayer.create(BrandSearchActivity.this, R.raw.click);
                click.start();
                Animation bounce = AnimationUtils.loadAnimation(BrandSearchActivity.this, R.anim.bounce_animation);
                goMap.startAnimation(bounce);
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
                intent.putExtra("task", "brand");
                startActivity(intent);
            }
        });

        final Button home_btn = (Button)findViewById(R.id.home_btn);
        final Button return_btn = (Button)findViewById(R.id.return_btn);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation bounce = AnimationUtils.loadAnimation(BrandSearchActivity.this, R.anim.bounce_animation);
                MediaPlayer click = MediaPlayer.create(BrandSearchActivity.this, R.raw.click);
                click.start();
                home_btn.startAnimation(bounce);
                Intent intent = new Intent(BrandSearchActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                Log.d(TAG, "onClick: Home button");
            }
        });
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrandSearchActivity.this, GuildPointActivity.class);

                MediaPlayer click = MediaPlayer.create(BrandSearchActivity.this, R.raw.click);
                click.start();
                Animation bounce = AnimationUtils.loadAnimation(BrandSearchActivity.this, R.anim.bounce_animation);
                return_btn.startAnimation(bounce);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                Log.d(TAG, "onClick: Return button");
            }
        });


        lstBrand = new ArrayList<>();
        lstbeacon = new ArrayList<>();
        List<String> stringBrand = new ArrayList<>();
        String[] strelement={"perngyuh", "cosmed", "wolsey", "miamia", "coach", "polo", "roots", "lacoste", "lanew", "blueway", "edwin", "poya"};
        for (int i=0;i<strelement.length;i++){
            stringBrand.add(strelement[i]);
        }
        flushicon(stringBrand, lstBrand);

        brandSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change string array when onclick
                List<String> stringBrand = new ArrayList<>();
                String[] strelement={"perngyuh", "cosmed", "wolsey", "miamia", "coach", "polo", "roots", "lacoste", "lanew", "blueway", "edwin", "poya"};
                for (int i=0;i<strelement.length;i++){
                    stringBrand.add(strelement[i]);
                }
                flushicon(stringBrand, lstBrand);

            }
        });


        //處理詳細的顯示


        final Button beacon1_btn = (Button)findViewById(R.id.beacon1_cancel);
        final Button beacon2_btn = (Button)findViewById(R.id.beacon2_cancel);
        final Button beacon3_btn = (Button)findViewById(R.id.beacon3_cancel);
        final Button beacon4_btn = (Button)findViewById(R.id.beacon4_cancel);
        final Button beacon5_btn = (Button)findViewById(R.id.beacon5_cancel);

        beacon1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: delete beacon1");
                MediaPlayer click = MediaPlayer.create(BrandSearchActivity.this, R.raw.click);
                click.start();

                lstbeacon.remove(lstbeacon.get(0));
                flushBeacon();
                Log.d(TAG, "onClick: "+lstbeacon);
            }
        });
        beacon2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: delete beacon2");
                MediaPlayer click = MediaPlayer.create(BrandSearchActivity.this, R.raw.click);
                click.start();

                lstbeacon.remove(lstbeacon.get(1));
                flushBeacon();
                Log.d(TAG, "onClick: "+lstbeacon);
            }
        });
        beacon3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: delete beacon3");
                MediaPlayer click = MediaPlayer.create(BrandSearchActivity.this, R.raw.click);
                click.start();

                lstbeacon.remove(lstbeacon.get(2));
                flushBeacon();
                Log.d(TAG, "onClick: "+lstbeacon);
            }
        });
        beacon4_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: delete beacon4");
                MediaPlayer click = MediaPlayer.create(BrandSearchActivity.this, R.raw.click);
                click.start();

                lstbeacon.remove(lstbeacon.get(3));
                flushBeacon();
                Log.d(TAG, "onClick: "+lstbeacon);
            }
        });
        beacon5_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: delete beacon5");
                MediaPlayer click = MediaPlayer.create(BrandSearchActivity.this, R.raw.click);
                click.start();

                lstbeacon.remove(lstbeacon.get(4));
                flushBeacon();
                Log.d(TAG, "onClick: "+lstbeacon);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    public void flushBeacon() { //重新整理預排清單
        int length = lstbeacon.size();
        Log.d(TAG, "flushBeacon: length" + length);
        Button beacon1_btn = (Button) findViewById(R.id.beacon1_cancel);
        Button beacon2_btn = (Button) findViewById(R.id.beacon2_cancel);
        Button beacon3_btn = (Button) findViewById(R.id.beacon3_cancel);
        Button beacon4_btn = (Button) findViewById(R.id.beacon4_cancel);
        Button beacon5_btn = (Button) findViewById(R.id.beacon5_cancel);
        Button schedule_btn = (Button) findViewById(R.id.brandtomap_btn);
        ImageView beacon1 = (ImageView) findViewById(R.id.beacon1);
        ImageView beacon2 = (ImageView) findViewById(R.id.beacon2);
        ImageView beacon3 = (ImageView) findViewById(R.id.beacon3);
        ImageView beacon4 = (ImageView) findViewById(R.id.beacon4);
        ImageView beacon5 = (ImageView) findViewById(R.id.beacon5);
        TextView beacon1_txt = (TextView) findViewById(R.id.beacon1_txt);
        TextView beacon2_txt = (TextView) findViewById(R.id.beacon2_txt);
        TextView beacon3_txt = (TextView) findViewById(R.id.beacon3_txt);
        TextView beacon4_txt = (TextView) findViewById(R.id.beacon4_txt);
        TextView beacon5_txt = (TextView) findViewById(R.id.beacon5_txt);
        if (length == 0) {
            schedule_btn.setEnabled(false);
        } else {
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
        if (length == 0) return;
        beacon1.setVisibility(View.VISIBLE);
        beacon1_txt.setVisibility(View.VISIBLE);
        beacon1_txt.setText(lstbeacon.get(0).title);
        beacon1_btn.setVisibility(View.VISIBLE);
        if (length == 1) return;
        beacon2.setVisibility(View.VISIBLE);
        beacon2_txt.setVisibility(View.VISIBLE);
        beacon2_txt.setText(lstbeacon.get(1).title);
        beacon2_btn.setVisibility(View.VISIBLE);
        if (length == 2) return;
        beacon3.setVisibility(View.VISIBLE);
        beacon3_txt.setVisibility(View.VISIBLE);
        beacon3_txt.setText(lstbeacon.get(2).title);
        beacon3_btn.setVisibility(View.VISIBLE);
        if (length == 3) return;
        beacon4.setVisibility(View.VISIBLE);
        beacon4_txt.setVisibility(View.VISIBLE);
        beacon4_txt.setText(lstbeacon.get(3).title);
        beacon4_btn.setVisibility(View.VISIBLE);
        if (length == 4) return;
        beacon5.setVisibility(View.VISIBLE);
        beacon5_txt.setVisibility(View.VISIBLE);
        beacon5_txt.setText(lstbeacon.get(4).title);
        beacon5_btn.setVisibility(View.VISIBLE);
    }

    public void flushicon(List<String> stringBrand, List<Branditem> lstBrand){
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


            if (stringBrand.contains("blueway")){
                lstBrand.add(blueway);
            }
            if (stringBrand.contains("cosmed")){
              lstBrand.add(cosmed);
            }
            if (stringBrand.contains("coach")){
                lstBrand.add(coach);
            }
            if (stringBrand.contains("edwin")){
                lstBrand.add(edwin);
            }
            if (stringBrand.contains("miamia")){
                lstBrand.add(miamia);
            }
            if (stringBrand.contains("lacoste")){
                lstBrand.add(lacoste);
            }
            if (stringBrand.contains("lanew")){
                lstBrand.add(lanew);
            }
            if (stringBrand.contains("perngyuh")){
                lstBrand.add(perngyuh);
            }
            if (stringBrand.contains("polo")){
                lstBrand.add(polo);
            }
            if (stringBrand.contains("poya")){
                lstBrand.add(poya);
            }
            if (stringBrand.contains("roots")){
                lstBrand.add(roots);
            }
            if (stringBrand.contains("wolsey")){
                lstBrand.add(wolsey);
            }














        RecyclerView myrycle = (RecyclerView) findViewById(R.id.brand_recycleView);
        BranditemAdapter myAdapter = new BranditemAdapter(this, BrandSearchActivity.this, lstBrand, lstbeacon);
        Log.d(TAG, "onCreate: lstbeacon" + lstbeacon);
        myrycle.setLayoutManager(new GridLayoutManager(this, 5));
        myrycle.setAdapter(myAdapter);


    }

    private void initAutoComplete(){
        ArrayList<String> storeList = getStores();
        String[] stores = new String[storeList.size()];
        storeList.toArray(stores);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, stores);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.actvBrand);
        textView.setAdapter(adapter);
        textView.setImeActionLabel("GO", KeyEvent.KEYCODE_ENTER);
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String query = (String) adapterView.getItemAtPosition(i);
                Log.d(TAG, "onItemClick: query"+query);
            }
        });
    }
    private ArrayList<String> getStores(){
        ArrayList<String> result = new ArrayList<String>();
        SQLiteDatabase db	=	DH.getWritableDatabase();
        Cursor dbCursor = db.rawQuery("SELECT cn_name FROM Store", null);
        while (dbCursor.moveToNext()) {
            result.add(dbCursor.getString(0));
        }
        return result;
    }
    private void initSearchView(){
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        svBrand = (SearchView)findViewById(R.id.svBrand) ;
        // Assumes current activity is the searchable activity
        svBrand.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        svBrand.setSubmitButtonEnabled(true);
        mCursor = null;
        // Defines a list of View IDs that will receive the Cursor columns for each row
        int [] viewIds = { android.R.id.text1 };
        // Defines a list of columns to retrieve from the Cursor and load into an output row
        String [] columNames = { SearchManager.SUGGEST_COLUMN_TEXT_1 };
        // Creates a new SimpleCursorAdapter
        suggAdapter = new SimpleCursorAdapter(
                getApplicationContext(),               // The application's Context object
                android.R.layout.simple_list_item_1,                  // A layout in XML for one row in the ListView
                mCursor,                               // The result from the query
                columNames,                      // A string array of column names in the cursor
                viewIds,                        // An integer array of view IDs in the row layout
                0);                                    // Flags (usually none are needed)

        // Sets the adapter for the ListView
        svBrand.setSuggestionsAdapter(suggAdapter);
        svBrand.setOnSuggestionListener(new SearchView.OnSuggestionListener(){

            @Override
            public boolean onSuggestionSelect(int position) {
                Log.d(TAG, "onSuggestionSelect: ");
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                // true if the listener handles the event and
                // wants to override the default behavior of launching any intent or
                // submitting a search query specified on that item.
                Log.d(TAG, "onSuggestionClick: "+String.valueOf(position));
                String query = null;
                handleSearch(query);
                return true;
            }
        });
        svBrand.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                //true if the query has been handled by the listener
                handleSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //false if the SearchView should perform the default action of
                //showing any suggestions if available
                Log.d(TAG, "onQueryTextChange: ");
                updateCursorAdapter(suggAdapter, newText);
                return true;
            }
        });

    }
    // When Query text change, update suggesstion
    private void updateCursorAdapter(CursorAdapter adapter, String newText){
//        ArrayList info = new ArrayList();
        Log.d(TAG, "updateCursorAdapter: ");
        SQLiteDatabase db	=	DH.getWritableDatabase();
        Cursor dbCursor = db.rawQuery("SELECT rowid _id, cn_name FROM Store WHERE cn_name LIKE ?",
                new String[]{newText+"%"});
        Log.d(TAG, "updateCursorAdapter: "+dbCursor.getColumnName(0));
        Log.d(TAG, "updateCursorAdapter: "+dbCursor.getColumnName(1));
        MatrixCursor mCursor = new MatrixCursor(
                new String[] {BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1}
        );
        while (dbCursor.moveToNext()) {
            mCursor.newRow()
                    .add("_id", dbCursor.getString(0))
                    .add("name", dbCursor.getString(1));
            String name = dbCursor.getString(1);
            Log.d(TAG, "updateCursorAdapter: "+name);
        }
        adapter.swapCursor(mCursor);
    }

    // when user submit or click on a suggestion
    private void handleSearch(String query){
        Log.d(TAG, "handleSearch: "+query);
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