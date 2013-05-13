package com.stevodimmick.bart.database;

import com.stevodimmick.bart.BartApplication;
import com.stevodimmick.bart.R;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * {@link ContentProvider} implementation to handle interacting with the 
 * sqlite database
 *  
 * @author sdimmick
 */
public class BartProvider extends ContentProvider {
    // Handles creating / upgrading the database
    private DatabaseHelper mDatabaseHelper;
    
    // Matches URIs to their appropriate query, insert, etc. handlers
    private UriMatcher mUriMatcher;
    
    // Constants for UriMatcher
    private static final int STATIONS = 100; 
    
    /**
     * Construct a {@link UriMatcher} to map URIs to their appropriate handlers
     */
    private UriMatcher buildUriMatcher(String contentAuthority) {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        
        matcher.addURI(contentAuthority, StationTable.BASE_PATH, STATIONS);
        
        return matcher;
    }
    
    /**
     * Since constructing the {@link UriMatcher} is dependent on the content 
     * authority string in strings.xml, and content providers are constructed
     * before the application context, we need to call this method every time
     * we need to use the UriMatcher to be sure it's initialized.
     */
    private void init() {
        if (mUriMatcher == null) {
            // Create the URI matcher
            String authority = BartApplication.getInstance().getString(R.string.provider_authority);
            mUriMatcher = buildUriMatcher(authority);
        }
    }
    
    @Override
    public boolean onCreate() {
        // Create the database, if need be
        mDatabaseHelper = new DatabaseHelper(getContext());
        
        return true;
    }
    
    /**
     * Get the content type for the specified URI
     */
    @Override
    public String getType(Uri uri) {
        init();
        
        int match = mUriMatcher.match(uri);
        
        switch (match) {
        
        case STATIONS:
            return StationTable.CONTENT_TYPE;
        default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
            
        }
    }
    
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        init();
        
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        int match = mUriMatcher.match(uri);
        Cursor cursor = null;
        
        switch (match) {
        case STATIONS:
            // Select all stations from the station table
            String columns = getColumnsFromProjection(projection);
            String query = "SELECT DISTINCT " + columns + " FROM "+ Tables.STATION
                    + " ORDER BY " + sortOrder;
            cursor = db.rawQuery(query, null);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            break;
        default:
            throw new IllegalArgumentException("unsupported uri: " + uri);
        }
        
        return cursor;
    }
    
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Not used at the moment
        return 0;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        init();
        
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        int match = mUriMatcher.match(uri);
        
        switch (match) {
        case STATIONS:
            // Insert stations 
            long rowId = db.insertOrThrow(Tables.STATION, null, values);
            getContext().getContentResolver().notifyChange(uri, null);
            return ContentUris.withAppendedId(StationTable.CONTENT_URI, rowId);
        default:
            throw new IllegalArgumentException("unsupported uri: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // Not used at the moment
        return 0;
    }
    
    /**
     * Builds a string of column names from a projection suitable for SQL 
     * select statements
     * @param projection the array of columns
     * @return a string of format 'column1,column2,...'
     */
    private String getColumnsFromProjection(String[] projection) {
        StringBuilder builder = new StringBuilder();
        int size = projection.length;
        for (String proj : projection) {

            builder.append(proj);
            if (size != 1)
                builder.append(",");
            size--;

        }
        String colums = builder.toString();

        return colums;
    }

}
