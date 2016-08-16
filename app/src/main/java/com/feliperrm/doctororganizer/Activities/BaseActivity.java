package com.feliperrm.doctororganizer.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public abstract class BaseActivity extends AppCompatActivity {

    public static final String SCREEN_NAME_KEY ="screenname";

    public static final int TIME_TO_BACK_PRESS = 3000;

  //  Tracker mTracker;
    String fullPathString;


    public abstract String getScreenName();
    public String getScreenNameHistory(){
        try {
            return getIntent().getExtras().getString(SCREEN_NAME_KEY);
        }
        catch (Exception e){
            return "";
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // MyApp application = (MyApp) getApplication();
       // mTracker = application.getDefaultTracker();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fullPathString = getScreenNameHistory() + "/" + getScreenName();
      /*  if (name != null) {
            mTracker.setScreenName(name);
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        } */
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      //  overridePendingTransition( android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public String getFullPathString() {
        return fullPathString;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }


}
