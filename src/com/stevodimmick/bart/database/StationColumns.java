package com.stevodimmick.bart.database;

import android.provider.BaseColumns;

/**
 * Columns for the station table (excluding those in {@link BaseColumns}) 
 * @author sdimmick
 */
public interface StationColumns {
    String NAME = "name";
    String ABBREVIATION = "abbreviation";
    String LATITUDE = "latitude";
    String LONGITUDE = "longitude";
    String ADDRESS = "address";
    String CITY = "city";
    String COUNTY = "county";
    String STATE = "state";
    String ZIPCODE = "zipcode";
}
