package com.stevodimmick.bart;

import android.app.Application;

public class BartApplication extends Application {
    private static BartApplication mInstance;
    
    public BartApplication() {
        mInstance = this;
    }
    
    public static BartApplication getInstance() {
        return mInstance;
    }

}
