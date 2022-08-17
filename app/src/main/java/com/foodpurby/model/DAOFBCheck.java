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
 * Created by AMSHEER on 15-03-2016.
 */
public class DAOFBCheck {
    private static DAOFBCheck ourInstance = new DAOFBCheck();

    public static DAOFBCheck getInstance() {
        return ourInstance;
    }

    private DAOFBCheck(){

    }

    public void callResponse(String fbID, Callback<FBResponse> callback){
        FBSingnInCheck mSingnInCheckApi = URLClassUser.getInstance().getApiBuilder().create(FBSingnInCheck.class);
        mSingnInCheckApi.FBSignInResponse(fbID, callback);

    }

    public interface  FBSingnInCheck{
        @FormUrlEncoded
        @POST("/fb_user_exists.html")
        public void FBSignInResponse(@Field("fb_user_id") String  fb_user_id, Callback<FBResponse> response);
    }

    public class Data {

        private String customer_key;
        private String customer_email;
        private String customer_name;
        private String customer_last_name;
        private String customer_mobile;
        private String access_token;
        public String getWallet_points() {
            return wallet_points;
        }

        public void setWallet_points(String wallet_points) {
            this.wallet_points = wallet_points;
        }

        private String wallet_points;
        private List<Object> delivery_details = new ArrayList<Object>();
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         *
         * @return
         * The customer_key
         */
        public String getCustomer_key() {
            return customer_key;
        }

        /**
         *
         * @param customer_key
         * The customer_key
         */
        public void setCustomer_key(String customer_key) {
            this.customer_key = customer_key;
        }

        /**
         *
         * @return
         * The customer_email
         */
        public String getCustomer_email() {
            return customer_email;
        }

        /**
         *
         * @param customer_email
         * The customer_email
         */
        public void setCustomer_email(String customer_email) {
            this.customer_email = customer_email;
        }

        /**
         *
         * @return
         * The customer_name
         */
        public String getCustomer_name() {
            return customer_name;
        }

        /**
         *
         * @param customer_name
         * The customer_name
         */
        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        /**
         *
         * @return
         * The customer_last_name
         */
        public String getCustomer_last_name() {
            return customer_last_name;
        }

        /**
         *
         * @param customer_last_name
         * The customer_last_name
         */
        public void setCustomer_last_name(String customer_last_name) {
            this.customer_last_name = customer_last_name;
        }

        /**
         *
         * @return
         * The customer_mobile
         */
        public String getCustomer_mobile() {
            return customer_mobile;
        }

        /**
         *
         * @param customer_mobile
         * The customer_mobile
         */
        public void setCustomer_mobile(String customer_mobile) {
            this.customer_mobile = customer_mobile;
        }

        /**
         *
         * @return
         * The access_token
         */
        public String getAccess_token() {
            return access_token;
        }

        /**
         *
         * @param access_token
         * The access_token
         */
        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        /**
         *
         * @return
         * The delivery_details
         */
        public List<Object> getDelivery_details() {
            return delivery_details;
        }

        /**
         *
         * @param delivery_details
         * The delivery_details
         */
        public void setDelivery_details(List<Object> delivery_details) {
            this.delivery_details = delivery_details;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class FBResponse {

        private String httpcode;
        private String status;
        private String message;
        private Data data;
        private String responsetime;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }
}
