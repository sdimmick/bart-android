package com.stevodimmick.bart.service;

import java.util.ArrayList;
import java.util.List;

import com.stevodimmick.bart.R;
import com.stevodimmick.bart.api.BartApi;
import com.stevodimmick.bart.api.model.Station;
import com.stevodimmick.bart.database.StationTable;
import com.stevodimmick.bart.util.LogUtils;

import android.app.Service;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

/**
 * Fetches and persists the complete list of BART stations.
 * Doing this in a service and persisting the result is probably
 * overkill for such a small amount of data, but... oh well.
 * 
 * @author sdimmick
 */
public class BartStationService extends Service {
    private static final String TAG = LogUtils.getTag(BartStationService.class);
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new FetchStationsTask().execute();
        return Service.START_FLAG_REDELIVERY;
    }
    
    /**
     * Fetches / parses BART station XML and stores the result in the database.
     */
    private class FetchStationsTask extends AsyncTask<Void, Void, List<Station>> {
        @Override
        protected List<Station> doInBackground(Void... params) {
            List<Station> stations = null;
            
            try {
                stations = BartApi.getStations();
                storeStations(stations);
            } catch (Exception e) {
                Log.e(TAG, "Error fetching / parsing staitons", e);
                Toast.makeText(getBaseContext(), "Error fetching BART stations", Toast.LENGTH_SHORT).show();
            }
            
            return stations;
        }
    }
    
    /**
     * Stores a {@link List} of {@link Station} objects in the database.
     * @param stations the {@link List} of {@link Station} objects to store
     */
    private void storeStations(List<Station> stations) throws RemoteException, OperationApplicationException {
        final ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
        
        for (Station station : stations) {
            batch.add(ContentProviderOperation
                    .newInsert(StationTable.CONTENT_URI)
                    .withValues(station.getContentValues())
                    .build());
        }
        
        String authority = this.getResources().getString(R.string.provider_authority);
        getContentResolver().applyBatch(authority, batch);
    }

}
