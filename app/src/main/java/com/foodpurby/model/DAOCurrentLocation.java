package com.foodpurby.model;


import com.foodpurby.util.URLClassDefault;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by android1 on 1/18/2016.
 */
public class DAOCurrentLocation {
    private static DAOCurrentLocation ourInstance = new DAOCurrentLocation();

    public static DAOCurrentLocation getInstance() {
        return ourInstance;
    }

    private DAOCurrentLocation() {
    }

    public void Callresponse(String key, String languge, Double latitude, Double longitude, String deviceid, int deviceType, Callback<CurrentLocation> mCallback) {
        MyLists mGetapi = URLClassDefault.getInstance().getApiBuilder().create(MyLists.class);
        mGetapi.MyListView(key, languge, latitude, longitude, deviceid, deviceType, mCallback);
    }

    public interface MyLists {
        @GET("/get_location.html")
        public void MyListView(@Query("key") String key,
                               @Query("language") String languge,
                               @Query("lat") Double latitude,
                               @Query("long") Double longitude,
                               @Query("device_id") String deviceid,
                               @Query("device_type") int deviceType,
                               Callback<CurrentLocation> response);
    }

    public class CurrentLocation {

        private Integer httpcode;
        private String status;
        private String message;
        private Data data;
        private String responsetime;

        /**
         * @return The httpcode
         */
        public Integer getHttpcode() {
            return httpcode;
        }

        /**
         * @param httpcode The httpcode
         */
        public void setHttpcode(Integer httpcode) {
            this.httpcode = httpcode;
        }

        /**
         * @return The status
         */
        public String getStatus() {
            return status;
        }

        /**
         * @param status The status
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * @return The message
         */
        public String getMessage() {
            return message;
        }

        /**
         * @param message The message
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * @return The data
         */
        public Data getData() {
            return data;
        }

        /**
         * @param data The data
         */
        public void setData(Data data) {
            this.data = data;
        }

        /**
         * @return The responsetime
         */
        public String getResponsetime() {
            return responsetime;
        }

        /**
         * @param responsetime The responsetime
         */
        public void setResponsetime(String responsetime) {
            this.responsetime = responsetime;
        }

    }

    public class Data {

        private Get_location get_location;

        /**
         * @return The get_location
         */
        public Get_location getGet_location() {
            return get_location;
        }

        /**
         * @param get_location The get_location
         */
        public void setGet_location(Get_location get_location) {
            this.get_location = get_location;
        }

    }

    public class Get_location {

        private String area_key;
        private String area_name;
        private String city_name;
        private String city_key;
        private Double distance_in_km;

        /**
         * @return The area_key
         */
        public String getArea_key() {
            return area_key;
        }

        /**
         * @param area_key The area_key
         */
        public void setArea_key(String area_key) {
            this.area_key = area_key;
        }

        /**
         * @return The area_name
         */
        public String getArea_name() {
            return area_name;
        }

        /**
         * @param area_name The area_name
         */
        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        /**
         * @return The city_name
         */
        public String getCity_name() {
            return city_name;
        }

        /**
         * @param city_name The city_name
         */
        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        /**
         * @return The city_key
         */
        public String getCity_key() {
            return city_key;
        }

        /**
         * @param city_key The city_key
         */
        public void setCity_key(String city_key) {
            this.city_key = city_key;
        }

        /**
         * @return The distance_in_km
         */
        public Double getDistance_in_km() {
            return distance_in_km;
        }

        /**
         * @param distance_in_km The distance_in_km
         */
        public void setDistance_in_km(Double distance_in_km) {
            this.distance_in_km = distance_in_km;
        }

    }
}