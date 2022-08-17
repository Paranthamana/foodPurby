package com.foodpurby.model;

/**
 * Created by tech on 11/16/2015.
 */
public class MenuItem {
    String mItemName, mItemCategoryNames, mItemCost;
    int mQuantitySet = 0;

    public String getmItemName() {
        return mItemName;
    }

    public void setmItemName(String mItemName) {
        this.mItemName = mItemName;
    }

    public String getmItemCategoryNames() {
        return mItemCategoryNames;
    }

    public void setmItemCategoryNames(String mItemCategoryNames) {
        this.mItemCategoryNames = mItemCategoryNames;
    }

    public String getmItemCost() {
        return mItemCost;
    }

    public void setmItemCost(String mItemCost) {
        this.mItemCost = mItemCost;
    }

    public int getmQuantitySet() {
        return mQuantitySet;
    }

    public void setmQuantitySet(int mQuantitySet) {
        this.mQuantitySet = mQuantitySet;
    }
}
