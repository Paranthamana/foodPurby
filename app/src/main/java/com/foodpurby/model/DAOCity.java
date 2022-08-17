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
public class DAOCity {

    private static DAOCity ourInstance = new DAOCity();

    public static DAOCity getInstance() {
        return ourInstance;
    }

    public DAOCity() {
    }

    public void Callresponse( String language, Callback<City> mCallback) {
        CityLists mGetapi = URLClassDefault.getInstance().getApiBuilder().create(CityLists.class);
        mGetapi.CityListView(language, mCallback);
    }

    public interface CityLists {
        @GET("/city_list.html")
        public void CityListView( @Query("language") String language,
                                  Callback<City> response);
    }

    public class City {

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

    public class City_list {

        private String city_key;
        private String city_name;

        /**
         *
         * @return
         * The city_key
         */
        public String getCity_key() {
            return city_key;
        }

        /**
         *
         * @param city_key
         * The city_key
         */
        public void setCity_key(String city_key) {
            this.city_key = city_key;
        }

        /**
         *
         * @return
         * The city_name
         */
        public String getCity_name() {
            return city_name;
        }

        /**
         *
         * @param city_name
         * The city_name
         */
        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

    }

    public class Data {

        private List<City_list> city_list = new ArrayList<City_list>();
        private Integer totalitems;
        private General_settigns general_settigns;

        /**
         *
         * @return
         * The city_list
         */
        public List<City_list> getCity_list() {
            return city_list;
        }

        /**
         *
         * @param city_list
         * The city_list
         */
        public void setCity_list(List<City_list> city_list) {
            this.city_list = city_list;
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

        /**
         *
         * @return
         * The general_settigns
         */
        public General_settigns getGeneral_settigns() {
            return general_settigns;
        }

        /**
         *
         * @param general_settigns
         * The general_settigns
         */
        public void setGeneral_settigns(General_settigns general_settigns) {
            this.general_settigns = general_settigns;
        }

    }

    public class General_settigns {

        private String app_name;
        private String terms_and_conditions;
        private String privacy_policy;

        /**
         *
         * @return
         * The app_name
         */
        public String getApp_name() {
            return app_name;
        }

        /**
         *
         * @param app_name
         * The app_name
         */
        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        /**
         *
         * @return
         * The terms_and_conditions
         */
        public String getTerms_and_conditions() {
            return terms_and_conditions;
        }

        /**
         *
         * @param terms_and_conditions
         * The terms_and_conditions
         */
        public void setTerms_and_conditions(String terms_and_conditions) {
            this.terms_and_conditions = terms_and_conditions;
        }

        /**
         *
         * @return
         * The privacy_policy
         */
        public String getPrivacy_policy() {
            return privacy_policy;
        }

        /**
         *
         * @param privacy_policy
         * The privacy_policy
         */
        public void setPrivacy_policy(String privacy_policy) {
            this.privacy_policy = privacy_policy;
        }

    }
}
