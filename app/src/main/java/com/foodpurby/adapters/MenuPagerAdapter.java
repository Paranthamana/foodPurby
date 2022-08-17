package com.foodpurby.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.foodpurby.screens.InfoFragment;
import com.foodpurby.screens.MenuFragment;
import com.foodpurby.screens.RatingsFragment;

/**
 * Created by Android on 7/4/2016.
 */
public class MenuPagerAdapter extends FragmentPagerAdapter {
    int[] mIcon;
    private String tabTitles[] = new String[]{"Menu", "Ratings", "Info"};
    final int PAGE_COUNT = 3;
    int mPosition;

    public MenuPagerAdapter(FragmentManager fragmentManager, int[] icon, int mGroupPosition) {
        super(fragmentManager);
        mIcon = icon;
        mPosition = mGroupPosition;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        // TODO Auto-generated method stub
        switch (position) {
            case 0:
                MenuFragment mMenuFragment = new MenuFragment(mPosition);
                return mMenuFragment;
            case 1:
                RatingsFragment mRatingsFragment = new RatingsFragment(mPosition);
                return mRatingsFragment;
            case 2:
                InfoFragment mInfoFragment = new InfoFragment(mPosition);
                return mInfoFragment;
            default:
                return null;
        }
    }

}
