package com.foodpurby.model;


import com.foodpurby.util.URLClass;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Query;

public class DAOPaymentWallet {

    private static DAOPaymentWallet ourInstance = new DAOPaymentWallet();

    public static DAOPaymentWallet getInstance() {
        return ourInstance;
    }

    private DAOPaymentWallet() {
    }

    public void Callresponse(String language,
                             String addressKey,
                             String userKey,
                             String restaurantKey,
                             int deliveryOrPickup,
                             int onlneOrCOD,
                             String deviceId,
                             String deviceType,
                             String later_date,
                             String later_time,
                             String member_count,
                             Callback<CODPaymentResponse> mCallback) {

        DAOOrderHelper obj = new DAOOrderHelper();

        MyLabelLists mGetapi = URLClass.getInstance().getApiBuilder().create(MyLabelLists.class);
        mGetapi.MyListView(language,
                addressKey,
                userKey,
                restaurantKey,
                deliveryOrPickup,
                onlneOrCOD,
                deviceId,
                deviceType,
                later_date,
                later_time,
                member_count,
                obj.getCartProducts(),
                mCallback);
    }

    public interface MyLabelLists {

        //@FormUrlEncoded
        @POST("/payment.html?key=$2y$13$O8eus5IZeW9tkpEB3.sahuVVsmZemaXTyIxqDi3ROO2uEYvRxVC72")
        public void MyListView(@Query("language") String language,
                               @Query("akey") String akey,
                               @Query("ckey") String ckey,
                               @Query("shop_key") String shop_key,
                               @Query("d_opt") int d_opt,
                               @Query("p_opt") int p_opt,
                               @Query("dev") String dev,
                               @Query("dev_t") String dev_t,
                               @Query("later_date") String later_date,
                               @Query("later_time") String later_time,
                               @Query("member_count") String member_count,
                               @Body DAOOrderHelper.RequestValues mRequestValues,
                               Callback<CODPaymentResponse> codPaymentResponse);
    }




    /*public class CODPaymentResponse {

        private String httpcode;
        private String status;
        private String message;
        private Data data;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getHttpcode() {
            return httpcode;
        }

        public void setHttpcode(String httpcode) {
            this.httpcode = httpcode;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Data {

        private String order_key;
        private Double order_total;
        private String wallet_points;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getOrder_key() {
            return order_key;
        }

        public void setOrder_key(String order_key) {
            this.order_key = order_key;
        }

        public Double getOrder_total() {
            return order_total;
        }

        public void setOrder_total(Double order_total) {
            this.order_total = order_total;
        }

        public String getWallet_points() {
            return wallet_points;
        }

        public void setWallet_points(String wallet_points) {
            this.wallet_points = wallet_points;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }*/

    public class Data {

        private String order_key;
        private Double order_total;
        private String wallet_points;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getOrder_key() {
            return order_key;
        }

        public void setOrder_key(String order_key) {
            this.order_key = order_key;
        }

        public Double getOrder_total() {
            return order_total;
        }

        public void setOrder_total(Double order_total) {
            this.order_total = order_total;
        }

        public String getWallet_points() {
            return wallet_points;
        }

        public void setWallet_points(String wallet_points) {
            this.wallet_points = wallet_points;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }


    public class CODPaymentResponse {

        private String httpcode;
        private String status;
        private String message;
        private Data data;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public String getHttpcode() {
            return httpcode;
        }

        public void setHttpcode(String httpcode) {
            this.httpcode = httpcode;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }




}