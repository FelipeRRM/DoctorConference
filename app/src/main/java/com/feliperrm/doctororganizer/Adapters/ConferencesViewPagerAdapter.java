package com.feliperrm.doctororganizer.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.feliperrm.doctororganizer.Fragments.ConferencesTabsFragment;
import com.feliperrm.doctororganizer.Fragments.ConfirmedConferencesFragment;
import com.feliperrm.doctororganizer.R;

/**
 * Created by felip on 18/08/2016.
 */
public class ConferencesViewPagerAdapter extends FragmentPagerAdapter {
    Context context;

    public ConferencesViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return new ConfirmedConferencesFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.confirmed_conferences);
            case 1:
                return context.getString(R.string.invited_conferences);
            case 2:
                return context.getString(R.string.suggested_conferences);
        }
        return null;
    }
}
