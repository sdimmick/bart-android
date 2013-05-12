package com.stevodimmick.bart;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.stevodimmick.bart.api.BartApi;
import com.stevodimmick.bart.api.model.Station;
import com.stevodimmick.bart.util.LogUtils;

public class StationListFragment extends BaseListFragment {
    private static final String TAG = LogUtils.getTag(StationListFragment.class);
    
    @Override
    public void onStart() {
        super.onStart();
        new FetchStationsTask().execute();
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        Station station = (Station) getListAdapter().getItem(position);
        launchArrivalTimesFragment(station);
    }
    
    private void launchArrivalTimesFragment(Station station) {
        ArrivalTimesListFragment fragment = new ArrivalTimesListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ArrivalTimesListFragment.STATION_EXTRA_KEY, station);
        fragment.setArguments(args);
        MainActivity activity = (MainActivity) getActivity();
        activity.showFragment(fragment);
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
            
            setListAdapter(new StationsAdapter(getActivity(), android.R.layout.simple_list_item_1, result));
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
