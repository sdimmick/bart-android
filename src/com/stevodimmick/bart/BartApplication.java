package com.stevodimmick.bart;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * {@link Application} {@link Context} for the app 
 * @author sdimmick
 */
public class BartApplication extends Application {
    private static BartApplication mInstance;
    public static Resources resources;
    
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        resources = getResources();
    }
    
    public static BartApplication getInstance() {
        return mInstance;
    }

}
