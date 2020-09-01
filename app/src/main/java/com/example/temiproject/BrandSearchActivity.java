package com.example.temiproject;

import androidx.annotation.CallSuper;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BrandSearchActivity extends ActivityController {

    private static final String TAG = "BrandSearchActivity";

    List<Branditem> lstBrand;
    List<Beacon> lstbeacon;

    private int weight;
    private ArrayList<String> sequence;
    final String start = "L4";
    private AutoCompleteTextView actvSearch;
    private Button btSearch;
    private Button goMap;
    private List<Position> map;
//    private SearchView svBrand;
//    CursorAdapter suggAdapter;
//    Cursor mCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_search);
        openDB();
        loadMap();
        findView();
        addListener();



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
        String[] strelement={"Perng Yuh芃諭名品", "康是美", "Wolsey", "mia mia", "COACH FACTORY", "POLO RALPH LAUREN "
                , "Roots", "LACOSTE", "La new", "BLUE WAY", "EDWIN", "寶雅生活館"};
        for (int i=0;i<strelement.length;i++){
            stringBrand.add(strelement[i]);
        }
        flushicon(stringBrand, lstBrand);


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
        Branditem perngyuh = new Branditem("  Perng Yuh\n芃諭名品  ", R.string.perngyuh,R.drawable.thumbnail_perngyuh, R.drawable.card_perngyuh);
        Branditem cosmed = new Branditem(" 康是美 ",R.string.cosmed ,R.drawable.thumbnail_cosmed, R.drawable.card_cosmed);
        Branditem wolsey = new Branditem("  Wolsey  ", R.string.wolsey,R.drawable.thumbnail_wolsey, R.drawable.card_wolsey);
        Branditem miamia = new Branditem(" mia mia" , R.string.miamia,R.drawable.thumbnail_miamia, R.drawable.card_miamia);
        Branditem coach = new Branditem("  COACH \nFACTORY  ", R.string.coach,R.drawable.thumbnail_coach, R.drawable.card_coach);
        Branditem polo = new Branditem("  POLO RALPH\n LAUREN  ", R.string.poloraphlaren,R.drawable.thumbnail_poloralphlauren, R.drawable.card_poloralphlauren);
        Branditem roots = new Branditem(" Roots ", R.string.roots,R.drawable.thumbnail_roots, R.drawable.card_roots);
        Branditem lacoste = new Branditem("  LACOSTE  ",R.string.lacoste ,R.drawable.thumbnail_lacoste, R.drawable.card_lacoste);
        Branditem lanew = new Branditem("  La new  ", R.string.lanew,R.drawable.thumbnail_lanew, R.drawable.card_lanew);
        Branditem blueway = new Branditem("  BLUE WAY  ", R.string.blueway, R.drawable.thumbnail_blueway, R.drawable.card_blueway);
        Branditem edwin = new Branditem(" EDWIN ", R.string.edwin,R.drawable.thumbnail_edwin, R.drawable.card_edwin);
        Branditem poya = new Branditem("  寶雅生活館  ", R.string.poya,R.drawable.thumbnail_poya, R.drawable.card_poya);
        lstBrand.clear();

            if (stringBrand.contains("BLUE WAY")){
                lstBrand.add(blueway);
            }
            if (stringBrand.contains("康是美")){
              lstBrand.add(cosmed);
            }
            if (stringBrand.contains("COACH FACTORY")){
                lstBrand.add(coach);
            }
            if (stringBrand.contains("EDWIN")){
                lstBrand.add(edwin);
            }
            if (stringBrand.contains("mia mia")){
                lstBrand.add(miamia);
            }
            if (stringBrand.contains("LACOSTE")){
                lstBrand.add(lacoste);
            }
            if (stringBrand.contains("La new")){
                lstBrand.add(lanew);
            }
            if (stringBrand.contains("Perng Yuh芃諭名品")){
                lstBrand.add(perngyuh);
            }
            if (stringBrand.contains("POLO RALPH LAUREN ")){
                lstBrand.add(polo);
            }
            if (stringBrand.contains("寶雅生活館")){
                lstBrand.add(poya);
            }
            if (stringBrand.contains("Roots")){
                lstBrand.add(roots);
            }
            if (stringBrand.contains("Wolsey")){
                lstBrand.add(wolsey);
            }

        RecyclerView myrycle = (RecyclerView) findViewById(R.id.brand_recycleView);
        BranditemAdapter myAdapter = new BranditemAdapter(this, BrandSearchActivity.this, lstBrand, lstbeacon);
        Log.d(TAG, "onCreate: lstbeacon" + lstbeacon);
        myrycle.setLayoutManager(new GridLayoutManager(this, 5));
        myrycle.setAdapter(myAdapter);


    }

    private void findView(){
        actvSearch = (AutoCompleteTextView)findViewById(R.id.actvBrand);
        goMap = (Button)findViewById(R.id.brandtomap_btn);
        btSearch = (Button)findViewById(R.id.brand_search_btn);
    }

    private void addListener(){
        initAutoComplete();
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = actvSearch.getText().toString();
                handleSearch(query);
            }
        });
        goMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: brand search button");
                MediaPlayer click = MediaPlayer.create(BrandSearchActivity.this, R.raw.click);
                click.start();
                Animation bounce = AnimationUtils.loadAnimation(BrandSearchActivity.this, R.anim.bounce_animation);
                goMap.startAnimation(bounce);
                // lstbeacon to Position
                String[] stores = new String[lstbeacon.size()];
                for(int i=0;i<lstbeacon.size();i++){
                    stores[i] = lstbeacon.get(i).title;
                    Log.d(TAG, "onClick: store-"+stores[i]);
                }
                ArrayList<Position> positions = mapToPos(stores);

                // plan the route
                positions = routePlan(positions);

                // test

                for(Position pos: positions){
                    Log.d(TAG, "onClick: "+ pos.getName());
                    Log.d(TAG, "onClick: "+ pos.getStores());
                    Log.d(TAG, "onClick: "+ pos.getLoc());
                }


                // intent
                Intent intent = new Intent(BrandSearchActivity.this, MapActivity.class);
                intent.putParcelableArrayListExtra("route",positions);
                intent.putExtra("task", "brand");
                startActivity(intent);
            }
        });
    }

    // initialize autocompletetextview
    private void initAutoComplete(){
        Log.d(TAG, "initAutoComplete: ");
        ArrayList<String> storeList = getStores();

        String[] stores = new String[storeList.size()];
        storeList.toArray(stores);
        addRightCancelDrawable(actvSearch);
       // String[] stores = {"a", "b", "c"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, stores);

        actvSearch.setAdapter(adapter);
        actvSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH /*|| EditorInfo.IME_ACTION_UNSPECIFIED==actionId*/) {
                    // Do whatever you want here
                    String query = actvSearch.getText().toString();
                    handleSearch(query);
                    return true;
                }
                return false;
            }
        });

        actvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String query = (String) adapterView.getItemAtPosition(i);
                Log.d(TAG, "onItemClick: query"+ query);
                handleSearch(query);
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addRightCancelDrawable(final EditText editText) {


        Button cancel_search = (Button)findViewById(R.id.cancel_search_btn);
        cancel_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actvSearch.setText("");
            }
        });
    }


    // get all stores name from DB
    private ArrayList<String> getStores(){
        ArrayList<String> result = new ArrayList<String>();
        SQLiteDatabase db	=	DH.getWritableDatabase();
        Cursor dbCursor = db.rawQuery("SELECT cn_name FROM Store", null);
        while (dbCursor.moveToNext()) {
            result.add(dbCursor.getString(0));
        }
        return result;
    }

    // when user submit or click on a suggestion
    private void handleSearch(String query){
        Log.d(TAG, "handleSearch: " + query);
        ArrayList<String> stores = new ArrayList<>();
        SQLiteDatabase db = DH.getWritableDatabase();
        Cursor dbCursor = db.rawQuery("SELECT cn_name FROM Store WHERE cn_name LIKE ?",
                new String[]{query+"%"});
        while (dbCursor.moveToNext()){
            stores.add(dbCursor.getString(0));
            Log.d(TAG, "handleSearch: result:"+dbCursor.getString(0));
        }
        flushicon(stores, lstBrand);
    }

    private void loadMap(){
        String Tag = "Load";
        if(map == null) {
            map = new ArrayList<Position>();
        }
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(
                    this.getAssets().open("location.csv")
            ));
            String[] next;
            while ((next = reader.readNext()) != null) {
                Log.d(Tag, next[0]);
                Log.d(Tag, next[1]);
                Log.d(Tag, next[2]);
                Log.d(Tag, next[3]);
                Log.d(Tag, next[4]);
                map.add(new Position(next[0], next[1], next[2], next[3], next[4]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Tag = "Pos";
        for(Position node: map){
            Log.d(Tag, node.getName());
            Log.d(Tag, node.getAdjacency());
            Log.d(Tag, node.lift);
        }
    }

    // map a array of stores name to a list of distinct positions
    private ArrayList mapToPos(String[] names){
        List<String> stores = storeNameToID(names);
        ArrayList<Position> positions = new ArrayList<Position>();
        for(String store:stores){
            Position found = toPos(store);
            if(found!=null){
                boolean add = false;
                for(Position pos: positions){
                    if(found.name.equals(pos.name)){
                        pos.stores.add(store);
                        add = true;
                        break;
                    }
                }
                if(!add){
                    positions.add(new Position(found.name, store, found.zone, found.floor, found.lift, 0));
                }
            }
        }
        return positions;
    }

    private List<String> storeNameToID(String[] names){
        List<String> result = new ArrayList<>();
        SQLiteDatabase db = DH.getWritableDatabase();
        String query = "SELECT id FROM Store"
                + " WHERE cn_name IN (" + TextUtils.join(",", Collections.nCopies(names.length, "?"))  + ")";
        Cursor cursor = db.rawQuery(query, names);
        while (cursor.moveToNext()){
            result.add(cursor.getString(0));
            Log.d(TAG, "storeNameToID: "+cursor.getString(0));
        }
        return result;
    }
    // map a store ID to it's position
    private Position toPos(String store){
        if(map != null){
            for(Position node : map){
                if(node.stores.contains(store)){
                    return node;
                }
            }
            Log.d(TAG, "toPos: position not found ");
            return null;
        }
        Log.d(TAG, "toPos: map is null");
        return null;
    }

    private ArrayList routePlan(ArrayList<Position> positions){
        int num = map.size();
        int inf = 10000;
        weight = inf;
        int[][] edge = new int[num][num];
        String[][] pred = new String[num][num]; // Store predecessor's name
        // Floyd Warshall Algorithm
        for(int i=0; i<num; i++){
            for(int j=0; j<num; j++){
                pred[i][j] = null;
                if(i==j) edge[i][j] = 0;
                else edge[i][j] = inf;
            }
            // convert adjacency list to adjacency matrix
            for(Map.Entry<String, Integer> adj:map.get(i).adjacency.entrySet()){
                try {
                    int j = Integer.parseInt(adj.getKey().substring(1)) - 1;
                    edge[i][j] = adj.getValue();
                    pred[i][j] = map.get(i).name;
                } catch (NumberFormatException e) {
                    Log.e(TAG, "routePlan: ", e);
                }
            }
        }
        for(int k=0; k<num; k++){
            for(int i=0; i<num; i++){
                for(int j=0; j<num; j++){
                    if(edge[i][j] > edge[i][k] + edge[k][j]){
                        edge[i][j] = edge[i][k] + edge[k][j];
                        pred[i][j] = pred[k][j];
                    }
                }
            }
        }

        /*
        // Test Floyd Warshall Algorithm
        for(int i=0; i<num; i++){
            String out = "";
            for(int j=0; j<num; j++){
                out += String.valueOf(edge[i][j]);
                out += ", ";
            }
            Log.d(TAG, "routePlan: "+ out);
        }
        for(int i=0; i<num; i++){
            String out = "";
            for(int j=0; j<num; j++){
                out += pred[i][j];
                out += ", ";
            }
            Log.d(TAG, "routePlan: "+ out);
        }
        */

        // Permutation: Heap's algorithm
        ArrayList<String> pos_name = new ArrayList<String>();
        for(Position pos: positions) { pos_name.add(pos.name); }
        pos_name.remove(start); // if the start pos in the sequence, remove it when do permutations
        getPermutation(pos_name.size(), pos_name, edge);

        // test
        Log.d(TAG, "routePlan: weig" + String.valueOf(weight));
        String out = "";
        for(String ele: sequence){ out += ele; }
        Log.d(TAG, "routePlan: " + out);

        positions = findRoute(positions, pred);
        checkElevator(positions);


        out = "";
        for(Position pos: positions){ out += pos.name; }
        Log.d(TAG, "routePlan: " + out);

        return positions;
    }

    private void getPermutation(int n, ArrayList arr, int[][] edge){
        if(n==1){
            // get one permutation
            getShortest(arr, edge);

        }else{
            getPermutation(n-1, arr, edge);
            for(int i=0; i<n-1; i++){
                if(n%2 == 0){
                    Collections.swap(arr, i, n-1);
                }else{
                    Collections.swap(arr, 0, n-1);
                }
                getPermutation(n-1, arr, edge);
            }
        }

    }

    private void getShortest(ArrayList<String> arr, int[][] edge){
        ArrayList<Integer> locs= new ArrayList<Integer>();

        locs.add(Integer.parseInt(start.substring(1))-1);
        for(String ele: arr){
            int loc = Integer.parseInt(ele.substring(1)) -1;
            locs.add(loc);
        }
        int temp_weight = 0;
        for(int i=0; i<locs.size()-1; i++){
            temp_weight += edge[locs.get(i)][locs.get(i+1)];
        }
        if(temp_weight < weight){
            weight = temp_weight;
            sequence = (ArrayList)arr.clone();
            sequence.add(0, start);
        }
    }

    private ArrayList<Position> findRoute(List<Position> positions, String[][] pred){
        ArrayList<Position> route = new ArrayList<Position>();
        for(String ele: sequence){
            boolean added = false;
            for(Position pos: positions){
                if(ele.equals(pos.name)){
                    route.add(pos);
                    added = true;
                    break;
                }
            }
            if(!added){
                for(Position node: map){
                    if(start.equals(node.name)){
                        Position pass_node = new Position(node.name, null, node.zone, node.floor, node.lift, 0);
                        route.add(0, pass_node);
                        break;
                    }
                }
            }
        }
        int index = route.size() -1;
        while(index>0){
            int st = Integer.parseInt(route.get(index-1).name.substring(1)) - 1;
            int end = Integer.parseInt(route.get(index).name.substring(1)) - 1;
            String pass = pred[st][end];
            if(!pass.equals(route.get(index-1).name)){
                for(Position node: map){
                    if(pass.equals(node.name)){
                        Position pass_node = new Position(node.name, null, node.zone, node.floor, node.lift, 0);
                        route.add(index, pass_node);
                        break;
                    }
                }
            }else{
                index--;
            }
        }
        return route;
    }

    private void checkElevator(List<Position> positions){
        for(int i=0; i<positions.size()-1;i++){
            String floor = positions.get(i).floor.substring(0,2);
            String nextFloor = positions.get(i+1).floor.substring(0,2);
            if(!floor.equals(nextFloor)){
                Log.d(TAG, "checkElevator: "+positions.get(i).name);
                Log.d(TAG, "checkElevator: "+positions.get(i).lift);
                positions.get(i).stores.add(positions.get(i).lift);
                positions.get(i+1).stores.add(positions.get(i+1).lift);
            }
        }
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
        if (v != null && (v instanceof AutoCompleteTextView)) {
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