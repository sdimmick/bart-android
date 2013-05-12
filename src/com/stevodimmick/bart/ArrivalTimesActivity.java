package com.stevodimmick.bart;

import java.util.List;

import com.stevodimmick.bart.api.BartApi;
import com.stevodimmick.bart.api.model.ArrivalTime;
import com.stevodimmick.bart.api.model.Station;
import com.stevodimmick.bart.api.model.TrainsForDestination;
import com.stevodimmick.bart.util.LogUtils;

import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArrivalTimesActivity extends ListActivity {
    private static final String TAG = LogUtils.getTag(ArrivalTimesActivity.class);
    public static final String STATION_EXTRA_KEY = "station"; 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Station station = (Station) getIntent().getSerializableExtra(STATION_EXTRA_KEY);
        new FetchArrivalTimesTask().execute(station);
    }

    // TODO: remove this
    private class FetchArrivalTimesTask extends AsyncTask<Station, Void, List<TrainsForDestination>> {

        @Override
        protected List<TrainsForDestination> doInBackground(Station... params) {
            Station station = params[0];
            List<TrainsForDestination> arrivalTimes = null;
            
            try {
                arrivalTimes = BartApi.getTrainsForDestination(station.getAbbr());
            } catch (Exception e) {
                Log.e(TAG, "Error", e);
            }
            
            return arrivalTimes;
        }
        
        @Override
        protected void onPostExecute(List<TrainsForDestination> result) {
            for (TrainsForDestination trains : result) {
                Log.d(TAG, trains.getDestination());
                for (ArrivalTime arrivalTime : trains.getArrivalTimes()) {
                    Log.d(TAG, arrivalTime.getMinutes());
                }
            }
            setListAdapter(new StationsAdapter(ArrivalTimesActivity.this, android.R.layout.simple_list_item_1, result));
        }
    }
    
    // TODO: remove this 
    private class StationsAdapter extends ArrayAdapter<TrainsForDestination> {
        private Context mContext;
        private List<TrainsForDestination> mTrainsForDestination;

        public StationsAdapter(Context context, int textViewResourceId, List<TrainsForDestination> objects) {
            super(context, textViewResourceId, objects);
            mContext = context;
            mTrainsForDestination = objects;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, null);
            view.setText(mTrainsForDestination.get(position).getDestination());
            return view;
        }
        
    }
}
