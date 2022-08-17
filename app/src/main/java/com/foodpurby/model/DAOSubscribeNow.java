package com.foodpurby.model;


import com.foodpurby.util.URLClassUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by android1 on 1/4/2016.
 */
public class DAOSubscribeNow {

    private static DAOSubscribeNow ourInstance = new DAOSubscribeNow();

    public static DAOSubscribeNow getInstance() {
        return ourInstance;
    }

    public DAOSubscribeNow() {
    }

    public void Callresponse(String email, String city, Callback<SubscribeNow> mCallback) {
        SubscribeNowInterface mGetapi = URLClassUser.getInstance().getApiBuilder().create(SubscribeNowInterface.class);
        mGetapi.SubscribeNow(email, city, mCallback);
    }

    public interface SubscribeNowInterface {
        @FormUrlEncoded
        @POST("/subscribe.html?key=$2y$13$O8eus5IZeW9tkpEB3.sahuVVsmZemaXTyIxqDi3ROO2uEYvRxVC72")
        public void SubscribeNow(@Field("email") String email,
                             @Field("city") String city,
                             Callback<SubscribeNow> response);
    }



    public class SubscribeNow {

        private Integer httpcode;
        private String status;
        private String message;
        private List<Object> data = new ArrayList<Object>();
        private String responsetime;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         *
         * @return
         * The httpcode
         */
        public Integer getHttpcode() {
            return httpcode;
        }

        /**
         *
         * @param httpcode
         * The httpcode
         */
        public void setHttpcode(Integer httpcode) {
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
        public List<Object> getData() {
            return data;
        }

        /**
         *
         * @param data
         * The data
         */
        public void setData(List<Object> data) {
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

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }
    }

}
