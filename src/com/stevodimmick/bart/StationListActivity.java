package com.stevodimmick.bart;

import java.util.List;

import com.stevodimmick.bart.api.BartApi;
import com.stevodimmick.bart.api.model.Station;
import com.stevodimmick.util.LogUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.app.ListActivity;
import android.content.Context;

public class StationListActivity extends ListActivity {
    private static final String TAG = LogUtils.getTag(StationListActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchStationsTask().execute();
    }
    
    // TODO: remove this
    private class FetchStationsTask extends AsyncTask<Void, Void, List<Station>> {

        @Override
        protected List<Station> doInBackground(Void... params) {
            List<Station> stations = null;
            
            try {
                stations = BartApi.getStations(StationListActivity.this);
            } catch (Exception e) {
                Log.e(TAG, "Error", e);
            }
            
            return stations;
        }
        
        @Override
        protected void onPostExecute(List<Station> result) {
            for (Station s : result) {
                Log.e(TAG, s.getName());
            }
            
            setListAdapter(new StationsAdapter(StationListActivity.this, android.R.layout.simple_list_item_1, result));
        }
    }
    
    // TODO: remove this 
    private class StationsAdapter extends ArrayAdapter<Station> {
        private Context mContext;
        private List<Station> mStations;

        public StationsAdapter(Context context, int textViewResourceId, List<Station> objects) {
            super(context, textViewResourceId, objects);
            mContext = context;
            mStations = objects;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, null);
            view.setText(mStations.get(position).getName());
            return view;
        }
        
    }

}
