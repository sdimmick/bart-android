package com.stevodimmick.bart;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.stevodimmick.bart.adapter.StationCursorAdapter;
import com.stevodimmick.bart.api.model.Station;
import com.stevodimmick.bart.database.StationTable;
import com.stevodimmick.bart.database.StationTable.StationsQuery;
import com.stevodimmick.bart.service.BartStationService;

/**
 * Displays the complete list of BART stations. This is the main fragment for the app.
 * @author sdimmick
 */
public class StationListFragment extends BaseListFragment implements LoaderCallbacks<Cursor> {
    private static final int STATIONS_CURSOR_ID = 1;
    private MenuItem mRefreshMenuItem;
    
    @Override
    public void onStart() {
        super.onStart();
        
        // Enable the menu
        setHasOptionsMenu(true);
        
        // Load a cursor to pull persisted stations from the database
        getLoaderManager().restartLoader(STATIONS_CURSOR_ID, null, this);
        
        // Spin the refresh button while data loads
        spinRefreshButton();
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh, menu);
        mRefreshMenuItem = menu.findItem(R.id.menu_refresh);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            spinRefreshButton();
            startStationService();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    
    private void spinRefreshButton() {
        if (mRefreshMenuItem != null) {
            mRefreshMenuItem.setActionView(R.layout.refresh_button_spinner);
        }
    }
    
    private void stopRefreshButton() {
        if (mRefreshMenuItem != null) {
            mRefreshMenuItem.setActionView(null);
        }
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

}
