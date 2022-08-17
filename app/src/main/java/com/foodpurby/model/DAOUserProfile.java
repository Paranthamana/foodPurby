package com.foodpurby.model;


import com.foodpurby.util.URLClassUser;
import com.foodpurby.utillities.AppSharedValues;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by android1 on 1/4/2016.
 */
public class DAOUserProfile {

    private static DAOUserProfile ourInstance = new DAOUserProfile();

    public static DAOUserProfile getInstance() {
        return ourInstance;
    }

    private DAOUserProfile() {
    }

    public void Callresponse(String token, String customerKey, Callback<Profile> mCallback) {
        ProfileLists mGetapi = URLClassUser.getInstance().getApiBuilder().create(ProfileLists.class);
        mGetapi.ProfileListView(token, customerKey, AppSharedValues.getLanguage(), "$2y$13$O8eus5IZeW9tkpEB3.sahuVVsmZemaXTyIxqDi3ROO2uEYvRxVC72", mCallback);
    }

    public interface ProfileLists {
        @GET("/profile.html")
        public void ProfileListView(@Header("request") String token,
                                    @Query("customer_key") String customerKey,
                                    @Query("languge") String languge,
                                    @Query("key") String key,
                                    Callback<Profile> response);
    }

    public class Data {

        private Profile_ profile;

        /**
         *
         * @return
         * The profile
         */
        public Profile_ getProfile() {
            return profile;
        }

        /**
         *
         * @param profile
         * The profile
         */
        public void setProfile(Profile_ profile) {
            this.profile = profile;
        }

    }

    public class Profile {

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

    public class Profile_ {

        private String customer_email;
        private String customer_name;
        private String customer_key;
        private String customer_last_name;
        private String customer_mobile;
        private String customer_gender;
        private String customer_dob;

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
         * The customer_gender
         */
        public String getCustomer_gender() {
            return customer_gender;
        }

        /**
         *
         * @param customer_gender
         * The customer_gender
         */
        public void setCustomer_gender(String customer_gender) {
            this.customer_gender = customer_gender;
        }

        /**
         *
         * @return
         * The customer_dob
         */
        public String getCustomer_dob() {
            return customer_dob;
        }

        /**
         *
         * @param customer_dob
         * The customer_dob
         */
        public void setCustomer_dob(String customer_dob) {
            this.customer_dob = customer_dob;
        }

    }
}
