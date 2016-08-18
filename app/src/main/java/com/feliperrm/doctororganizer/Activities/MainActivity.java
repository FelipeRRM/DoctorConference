package com.feliperrm.doctororganizer.Activities;

import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.feliperrm.doctororganizer.Fragments.CalendarFragment;
import com.feliperrm.doctororganizer.Fragments.ConferencesTabsFragment;
import com.feliperrm.doctororganizer.Fragments.CreateEventFragment;
import com.feliperrm.doctororganizer.Fragments.ProfileFragment;
import com.feliperrm.doctororganizer.R;
import com.feliperrm.doctororganizer.Utils.MyApp;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.roughike.bottombar.OnTabClickListener;

public class MainActivity extends BaseActivity implements CreateEventFragment.CreateEventInterface {

    private BottomBar mBottomBar;
    private FrameLayout frameForFragments;
    Fragment calendarFragment;
    Fragment profileFragment;
    Fragment conferencesTab;

    public static final int TAB1_COLOR = ContextCompat.getColor(MyApp.getContext(), R.color.colorAccent);
    public static final int TAB2_COLOR = 0xFF5D4037;
    public static final int TAB3_COLOR = Color.parseColor("#7B1FA2");

    int selectedTab;


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
        profileFragment = new ProfileFragment();
        conferencesTab = new ConferencesTabsFragment();
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
                if(position!=selectedTab) {
                    selectedTab = position;
                    Fragment framgmentToShow = null;
                    switch (selectedTab){
                        case 0: {
                            if(calendarFragment==null)
                                calendarFragment = new CalendarFragment();
                            framgmentToShow = calendarFragment;
                            break;
                        }
                        case 1:{
                            if(conferencesTab==null)
                                conferencesTab = new ConferencesTabsFragment();
                            framgmentToShow = conferencesTab;
                            break;
                        }

                        case 2:{
                            if(profileFragment == null)
                                profileFragment = new ProfileFragment();
                            framgmentToShow = profileFragment;
                            break;
                        }

                    }
                    if(framgmentToShow!=null)
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameForFragments, framgmentToShow).commitAllowingStateLoss();
                }
            }

            @Override
            public void onTabReSelected(int position) {
                // The user reselected a tab at the specified position!
                Log.d("onTabReSelected", String.valueOf(position));
            }
        });

        // Setting colors for different tabs when there's more than three of them.
        // You can set colors for tabs in three different ways as shown below.
        mBottomBar.mapColorForTab(0, TAB1_COLOR);
        mBottomBar.mapColorForTab(1, TAB2_COLOR);
        mBottomBar.mapColorForTab(2, TAB3_COLOR);
//        mBottomBar.mapColorForTab(3, "#FF5252");
//        mBottomBar.mapColorForTab(4, "#FF9800");

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBottomBar.onSaveInstanceState(outState);
    }

    @Override
    public void SuggestionCreated() {
        ((CalendarFragment)(calendarFragment)).setUpViews();
    }
}
