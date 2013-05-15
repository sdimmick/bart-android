package com.stevodimmick.bart;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.stevodimmick.bart.adapter.SpinnerFilterAdapter;
import com.stevodimmick.bart.adapter.StationCursorAdapter;
import com.stevodimmick.bart.api.model.Station;
import com.stevodimmick.bart.database.StationTable;
import com.stevodimmick.bart.database.StationTable.StationsQuery;
import com.stevodimmick.bart.service.BartStationService;

/**
 * Displays the complete list of BART stations. This is the main fragment for the app.
 * @author sdimmick
 */
public class StationListFragment extends BaseListFragment implements LoaderCallbacks<Cursor>, OnNavigationListener {
    private static final int STATIONS_CURSOR_ID = 1;
    private String[] mFilters;
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        // Initialize the filter spinner
        mFilters = getResources().getStringArray(R.array.station_list_spinner);
        SpinnerAdapter spinnerAdapter = new SpinnerFilterAdapter(
                getActivity(), R.layout.spinner_item, mFilters);

        ActionBar actionBar = getSherlockActivity().getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(spinnerAdapter, this);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
        // Load a cursor to pull persisted stations from the database
        getLoaderManager().restartLoader(STATIONS_CURSOR_ID, null, this);
    }
    
    /**
     * Refresh the list of stations
     */
    @Override
    protected void refreshButtonClicked() {
        startStationService();
    }
    
    /**
     * Starts the station service to fetch and store BART stations
     */
    private void startStationService() {
        Intent intent = new Intent(getActivity(), BartStationService.class);
        getActivity().startService(intent);
    }
    
    /**
     * Launches a fragment to display detailed information about the selected
     * station
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        // Get the selected station
        StationCursorAdapter adapter = (StationCursorAdapter) getListAdapter();
        Cursor cursor = adapter.getCursor();
        cursor.moveToPosition(position);
        Station station = new Station(cursor);
        
        launchArrivalTimesFragment(station);
    }
    
    /**
     * Launches a fragment to display detailed information about the selected
     * station
     */
    private void launchArrivalTimesFragment(Station station) {
        ArrivalTimesListFragment fragment = new ArrivalTimesListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ArrivalTimesListFragment.STATION_EXTRA_KEY, station);
        fragment.setArguments(args);
        MainActivity activity = (MainActivity) getActivity();
        activity.showFragment(fragment, true);
    }
    
    /**
     * Creates a {@link CursorLoader} to fetch the list of stations
     */
    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        switch (loaderId) {
        case STATIONS_CURSOR_ID:
            return new CursorLoader(
                    getActivity(), 
                    StationTable.CONTENT_URI, 
                    StationsQuery.PROJECTION, 
                    null, 
                    null, 
                    StationTable.SORT_ALPHABETICALLY);
            default:
                return null;
        }
    }

    /**
     * Cursor's finished loading. If there's data, display it. If there's no data,
     * start the {@link BartStationService} to fetch it.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
        case STATIONS_CURSOR_ID:
            if (cursor.getCount() == 0) {
                // No stations in the database yet. 
                // Start the StationService to fetch and store them.
                startStationService();
            } else {
                // We have some stations. Show 'em.
                StationCursorAdapter adapter = new StationCursorAdapter(getActivity(), cursor);
                setListAdapter(adapter);
                stopRefreshButton();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        String filter = mFilters[itemPosition];
        Toast.makeText(getActivity(), filter, Toast.LENGTH_SHORT).show();
        return true;
    }

}
