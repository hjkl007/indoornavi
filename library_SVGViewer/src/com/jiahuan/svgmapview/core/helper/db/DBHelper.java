package com.jiahuan.svgmapview.core.helper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class DBHelper extends SQLiteOpenHelper implements IProivderMetaData {
    private static final String TAG = "DBHelper";
    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLESQL = "create table if not exists "
                + PoiTableMetaData.TABLE_NAME + " ("
                + PoiTableMetaData.POI_ID + " integer primary key,"
                + PoiTableMetaData.POI_NAME + " varchar,"
                + PoiTableMetaData.POI_LOCATON_X + "float,"
                + PoiTableMetaData.POI_LOCATON_Y + "float,"
                + PoiTableMetaData.POI_NEXT_X + "float,"
                + PoiTableMetaData.POI_NEXT_Y + " float)";
        db.execSQL(TABLESQL);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        onCreate(db);
    }
}