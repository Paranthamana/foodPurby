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
public class DAOUserPassword {

    private static DAOUserPassword ourInstance = new DAOUserPassword();

    public static DAOUserPassword getInstance() {
        return ourInstance;
    }

    private DAOUserPassword() {
    }

    public void Callresponse(String token, String userKey, String userPassword, String newPassword, String language, Callback<UserPassword> mCallback) {
        UserPasswordLists mGetapi = URLClassUser.getInstance().getApiBuilder().create(UserPasswordLists.class);
        mGetapi.UserPasswordListView(token, userKey, userPassword, newPassword, language, mCallback);
    }

    public interface UserPasswordLists {

        @FormUrlEncoded
        @POST("/change_password.html")
        public void UserPasswordListView(@Header("request") String token,
                                    @Field("customer_key") String userKey,
                                     @Field("customer_password") String userPassword,
                                     @Field("new_password") String newPassword,
                                         @Field("language") String language,
                                    Callback<UserPassword> response);
    }

    public class Error {

        private String customer_password;

        /**
         *
         * @return
         * The customer_password
         */
        public String getCustomer_password() {
            return customer_password;
        }

        /**
         *
         * @param customer_password
         * The customer_password
         */
        public void setCustomer_password(String customer_password) {
            this.customer_password = customer_password;
        }

    }

    public class UserPassword {

        private Error error;
        private String httpcode;
        private String status;
        private String message;
        private List<Object> data = new ArrayList<Object>();
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