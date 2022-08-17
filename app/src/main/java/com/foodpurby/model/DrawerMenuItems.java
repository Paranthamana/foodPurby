package com.foodpurby.model;

/**
 * Created by tech on 8/6/2015.
 */
public class DrawerMenuItems {

    String mTitle;
    int mIcon;


    public DrawerMenuItems(String mTitle, int mIcon) {
        this.mTitle = mTitle;
        this.mIcon = mIcon;
    }


    public DrawerMenuItems() {

    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getmIcon() {
        return mIcon;
    }

    public void setmIcon(int mIcon) {
        this.mIcon = mIcon;
    }
}
