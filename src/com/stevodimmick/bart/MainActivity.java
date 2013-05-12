package com.stevodimmick.bart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * The main activity. Launches the main fragment (currently {@link StationListFragment}
 * @author sdimmick
 */
public class MainActivity extends SherlockFragmentActivity {
    private static final String MAIN_FRAGMENT_TAG = "main";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Load the layout
        setContentView(R.layout.activity_main);
        
        // Load the main fragment
        StationListFragment fragment = new StationListFragment();
        showFragment(fragment);
    }
    
    /**
     * Replaces the main fragment with this instance
     */
    public void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, fragment, MAIN_FRAGMENT_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
