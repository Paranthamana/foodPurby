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
public class DAOUserOrder {

    private static DAOUserOrder ourInstance = new DAOUserOrder();

    public static DAOUserOrder getInstance() {
        return ourInstance;
    }

    private DAOUserOrder() {
    }

    public void Callresponse(String token, String userKey, String language, Callback<UserOrder> mCallback) {
        MyLists mGetapi = URLClassUser.getInstance().getApiBuilder().create(MyLists.class);
        mGetapi.MyListView(token, userKey, language, mCallback);
    }

    public interface MyLists {

        @FormUrlEncoded
        @POST("/myorder.html")
        public void MyListView(@Header("request") String token,
                                         @Field("ckey") String userKey,
                                         @Field("language") String language,
                                         Callback<UserOrder> response);
    }

    public class Data {

        private String shop_name;
        private String shop_key;
        private String shop_logo;
        private String order_key;
        private String order_datetime;
        private Double order_total;
        private String payment_status;
        private Integer products;

        /**
         *
         * @return
         * The shop_name
         */
        public String getShop_name() {
            return shop_name;
        }

        /**
         *
         * @param shop_name
         * The shop_name
         */
        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        /**
         *
         * @return
         * The shop_key
         */
        public String getShop_key() {
            return shop_key;
        }

        /**
         *
         * @param shop_key
         * The shop_key
         */
        public void setShop_key(String shop_key) {
            this.shop_key = shop_key;
        }

        /**
         *
         * @return
         * The shop_logo
         */
        public String getShop_logo() {
            return shop_logo;
        }

        /**
         *
         * @param shop_logo
         * The shop_logo
         */
        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }

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
         * The order_datetime
         */
        public String getOrder_datetime() {
            return order_datetime;
        }

        /**
         *
         * @param order_datetime
         * The order_datetime
         */
        public void setOrder_datetime(String order_datetime) {
            this.order_datetime = order_datetime;
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

        /**
         *
         * @return
         * The payment_status
         */
        public String getPayment_status() {
            return payment_status;
        }

        /**
         *
         * @param payment_status
         * The payment_status
         */
        public void setPayment_status(String payment_status) {
            this.payment_status = payment_status;
        }

        /**
         *
         * @return
         * The products
         */
        public Integer getProducts() {
            return products;
        }

        /**
         *
         * @param products
         * The products
         */
        public void setProducts(Integer products) {
            this.products = products;
        }

    }

    public class UserOrder {

        private Integer httpcode;
        private String status;
        private String message;
        private List<Data> data = new ArrayList<Data>();
        private String responsetime;

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
        public List<Data> getData() {
            return data;
        }

        /**
         *
         * @param data
         * The data
         */
        public void setData(List<Data> data) {
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