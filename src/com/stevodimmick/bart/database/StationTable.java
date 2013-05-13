package com.stevodimmick.bart.database;

import com.stevodimmick.bart.BartApplication;
import com.stevodimmick.bart.R;

import android.content.res.Resources;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Static definition of the station table
 * @author sdimmick
 */
public abstract class StationTable implements BaseColumns, StationColumns {
    static Resources resources = BartApplication.resources;

    // Base URI path for the table
    public static final String BASE_PATH = "station";
    
    private static final String CONTENT_AUTHORITY = 
            resources.getString(R.string.provider_authority);
    private static final Uri BASE_CONTENT_URI = 
            Uri.parse("content://" + CONTENT_AUTHORITY);
    
    // Content URI for full table scan
    public static final Uri CONTENT_URI = 
            BASE_CONTENT_URI.buildUpon().appendPath(BASE_PATH).build();
    
    // Content type for the station table. Not really used at the moment.
    public static final String CONTENT_TYPE = resources
            .getString(R.string.provider_station_mimeType_dir);
    public static final String CONTENT_ITEM_TYPE = resources
            .getString(R.string.provider_station_mimeType_item);
    
    // Sorts results alphabetically
    public static final String SORT_ALPHABETICALLY = 
            StationColumns.NAME + " COLLATE NOCASE";
    
    /**
     * Projection containing all columns in the table 
     * @author sdimmick
     */
    public static interface StationsQuery {
        String[] PROJECTION = {
                Tables.STATION + "." + BaseColumns._ID,
                Tables.STATION + "." + StationColumns.NAME,
                Tables.STATION + "." + StationColumns.ABBREVIATION,
                Tables.STATION + "." + StationColumns.LATITUDE,
                Tables.STATION + "." + StationColumns.LONGITUDE,
                Tables.STATION + "." + StationColumns.ADDRESS,
                Tables.STATION + "." + StationColumns.CITY,
                Tables.STATION + "." + StationColumns.COUNTY,
                Tables.STATION + "." + StationColumns.STATE,
                Tables.STATION + "." + StationColumns.ZIPCODE
        };
        
        int _ID = 0;
        int NAME = 1;
        int ABBREVIATION = 2;
        int LATITUDE = 3;
        int LONGITUDE = 4;
        int ADDRESS = 5;
        int CITY = 6;
        int COUNTY = 7;
        int STATE = 8;
        int ZIPCODE = 9;
    }
}
