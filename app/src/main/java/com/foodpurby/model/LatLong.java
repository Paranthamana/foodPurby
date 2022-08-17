package com.foodpurby.model;

import java.io.Serializable;

/**
 * Created by tech on 12/7/2015.
 */
public class LatLong implements Serializable {

    double mLatitutde, mLongitude;
    String mLocationName;

    public double getmLatitutde() {
        return mLatitutde;
    }

    public void setmLatitutde(double mLatitutde) {
        this.mLatitutde = mLatitutde;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getmLocationName() {
        return mLocationName;
    }

    public void setmLocationName(String mLocationName) {
        this.mLocationName = mLocationName;
    }
}
