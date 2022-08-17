package com.foodpurby.model;


import com.foodpurby.util.URLClassUser;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by android1 on 1/4/2016.
 */
public class DAOUserProfileEdit {

    private static DAOUserProfileEdit ourInstance = new DAOUserProfileEdit();

    public static DAOUserProfileEdit getInstance() {
        return ourInstance;
    }

    private DAOUserProfileEdit() {
    }

    public void Callresponse(String token,
                             String userKey,
                             String firstName, String lastName, String mobileNumber,
                             String language,
                             String dob,
                             String gender,
                             Callback<UserProfile> mCallback) {
        EditProfileLists mGetapi = URLClassUser.getInstance().getApiBuilder().create(EditProfileLists.class);
        mGetapi.EditProfileListView(token, userKey, firstName, lastName, mobileNumber, language, dob, gender, mCallback);
    }

    public interface EditProfileLists {

        @FormUrlEncoded
        @POST("/edit_profile.html")
        public void EditProfileListView(@Header("request") String token,
                                        @Field("customer_key") String userKey,
                                        @Field("customer_name") String firstName,
                                        @Field("customer_last_name") String lastName,
                                        @Field("customer_mobile") String mobileNumber,
                                        @Field("language") String language,
                                        @Field("customer_dob") String customer_dob,
                                        @Field("customer_gender") String customer_gender,
                                        Callback<UserProfile> response);
    }


    public class Data {

        private String customer_key;
        private String customer_email;
        private String customer_name;
        private String customer_last_name;
        private String customer_mobile;
        private String access_token;
        private List<Object> delivery_details = new ArrayList<Object>();

        /**
         * @return The customer_key
         */
        public String getCustomer_key() {
            return customer_key;
        }

        /**
         * @param customer_key The customer_key
         */
        public void setCustomer_key(String customer_key) {
            this.customer_key = customer_key;
        }

        /**
         * @return The customer_email
         */
        public String getCustomer_email() {
            return customer_email;
        }

        /**
         * @param customer_email The customer_email
         */
        public void setCustomer_email(String customer_email) {
            this.customer_email = customer_email;
        }

        /**
         * @return The customer_name
         */
        public String getCustomer_name() {
            return customer_name;
        }

        /**
         * @param customer_name The customer_name
         */
        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        /**
         * @return The customer_last_name
         */
        public String getCustomer_last_name() {
            return customer_last_name;
        }

        /**
         * @param customer_last_name The customer_last_name
         */
        public void setCustomer_last_name(String customer_last_name) {
            this.customer_last_name = customer_last_name;
        }

        /**
         * @return The customer_mobile
         */
        public String getCustomer_mobile() {
            return customer_mobile;
        }

        /**
         * @param customer_mobile The customer_mobile
         */
        public void setCustomer_mobile(String customer_mobile) {
            this.customer_mobile = customer_mobile;
        }

        /**
         * @return The access_token
         */
        public String getAccess_token() {
            return access_token;
        }

        /**
         * @param access_token The access_token
         */
        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        /**
         * @return The delivery_details
         */
        public List<Object> getDelivery_details() {
            return delivery_details;
        }

        /**
         * @param delivery_details The delivery_details
         */
        public void setDelivery_details(List<Object> delivery_details) {
            this.delivery_details = delivery_details;
        }

    }

    public class Error {

        private String customer_name;
        private String customer_last_name;
        private String customer_email;
        private String customer_mobile;

        /**
         * @return The customer_email
         */
        public String getCustomer_name() {
            return customer_name;
        }

        /**
         * @param customer_name The customer_name
         */
        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        /**
         * @return The customer_email
         */
        public String getCustomer_last_name() {
            return customer_last_name;
        }

        /**
         * @param customer_last_name The customer_last_name
         */
        public void setCustomer_last_name(String customer_last_name) {
            this.customer_last_name = customer_last_name;
        }

        /**
         * @return The customer_email
         */
        public String getCustomer_email() {
            return customer_email;
        }

        /**
         * @param customer_email The customer_email
         */
        public void setCustomer_email(String customer_email) {
            this.customer_email = customer_email;
        }

        /**
         * @return The customer_mobile
         */
        public String getCustomer_mobile() {
            return customer_mobile;
        }

        /**
         * @param customer_mobile The customer_mobile
         */
        public void setCustomer_mobile(String customer_mobile) {
            this.customer_mobile = customer_mobile;
        }

    }

    public class UserProfile {

        private String httpcode;
        private String status;
        private String message;
        private String responsetime;
        private Error error;

        /**
         * @return The httpcode
         */
        public String getHttpcode() {
            return httpcode;
        }

        /**
         * @param httpcode The httpcode
         */
        public void setHttpcode(String httpcode) {
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

        /**
         * @return The error
         */
        public Error getError() {
            return error;
        }

        /**
         * @param error The error
         */
        public void setError(Error error) {
            this.error = error;
        }

    }
}