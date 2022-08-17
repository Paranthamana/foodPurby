package com.foodpurby.utillities;

import com.google.gson.Gson;
import com.foodpurby.model.DAOShops;
import com.foodpurby.screens.MyCart;

import java.text.DecimalFormat;

import io.socket.client.Socket;

/**
 * Created by android1 on 12/9/2015.
 */
public class AppConfig {
   // public static String BaseUrl = "http://demo.foodpurby.in";   /* ---  live  --- */
    public static String BaseUrl = "http://foodpurby.in";         /* ---  demo --- */
    public static String APIVersion = "v4";
    public static String APIVersionTab = "v1";
    public static String BaseUrlTab = "http://54.174.36.170";
    public static String CopyrightCompanyName = "Foodpurby V1";
    public static String AboutUrl = "https://www.technoduce.com";
    public static String AboutCompanyName = "Technoduce Info Solutions Pvt. Ltd., India";
    public static int SPLASH_DISPLAY_LENGTH_IN_SEC = 1;
    public static int MAX_CART_PER_ITEM = 99;
    public static DecimalFormat decimalFormat = new DecimalFormat("0.00");
    public static MyCart MYCART = null;

    public static Socket socket;
    //public static String SocketPath = "http://188.166.235.74:8090/";
    public static String SocketPath = "http://54.172.71.190:8090/";

    public static DAOShops.Restaurant StringToObject(String response) {
        Gson gson = new Gson();
        DAOShops.Restaurant obj = gson.fromJson(response, DAOShops.Restaurant.class);
        return obj;
    }

    public static String ObjectToString(DAOShops.Restaurant restaurant) {
        Gson gson = new Gson();
        String json = gson.toJson(restaurant);
        return json;
    }
}
