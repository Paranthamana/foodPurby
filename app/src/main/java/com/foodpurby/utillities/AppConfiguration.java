package com.foodpurby.utillities;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tech on 10-05-2017.
 */

public class AppConfiguration { //latest one
    private static AppConfiguration ourInstance = new AppConfiguration();
    Retrofit mRetrofit;

    public static AppConfiguration getInstance() {
        if (ourInstance == null) {
            synchronized (AppConfiguration.class) {
                if (ourInstance == null) {
                    ourInstance = new AppConfiguration();
                }
            }
        }
        ourInstance.config();
        return ourInstance;
    }

    private AppConfiguration() {
    }

    private void config() {
        //http://192.168.1.235:1018
//        "http://192.168.1.224:1016/api/v1/deliveryboy/"
        //http://demo14.duceapps.com
        //http://demo31.duceapps.com/api/v3/deliveryboy/
//http://demo36.duceapps.com/api/v3/deliveryboy/
//        public String domainName = "http://delivo.mn/api/v3/";
        //demo -http://demo32.duceapps.com/api/v1/deliveryboy/
        //live-http://shuneezfsc.com/

        //http://192.168.1.224:1025/api/v1/deliveryboy
        //http://shuneezfsc.com/api/v1/deliveryboy/
        mRetrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.1.73:1006/api/web/index.php/v1/vendor/")
//                .baseUrl("http://192.168.1.73:1006/api/web/index.php/v1/vendor/")
//        http:
//demo58.duceapps.com/api/web/index.php/v1/vendor
                .baseUrl("http://www.foodpurby.com/api/v4/default/")

                .client(getRequestHeader())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient getRequestHeader() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    public Retrofit getApiBuilder() {

        return mRetrofit;
    }
}