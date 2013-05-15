package com.stevodimmick.bart.adapter;

import com.stevodimmick.bart.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Simple {@link ArrayAdapter} to populate dropdown filters (Spinners)
 * for sorting lists
 * @author sdimmick
 */
public class SpinnerFilterAdapter extends ArrayAdapter<CharSequence> {
    private Context mContext;
    private CharSequence[] mFilterItems;
    
    public SpinnerFilterAdapter(Context context, int textViewResourceId, CharSequence[] objects) {
        super(context, textViewResourceId, objects);
        mContext = context;
        mFilterItems = objects;
    }

    /**
     * Inflates the view for the currently selected spinner item (the one displayed
     * in the action bar).
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.spinner_item, null);
        }
        
        ((TextView) view).setText(mFilterItems[position]);

        return view;
    }
    
    /**
     * Inflates the view for the dropdown filter items
     */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.spinner_dropdown_item, null);
        }
        
        ((TextView) view).setText(mFilterItems[position]);

        return view;
    }

}
