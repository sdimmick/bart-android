package com.stevodimmick.bart.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Helper class for creating the sqlite database
 * @author sdimmick
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bart";
    private static final int DATABASE_VERSION = 1;
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the station table
        db.execSQL("CREATE TABLE " + Tables.STATION + " ("
                + BaseColumns._ID           + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + StationTable.ABBREVIATION + " TEXT, "
                + StationTable.ADDRESS      + " TEXT, "
                + StationTable.CITY         + " TEXT, "
                + StationTable.COUNTY       + " TEXT, "
                + StationTable.LATITUDE     + " REAL, "
                + StationTable.LONGITUDE    + " REAL, "
                + StationTable.NAME         + " TEXT, "
                + StationTable.STATE        + " TEXT, "
                + StationTable.ZIPCODE      + " TEXT, "
                + "UNIQUE (" + StationTable.ABBREVIATION + ") ON CONFLICT REPLACE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not too worried about database upgrades at the moment
    }

}
