package com.feliperrm.doctororganizer.Utils;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.feliperrm.doctororganizer.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class MyApp extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Fabric.with(this, new Crashlytics());
        context = getApplicationContext();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Prime-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        ActiveAndroid.initialize(this);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
        //FileDownloader.init(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
