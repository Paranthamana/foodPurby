package com.foodpurby.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by tech on 12/18/2015.
 */
public class MainMenuAdapter extends FragmentPagerAdapter {
    int[] mIcon;
    String[] title;
    ArrayList<Fragment> fragmentContents;

    public MainMenuAdapter(FragmentManager fragmentManager, String[] title, ArrayList<Fragment> fragmentContents) {
        super(fragmentManager);
        this.fragmentContents = fragmentContents;
        this.title = title;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public Fragment getItem(int position) {
        // TODO Auto-generated method stub
        return fragmentContents.get(position);
    }
}
