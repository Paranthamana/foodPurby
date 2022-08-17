package com.foodpurby.util;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.foodpurby.utillities.AppConfig;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by tech on 11/2/2015.
 */
public class URLClassCategory {
    //public static String BASE_URL = "http://192.168.1.120:1025/api/v1/shop";
    public static String BASE_URL = AppConfig.BaseUrl + "/api/" + AppConfig.APIVersion + "/category";
    //public static String BASE_URL = "http://foodpurby.in/api/v1/user";

    RestAdapter ApiBuilder;
    private static URLClassCategory ourInstance = new URLClassCategory();

    private URLClassCategory() {
    }


    public static URLClassCategory getInstance() {
        if (ourInstance == null) {
            synchronized (URLClassCategory.class) {
                if (ourInstance == null)
                    ourInstance = new URLClassCategory();
            }
        }
        ourInstance.config();
        return ourInstance;
    }



    private void config() {
        OkHttpClient mOkhttp = new OkHttpClient();
        mOkhttp.setReadTimeout(150, TimeUnit.SECONDS);
        mOkhttp.setConnectTimeout(150, TimeUnit.SECONDS);


        ApiBuilder = new RestAdapter.Builder().setConverter(new GsonConverter(new Gson())).setLogLevel(RestAdapter.LogLevel.FULL).setClient(new OkClient(mOkhttp))
                .setEndpoint(BASE_URL)
                .build();
    }

    public RestAdapter getApiBuilder() {
        return ApiBuilder;
    }

    /**
     * We inject our own concept with json parsing when we convert to pojo class*
     */
    public void setApiBuilder(RestAdapter apiBuilder) {
        ApiBuilder = apiBuilder;
    }
}
