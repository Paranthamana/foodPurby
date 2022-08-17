package com.foodpurby.model;


import com.foodpurby.util.URLClassDefault;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by android1 on 1/4/2016.
 */
public class DAOArea {

    private static DAOArea ourInstance = new DAOArea();

    public static DAOArea getInstance() {
        return ourInstance;
    }

    public DAOArea() {
    }

    public void Callresponse(String key, String language, String cityKey, Callback<Area> mCallback) {
        AreaLists mGetapi = URLClassDefault.getInstance().getApiBuilder().create(AreaLists.class);
        mGetapi.ListView(key, language, cityKey, mCallback);
    }

    public interface AreaLists {
        @GET("/area.html")
        public void ListView(@Query("key") String key,
                             @Query("language") String language,
                             @Query("city") String cityKey,
                             Callback<Area> response);
    }



    public class Area {

        private String httpcode;
        private String status;
        private String message;
        private Data data;
        private String responsetime;

        /**
         *
         * @return
         * The httpcode
         */
        public String getHttpcode() {
            return httpcode;
        }

        /**
         *
         * @param httpcode
         * The httpcode
         */
        public void setHttpcode(String httpcode) {
            this.httpcode = httpcode;
        }

        /**
         *
         * @return
         * The status
         */
        public String getStatus() {
            return status;
        }

        /**
         *
         * @param status
         * The status
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         *
         * @return
         * The message
         */
        public String getMessage() {
            return message;
        }

        /**
         *
         * @param message
         * The message
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         *
         * @return
         * The data
         */
        public Data getData() {
            return data;
        }

        /**
         *
         * @param data
         * The data
         */
        public void setData(Data data) {
            this.data = data;
        }

        /**
         *
         * @return
         * The responsetime
         */
        public String getResponsetime() {
            return responsetime;
        }

        /**
         *
         * @param responsetime
         * The responsetime
         */
        public void setResponsetime(String responsetime) {
            this.responsetime = responsetime;
        }

    }

    public class Area_list {

        private String area_name;
        private String latitude;
        private String longitude;
        private String area_key;

        /**
         *
         * @return
         * The area_name
         */
        public String getArea_name() {
            return area_name;
        }

        /**
         *
         * @param area_name
         * The area_name
         */
        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        /**
         *
         * @return
         * The latitude
         */
        public String getLatitude() {
            return latitude;
        }

        /**
         *
         * @param latitude
         * The latitude
         */
        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        /**
         *
         * @return
         * The longitude
         */
        public String getLongitude() {
            return longitude;
        }

        /**
         *
         * @param longitude
         * The longitude
         */
        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        /**
         *
         * @return
         * The area_key
         */
        public String getArea_key() {
            return area_key;
        }

        /**
         *
         * @param area_key
         * The area_key
         */
        public void setArea_key(String area_key) {
            this.area_key = area_key;
        }

    }

    public class Data {

        private List<Area_list> area_list = new ArrayList<Area_list>();
        private Integer totalitems;

        /**
         *
         * @return
         * The area_list
         */
        public List<Area_list> getArea_list() {
            return area_list;
        }

        /**
         *
         * @param area_list
         * The area_list
         */
        public void setArea_list(List<Area_list> area_list) {
            this.area_list = area_list;
        }

        /**
         *
         * @return
         * The totalitems
         */
        public Integer getTotalitems() {
            return totalitems;
        }

        /**
         *
         * @param totalitems
         * The totalitems
         */
        public void setTotalitems(Integer totalitems) {
            this.totalitems = totalitems;
        }

    }

}
