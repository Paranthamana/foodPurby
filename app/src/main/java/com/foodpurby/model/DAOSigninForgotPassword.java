package com.foodpurby.model;


import com.foodpurby.util.URLClassUser;
import com.foodpurby.utillities.AppSharedValues;

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
public class DAOSigninForgotPassword {

    private static DAOSigninForgotPassword ourInstance = new DAOSigninForgotPassword();

    public static DAOSigninForgotPassword getInstance() {
        return ourInstance;
    }

    private DAOSigninForgotPassword() {
    }

    public void Callresponse(String token, String eMail, Callback<ForgotPassword> mCallback) {
        MyLists mGetapi = URLClassUser.getInstance().getApiBuilder().create(MyLists.class);
        mGetapi.MyListView(token, eMail, AppSharedValues.getLanguage(), mCallback);
    }

    public interface MyLists {

        @FormUrlEncoded
        @POST("/forgot_password.html")
        public void MyListView(@Header("request") String token,
                                   @Field("customer_email") String eMail,
                                   @Field("language") String language,
                                   Callback<ForgotPassword> response);
    }


    public class Error {

        private String customer_email;

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

    }

    public class ForgotPassword {

        private String httpcode;
        private String status;
        private String message;
        private List<Object> data = new ArrayList<Object>();
        private Error error;

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
         * The error
         */
        public Error getError() {
            return error;
        }

        /**
         *
         * @param error
         * The error
         */
        public void setError(Error error) {
            this.error = error;
        }

    }
}