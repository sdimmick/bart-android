package com.stevodimmick.bart.adapter;

import com.stevodimmick.bart.api.model.Station;
import com.stevodimmick.bart.database.StationTable.StationsQuery;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * {@link CursorAdapter} for displaying a list of parsed {@link Station}
 * objects from the database 
 * @author sdimmick
 */
public class StationCursorAdapter extends CursorAdapter {

    public StationCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, android.R.layout.simple_list_item_1);
    }

    /**
     * Renders the {@link Station} in the list
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view).setText(cursor.getString(StationsQuery.NAME));
    }

    /**
     * Constructs a new list view item 
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null);
    }

}
