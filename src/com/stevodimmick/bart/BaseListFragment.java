package com.stevodimmick.bart;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public abstract class BaseListFragment extends SherlockListFragment {
    private MenuItem mRefreshMenuItem;
    
    @Override
    public void onStart() {
        super.onStart();
        
        // Enable the menu
        setHasOptionsMenu(true);
        
        spinRefreshButton();
    }
    
    /**
     * Add the refresh button to the menu
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh, menu);
        mRefreshMenuItem = menu.findItem(R.id.menu_refresh);
    }
    
    /**
     * Handle action bar button clicks
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            spinRefreshButton();
            refreshButtonClicked();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    
    /**
     * Animates the refresh button progress spinner
     */
    protected void spinRefreshButton() {
        if (mRefreshMenuItem != null) {
            mRefreshMenuItem.setActionView(R.layout.refresh_button_spinner);
        }
    }
    
    protected void stopRefreshButton() {
        if (mRefreshMenuItem != null) {
            mRefreshMenuItem.setActionView(null);
        }
    }
    
    /**
     * Should be overridden to handle interaction with the refres button
     */
    protected void refreshButtonClicked() {
        
    }
    
}
