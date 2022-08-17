package com.foodpurby.utillities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tech on 2/3/2016.
 */
public class SessionManager {
    private static SessionManager ourInstance = new SessionManager();
    private String SwiftShoppa = "SwiftShoppaPrefs";

    public static SessionManager getInstance() {
        return ourInstance;
    }

    private SessionManager() {
    }

    public void setAddressType(Context mContext, String deviceToken) {
        SharedPreferences prefs = mContext.getSharedPreferences(SwiftShoppa, Context.MODE_PRIVATE);
        prefs.edit().putString("AddressType", deviceToken).apply();
    }

    public String getAddressType(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        return prefs.getString("AddressType", "");
    }

    public void setDeviceToken(Context mContext, String deviceToken) {
        SharedPreferences prefs = mContext.getSharedPreferences(SwiftShoppa, Context.MODE_PRIVATE);
        prefs.edit().putString("DeviceToken", deviceToken).apply();
    }

    public String getDeviceToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        return prefs.getString("DeviceToken", "");
    }

    public void setAddressTypeId(Context context, String mUserKey) {
        SharedPreferences pref = context.getSharedPreferences(SwiftShoppa, Context.MODE_PRIVATE);
        pref.edit().putString("AddressTypeId", mUserKey).apply();
    }

    public String getAddressTypeId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        return prefs.getString("AddressTypeId", "");
    }


    public void setAddresskey(Context context, String maddressKey) {
        SharedPreferences pref = context.getSharedPreferences(SwiftShoppa, Context.MODE_PRIVATE);
        pref.edit().putString("AddressTypeKey", maddressKey).apply();
    }

    public String getAddresskey(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        return prefs.getString("AddressTypeKey", "");
    }


    public void setLatitute(Context context, String Latitute) {
        SharedPreferences pref = context.getSharedPreferences(SwiftShoppa, Context.MODE_PRIVATE);
        pref.edit().putString("Latitute", Latitute).apply();
    }

    public String getLatitute(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        return prefs.getString("Latitute", "");
    }

    public void setAddress(Context context, String Address) {
        SharedPreferences pref = context.getSharedPreferences(SwiftShoppa, Context.MODE_PRIVATE);
        pref.edit().putString("Address", Address).apply();
    }

    public String getAddress(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        return prefs.getString("Address", "");
    }

    public void setmember(Context context, int member) {
        SharedPreferences pref = context.getSharedPreferences(SwiftShoppa, Context.MODE_PRIVATE);
        pref.edit().putInt("member", member).apply();
    }

    public int getmember(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        return prefs.getInt("member", 0);
    }

    public void setselectedmember(Context context, int selectedmember) {
        SharedPreferences pref = context.getSharedPreferences(SwiftShoppa, Context.MODE_PRIVATE);
        pref.edit().putInt("selectedmember", selectedmember).apply();
    }

    public int getselectedmember(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        return prefs.getInt("selectedmember", 0);
    }


    public void setLongtitue(Context context, String Longtitue) {
        SharedPreferences pref = context.getSharedPreferences(SwiftShoppa, Context.MODE_PRIVATE);
        pref.edit().putString("Longtitue", Longtitue).apply();
    }

    public String getLongtitue(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        return prefs.getString("Longtitue", "");
    }


    public void setBannerImage(Context context, String mSelectLanguageCode) {
        SharedPreferences pref = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        pref.edit().putString("Banner_image", mSelectLanguageCode).apply();
    }

    public String getBannerImage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        return prefs.getString("Banner_image", "");
    }

    public void setcreditpoint(Context context, String mcreditpoint) {
        SharedPreferences pref = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        pref.edit().putString("creditpoint", mcreditpoint).apply();
    }

    public String getcreditpoint(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        return prefs.getString("creditpoint", "0");
    }

    public void setCurrentOrderKey(Context context, String mcreditpoint) {
        SharedPreferences pref = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        pref.edit().putString("orderKey", mcreditpoint).apply();
    }

    public String getCurrentOrderKey(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        return prefs.getString("orderKey", "");
    }

    public void setDeliveryMethod(Context context, String mcreditpoint) {
        SharedPreferences pref = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        pref.edit().putString("deliverymethod", mcreditpoint).apply();
    }

    public String getDeliveryMethod(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        return prefs.getString("deliverymethod", "0");
    }


    public void setDateTimeSelected(Context context, String mcreditpoint) {
        SharedPreferences pref = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        pref.edit().putString("DateTimeSelected", mcreditpoint).apply();
    }

    public String getDateTimeSelected(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        return prefs.getString("DateTimeSelected", "0");
    }


    public void setlat(Context context, String lat) {
        SharedPreferences pref = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        pref.edit().putString("lat", lat).apply();
    }

    public String getlat(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        return prefs.getString("lat", "0.0");
    }


    public void setlong(Context context, String longi) {
        SharedPreferences pref = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        pref.edit().putString("long", longi).apply();
    }

    public String getlong(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SwiftShoppa, Activity.MODE_PRIVATE);
        return prefs.getString("long", "0.0");
    }


}
