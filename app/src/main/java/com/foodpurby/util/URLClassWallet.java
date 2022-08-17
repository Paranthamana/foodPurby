package com.foodpurby.util;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.foodpurby.utillities.AppConfig;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by Tech on 19-04-2017.
 */

public class URLClassWallet {
    public static String BASE_URL = AppConfig.BaseUrl + "/api/" + AppConfig.APIVersion + "/user";

    //public static String BASE_URL = "http://foodpurby.in/api/v1/user";

    RestAdapter ApiBuilder;
    private static URLClassWallet ourInstance = new URLClassWallet();

    private URLClassWallet() {
    }


    public static URLClassWallet getInstance() {
        if (ourInstance == null) {
            synchronized (URLClassWallet.class) {
                if (ourInstance == null)
                    ourInstance = new URLClassWallet();
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


