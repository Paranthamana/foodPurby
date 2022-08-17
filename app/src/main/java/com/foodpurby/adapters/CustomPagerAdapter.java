package com.foodpurby.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.foodpurby.ondbstorage.Cuisine;
import com.foodpurby.ondbstorage.DBActionsCuisine;
import com.foodpurby.screens.ItemListFragment;

import java.util.ArrayList;
import java.util.List;

public class CustomPagerAdapter extends FragmentPagerAdapter {
    int[] mIcon;
    List<Cuisine> cuisines;

    ArrayList<Fragment> mTabNameList = new ArrayList<>();

    public CustomPagerAdapter(FragmentManager fragmentManager, int[] icon,
                              String groupKey) {
        super(fragmentManager);

        mIcon = icon;
        cuisines = DBActionsCuisine.getCuisines(groupKey);
        this.mTabNameList.clear();
        for (Cuisine cuisine : cuisines) {

            Bundle bundle = new Bundle();
            bundle.putString("cuisineKey", cuisine.getCuisineKey());
            ItemListFragment args = new ItemListFragment();
            args.setArguments(bundle);

            this.mTabNameList.add(args);
        }
    }

    @Override
    public int getCount() {
        return cuisines.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return cuisines.get(position).getCuisineName();
    }

    @Override
    public Fragment getItem(int position) {
        // TODO Auto-generated method stub
        return mTabNameList.get(position);
    }

}