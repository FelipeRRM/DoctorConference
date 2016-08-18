package com.feliperrm.doctororganizer.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feliperrm.doctororganizer.Activities.MainActivity;
import com.feliperrm.doctororganizer.Adapters.ConferencesViewPagerAdapter;
import com.feliperrm.doctororganizer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConferencesTabsFragment extends Fragment {

    TabLayout tabLayout;
    private ViewPager mViewPager;


    public ConferencesTabsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_conferences_tabs, container, false);
        findViews(v);
        setUpViews();
        return v;
    }


    private void findViews(View v) {
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        mViewPager = (ViewPager) v.findViewById(R.id.container);
    }

    private void setUpViews() {
        mViewPager.setAdapter(new ConferencesViewPagerAdapter(getContext(),getChildFragmentManager()));
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setBackgroundColor(MainActivity.TAB2_COLOR);
    }


}
