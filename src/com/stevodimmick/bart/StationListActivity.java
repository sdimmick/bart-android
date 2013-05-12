package com.stevodimmick.bart;

import java.util.List;

import com.stevodimmick.bart.api.BartApi;
import com.stevodimmick.bart.api.model.Station;
import com.stevodimmick.bart.util.LogUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;

public class StationListActivity extends ListActivity {
    private static final String TAG = LogUtils.getTag(StationListActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchStationsTask().execute();
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Station station = (Station) getListAdapter().getItem(position);
        Intent intent = new Intent(this, ArrivalTimesActivity.class);
        intent.putExtra(ArrivalTimesActivity.STATION_EXTRA_KEY, station);
        startActivity(intent);
    }
    
    // TODO: remove this
    private class FetchStationsTask extends AsyncTask<Void, Void, List<Station>> {

        @Override
        protected List<Station> doInBackground(Void... params) {
            List<Station> stations = null;
            
            try {
                stations = BartApi.getStations();
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
