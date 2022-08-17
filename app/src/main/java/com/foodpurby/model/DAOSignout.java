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
public class DAOSignout {

    private static DAOSignout ourInstance = new DAOSignout();

    public static DAOSignout getInstance() {
        return ourInstance;
    }

    private DAOSignout() {
    }

    public void Callresponse(String token, String eMail, Callback<Signout> mCallback) {
        SignoutLists mGetapi = URLClassUser.getInstance().getApiBuilder().create(SignoutLists.class);
        mGetapi.SignoutListView(token, eMail, mCallback);
    }

    public interface SignoutLists {

        @FormUrlEncoded
        @POST("/logout.html")
        public void SignoutListView(@Header("request") String token,
                                   @Field("customer_email") String eMail,
                                   Callback<Signout> response);
    }

    public class Signout {

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

    }
}