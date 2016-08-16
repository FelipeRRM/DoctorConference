package com.feliperrm.doctororganizer.Activities;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.feliperrm.doctororganizer.Fragments.CalendarFragment;
import com.feliperrm.doctororganizer.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.roughike.bottombar.OnTabClickListener;

public class MainActivity extends BaseActivity {

    private BottomBar mBottomBar;
    private FrameLayout frameForFragments;
    Fragment calendarFragment;


    @Override
    public String getScreenName() {
        return "Main";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.noTopOffset();
        findViews();
        setUpViews();
    }

    private void findViews(){
        frameForFragments = (FrameLayout) findViewById(R.id.frameForFragments);
    }

    private void setUpViews(){
        setUpBottomBar();
        calendarFragment = new CalendarFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frameForFragments, calendarFragment).commitAllowingStateLoss();
    }

    private void setUpBottomBar(){
        mBottomBar.setMaxFixedTabs(2);
        mBottomBar.setItems(
                new BottomBarTab(R.drawable.ic_calendar, "Calendar"),
                new BottomBarTab(R.drawable.conference, "Conferences"),
                new BottomBarTab(R.drawable.user, "Profile")
        );
        mBottomBar.setOnTabClickListener(new OnTabClickListener() {
            @Override
            public void onTabSelected(int position) {
                // The user selected a tab at the specified position
                Log.d("onTabSelected", String.valueOf(position));
            }

            @Override
            public void onTabReSelected(int position) {
                // The user reselected a tab at the specified position!
                Log.d("onTabReSelected", String.valueOf(position));
            }
        });

        // Setting colors for different tabs when there's more than three of them.
        // You can set colors for tabs in three different ways as shown below.
        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(1, 0xFF5D4037);
        mBottomBar.mapColorForTab(2, "#7B1FA2");
//        mBottomBar.mapColorForTab(3, "#FF5252");
//        mBottomBar.mapColorForTab(4, "#FF9800");

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBottomBar.onSaveInstanceState(outState);
    }
}
