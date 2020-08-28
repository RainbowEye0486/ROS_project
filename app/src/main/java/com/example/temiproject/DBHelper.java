package com.example.temiproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    static final String TAG = "DBHelper";
    private final static String _DBName = "StoreList.db";
    private	Context	mContext;
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {

        super(context, _DBName, factory, version);
        mContext =context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL = "CREATE TABLE IF NOT EXISTS " + "Store" + "( " +
                "id VARCHAR(10), " +
                "cn_name VARCHAR(50), " +
                "en_name VARCHAR(50), " +
                "big_pic TEXT," +
                "small_pic TEXT," +
                "modify_time DATE" +
                ")";

        db.execSQL(SQL);
        Toast.makeText(mContext,	"Create Store succeeded",	Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate: Create Store succeeded");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists Store");
        onCreate(sqLiteDatabase);
    }
}
