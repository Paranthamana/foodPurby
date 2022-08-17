package com.foodpurby.model;


import com.foodpurby.util.URLClass;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by android1 on 12/31/2015.
 */
public class DAOUserOrderResendOTP {

    private static DAOUserOrderResendOTP ourInstance = new DAOUserOrderResendOTP();

    public static DAOUserOrderResendOTP getInstance() {
        return ourInstance;
    }

    private DAOUserOrderResendOTP() {
    }

    public void Callresponse(String token, String orderKey, String key, Callback<ConfirmOrder> mCallback) {
        MyLists mGetapi = URLClass.getInstance().getApiBuilder().create(MyLists.class);
        mGetapi.MyListView(token, orderKey, key, mCallback);
    }

    public interface MyLists {
        @FormUrlEncoded
        @POST("/resendotp.html")
        public void MyListView(@Header("request") String token,
                               @Field("okey") String orderKey,
                               @Query("key") String key,
                               Callback<ConfirmOrder> response);
    }

    public class ConfirmOrder {

        private String httpcode;
        private String status;
        private String message;
        private Data data;

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
         * The message
         */
        public Data getData() {
            return data;
        }

        /**
         *
         * @param data
         * The message
         */
        public void setMessage(Data data) {
            this.data = data;
        }


    }

    public class Data {

        private String order_key;
        private Double order_total;

        /**
         *
         * @return
         * The order_key
         */
        public String getOrder_key() {
            return order_key;
        }

        /**
         *
         * @param order_key
         * The order_key
         */
        public void setOrder_key(String order_key) {
            this.order_key = order_key;
        }

        /**
         *
         * @return
         * The order_total
         */
        public Double getOrder_total() {
            return order_total;
        }

        /**
         *
         * @param order_total
         * The order_total
         */
        public void setOrder_total(Double order_total) {
            this.order_total = order_total;
        }

    }
}