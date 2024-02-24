package com.example.prosoft.taxiloy.ui.activity;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;

import com.example.prosoft.taxiloy.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.Locale;

/**
 * Created by Manh on 2/29/2016.
 */
public class AnalyticsApplication extends Application{
    private Tracker mTracker;

    synchronized public Tracker getDefaultTracker(){
        if(mTracker==null){
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker("UA-74381698-4");
        }
        return mTracker;
    }

}
